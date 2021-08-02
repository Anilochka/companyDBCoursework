package sample.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.AlertMessage;
import sample.Entity.Project;
import java.sql.*;

public class ProjectsService {
    private Connection connection;
    private DepartmentsService departmentsService;
    private AlertMessage alertMessage;

    public ProjectsService(Connection connection, DepartmentsService departmentsService) {
        this.connection = connection;
        this.departmentsService = departmentsService;
        this.alertMessage = new AlertMessage();
    }

    public ObservableList<Project> getProjects() {
        ObservableList<Project> projects = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM projects");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int projectId = rs.getInt("id");
                String name = rs.getString("name");
                int cost = rs.getInt("cost");
                int departmentId = rs.getInt("department_id");
                Date dateBeg = rs.getDate("date_beg");
                Date dateEnd = rs.getDate("date_end");
                Date dateEndReal = rs.getDate("date_end_real");
                String departmentName = departmentsService.getDepartmentById(departmentId).getName();

                projects.add(new Project(projectId, name, cost, departmentId, dateBeg, dateEnd, dateEndReal, "0", departmentName));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting projects list!", e);
        }

        return projects;
    }

    public ObservableList<Project> getProjectsWithIncomes() {
        ObservableList<Project> projects = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT ID, NAME, INCOME FROM PROJECTS_INCOME");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("ID"));
                project = getProjectById(rs.getInt("ID"));
                int departmentId = project.getDepartmentId();
                String departmentName = departmentsService.getDepartmentById(departmentId).getName();
                project.setDepartmentName(departmentName);
                project.setProfit(String.valueOf(rs.getDouble("INCOME")));

                projects.add(project);
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting projects list with incomes!", e);
        }
    }

    public Project getProjectById(int idProject) {
        Project project = new Project();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM projects WHERE id = ?");
            ps.setInt(1, idProject);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int cost = rs.getInt("cost");
                int departmentId = rs.getInt("department_id");
                Date dateBeg = rs.getDate("date_beg");
                Date dateEnd = rs.getDate("date_end");
                Date dateEndReal = rs.getDate("date_end_real");
                String departmentName = departmentsService.getDepartmentById(departmentId).getName();

                return new Project(idProject, name, cost, departmentId, dateBeg, dateEnd, dateEndReal, "0", departmentName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting project by ID!", e);
        }
        return project;
    }

    public void addProject(Project project) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO projects (name, cost, department_id, " +
                    "date_beg, date_end, date_end_real) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, project.getName());
            ps.setInt(2, project.getCost());
            ps.setInt(3, project.getDepartmentId());
            ps.setDate(4, project.getDateBeg());
            ps.setDate(5, project.getDateEnd());
            ps.setDate(6, project.getDateEndReal());
            ps.executeUpdate();
        }
         catch (SQLException e) {
            if (project.getDateBeg() == null) {
                alertMessage.showWarningAlert("Begin date should be setted!");
                return;
            } else if (project.getDateBeg().after(project.getDateEnd()) || project.getDateBeg().after(project.getDateEndReal())) {
                alertMessage.showWarningAlert("You can't set end date earlier than begin date!");
                return;
            }
            throw new RuntimeException("Error adding project!", e);
        }
    }

    public void updateProject(Project newProject, Project oldProject) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE projects SET name = ?, cost = ?, " +
                    "department_id = ?, date_beg = ?, date_end = ?, date_end_real = ? WHERE id = ?");
            ps.setString(1, newProject.getName());
            ps.setInt(2, newProject.getCost());
            ps.setInt(3, newProject.getDepartmentId());
            ps.setDate(4, newProject.getDateBeg());
            ps.setDate(5, newProject.getDateEnd());
            ps.setDate(6, newProject.getDateEndReal());
            ps.setInt(7, oldProject.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            if (newProject.getDateBeg() == null) {
                alertMessage.showWarningAlert("Begin date should be setted!");
                return;
            } else if (newProject.getDateBeg().after(newProject.getDateEnd()) ||
                    newProject.getDateBeg().after(newProject.getDateEndReal())) {
                alertMessage.showWarningAlert("You can't set end date earlier than begin date!");
                return;
            }
            throw new RuntimeException("Error updating project!", e);
        }
    }

    public void deleteProject(Project project) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM projects WHERE id = ?");
            ps.setDouble(1, project.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            if (project.getDateEnd().after(new java.sql.Date(System.currentTimeMillis()))) {
                alertMessage.showWarningAlert("Project is active now, so it can't be deleted!");
                return;
            }
            throw new RuntimeException("Error deleting project!", e);
        }
    }
}
