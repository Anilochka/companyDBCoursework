package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Entity.Department;
import sample.Entity.DepartmentEmployee;
import sample.Entity.Employee;
import sample.Entity.Project;
import sample.Main;
import sample.Service.DepartmentsEmployeesService;
import sample.Service.DepartmentsService;
import sample.Service.EmployeesService;
import sample.Service.ProjectsService;
import java.sql.Connection;
import java.sql.Date;

public class MainPageController implements Controller {
    private Main mainApp;
    private Connection connection;
    private ProjectsService projectsService;
    private DepartmentsService departmentsService;
    private DepartmentsEmployeesService departmentsEmployeesService;
    private EmployeesService employeesService;

    @FXML
    private TabPane tabPane;

    @FXML
    private TableView<Project> projectsTable;

    @FXML
    private TableColumn<Project, Integer> projectId;

    @FXML
    private TableColumn<Project, String> projectName;

    @FXML
    private TableColumn<Project, Integer> projectCost;

    @FXML
    private TableColumn<Project, Integer> projectDepName;

    @FXML
    private TableColumn<Project, Date> projectBeg;

    @FXML
    private TableColumn<Project, Date> projectEnd;

    @FXML
    private TableColumn<Project, Date> projectEndReal;

    @FXML
    private TableColumn<Project, Double> projectProfit;

    @FXML
    private TableView<Employee> employeesTable;

    @FXML
    private TableColumn<Employee, Integer> employeeId;

    @FXML
    private TableColumn<Employee, String> employeeFirstName;

    @FXML
    private TableColumn<Employee, String> employeeLastName;

    @FXML
    private TableColumn<Employee, String> employeeFatherName;

    @FXML
    private TableColumn<Employee, String> employeePosition;

    @FXML
    private TableColumn<Employee, Integer> employeeSalary;

    @FXML
    private TableView<Department> departmentsTable;

    @FXML
    private TableColumn<Department, Integer> departmentId;

    @FXML
    private TableColumn<Department, String> departmentName;

    @FXML
    private TableView<DepartmentEmployee> departmentsEmployeesTable;

    @FXML
    private TableColumn<DepartmentEmployee, Integer> departmentsEmployeesId;

    @FXML
    private TableColumn<DepartmentEmployee, String> departmentsName;

    @FXML
    private TableColumn<DepartmentEmployee, String> employeesName;

    @Override
    public void updatePage() {
        projectId.setCellValueFactory(new PropertyValueFactory<>("id"));
        projectName.setCellValueFactory(new PropertyValueFactory<>("name"));
        projectCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        projectDepName.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        projectBeg.setCellValueFactory(new PropertyValueFactory<>("dateBeg"));
        projectEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        projectEndReal.setCellValueFactory(new PropertyValueFactory<>("dateEndReal"));
        projectProfit.setCellValueFactory(new PropertyValueFactory<>("profit"));
        projectProfit.setVisible(false);

        employeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employeeLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employeeFatherName.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        employeePosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        employeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        departmentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        departmentName.setCellValueFactory(new PropertyValueFactory<>("name"));

        departmentsEmployeesId.setCellValueFactory(new PropertyValueFactory<>("id"));
        departmentsName.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        employeesName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

        projectsService = mainApp.getProjectsService();
        departmentsService = mainApp.getDepartmentsService();
        departmentsEmployeesService = mainApp.getDepartmentsEmployeesService();
        employeesService = mainApp.getEmployeesService();

        projectsTable.setItems(projectsService.getProjects());
        employeesTable.setItems(employeesService.getEmployees());
        departmentsTable.setItems(departmentsService.getDepartments());
        departmentsEmployeesTable.setItems(departmentsEmployeesService.getDepartmentEmployees());
    }

    public void showProfits(ActionEvent event) {
        projectsTable.setItems(projectsService.getProjectsWithIncomes());
        projectProfit.setVisible(true);
    }

    public void onDeleteProject(ActionEvent event){
        Project project = projectsTable.getSelectionModel().getSelectedItem();
        projectsService.deleteProject(project);
        updatePage();
    }

    public void onDeleteDepartment(ActionEvent event){
        Department department = departmentsTable.getSelectionModel().getSelectedItem();
        departmentsService.deleteDepartment(department);
        updatePage();
    }

    public void onDeleteEmployee(ActionEvent event){
        Employee employee = employeesTable.getSelectionModel().getSelectedItem();
        employeesService.deleteEmployee(employee);
        updatePage();
    }

    public void onDeleteDepartmentEmployee(ActionEvent event){
        DepartmentEmployee departmentEmployee = departmentsEmployeesTable.getSelectionModel().getSelectedItem();
        departmentsEmployeesService.deleteDepartmentEmployee(departmentEmployee);
        updatePage();
    }

    public void onUpdateProject(ActionEvent event){
        Project project = projectsTable.getSelectionModel().getSelectedItem();

        if (project != null) {
            mainApp.setProject(project);
            mainApp.openPage("projects/ProjectsUpdate");
        }
    }

    public void onUpdateDepartment(ActionEvent event){
        Department department = departmentsTable.getSelectionModel().getSelectedItem();

        if (department != null) {
            mainApp.setDepartment(department);
            mainApp.openPage("departments/DepartmentsUpdate");
        }
    }

    public void onUpdateEmployee(ActionEvent event){
        Employee employee = employeesTable.getSelectionModel().getSelectedItem();

        if (employee != null) {
            mainApp.setEmployee(employee);
            mainApp.openPage("employees/EmployeesUpdate");
        }
    }

    public void onUpdateDepartmentEmployee(ActionEvent event){
        DepartmentEmployee departmentEmployee = departmentsEmployeesTable.getSelectionModel().getSelectedItem();

        if (departmentEmployee != null) {
            mainApp.setDepartmentEmployee(departmentEmployee);
            mainApp.openPage("departmentEmployees/DepartmentsEmployeesUpdate");
        }
    }

    public void onAddProject(ActionEvent event){
        mainApp.openPage("projects/ProjectAdd");
    }

    public void onAddDepartment(ActionEvent event){
        mainApp.openPage("departments/DepartmentAdd");
    }

    public void onAddEmployee(ActionEvent event){
        mainApp.openPage("employees/EmployeeAdd");
    }

    public void onAddDepartmentEmployee(ActionEvent event){ mainApp.openPage("departmentEmployees/DepartmentEmployeeAdd"); }

    @Override
    public void setConnection(Connection con) { this.connection = con; }

    @Override
    public void setMainApp(Main main) { this.mainApp = main; }

    @Override
    public void setTab(int idTab) { tabPane.getSelectionModel().select(idTab); }
}
