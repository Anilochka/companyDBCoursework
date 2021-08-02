package sample.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Entity.Department;
import java.sql.*;

public class DepartmentsService {
    private Connection connection;

    public DepartmentsService(Connection connection) { this.connection = connection; }

    public ObservableList<Department> getDepartments() {
        ObservableList<Department> departments = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM departments");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int departmentId = rs.getInt("id");
                String name = rs.getString("name");

                departments.add(new Department(departmentId, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting departments list!", e);
        }
        return departments;
    }

    public Department getDepartmentById(int idDepartment) {
        Department department = new Department();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM departments WHERE id = ?");
            ps.setInt(1, idDepartment);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");

                return new Department(idDepartment, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error department by ID!", e);
        }
        return department;
    }

    public Department getDepartmentByName(String name) {
        Department department = new Department();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM departments WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int departmentId = rs.getInt("id");

                return new Department(departmentId, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting department by name!", e);
        }
        return department;
    }

    public void addDepartment(Department department) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO departments (name) VALUES (?)");
            ps.setString(1, department.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding department!", e);
        }
    }

    public void updateDepartment(Department newDepartment, Department oldDepartment) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE departments SET name = ? WHERE id = ?");
            ps.setString(1, newDepartment.getName());
            ps.setInt(2, oldDepartment.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating department!", e);
        }
    }

    public void deleteDepartment(Department department) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM departments WHERE id = ?");
            ps.setDouble(1, department.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting department!", e);
        }
    }
}
