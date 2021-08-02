package sample.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.AlertMessage;
import sample.Entity.DepartmentEmployee;
import sample.Entity.Employee;
import java.sql.*;

public class DepartmentsEmployeesService {
    private Connection connection;
    private DepartmentsService departmentsService;
    private EmployeesService employeesService;
    private AlertMessage alertMessage;

    public DepartmentsEmployeesService(Connection connection, DepartmentsService departmentsService, EmployeesService employeesService) {
        this.connection = connection;
        this.departmentsService = departmentsService;
        this.employeesService = employeesService;
        this.alertMessage = new AlertMessage();
    }

    public ObservableList<DepartmentEmployee> getDepartmentEmployees() {
        ObservableList<DepartmentEmployee> departmentEmployees = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM departments_employees");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int departmentEmployeeId = rs.getInt("id");
                int departmentId = rs.getInt("department_id");
                int employeeId = rs.getInt("employee_id");
                String departmentName = departmentsService.getDepartmentById(departmentId).getName();
                Employee employee= employeesService.getEmployeeById(employeeId);
                String employeeName = employee.getLastName() + " " + employee.getFirstName() + " " + employee.getFatherName();

                departmentEmployees.add(new DepartmentEmployee(departmentEmployeeId, departmentId, employeeId, departmentName, employeeName));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting DepartmentEmployees list!", e);
        }
        return departmentEmployees;
    }

    public DepartmentEmployee getDepartmentEmployeeById(int idDepartmentEmployee) {
        DepartmentEmployee departmentEmployee = new DepartmentEmployee();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM departments_employees WHERE id = ?");
            ps.setInt(1, idDepartmentEmployee);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int departmentId = rs.getInt("department_id");
                int employeeId = rs.getInt("employee_id");
                String departmentName = departmentsService.getDepartmentById(departmentId).getName();
                Employee employee= employeesService.getEmployeeById(employeeId);
                String employeeName = employee.getLastName() + " " + employee.getFirstName() + " " + employee.getFatherName();

                return new DepartmentEmployee(idDepartmentEmployee, departmentId, employeeId, departmentName, employeeName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting DepartmentEmployee list!", e);
        }
        return departmentEmployee;
    }

    public DepartmentEmployee getDepartmentEmployeeByIds(int idDepartment, int idEmployee) {
        DepartmentEmployee departmentEmployee = new DepartmentEmployee();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM departments_employees WHERE " +
                    "department_id = ? AND employee_id = ?");
            ps.setInt(1, idDepartment);
            ps.setInt(2, idEmployee);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idDepartmentEmployee = rs.getInt("id");
                String departmentName = departmentsService.getDepartmentById(idDepartment).getName();
                Employee employee= employeesService.getEmployeeById(idEmployee);
                String employeeName = employee.getLastName() + " " + employee.getFirstName() + " " + employee.getFatherName();

                return new DepartmentEmployee(idDepartmentEmployee, idDepartment, idEmployee, departmentName, employeeName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting DepartmentEmployee by depId, empId!", e);
        }
        return departmentEmployee;
    }

    public void addDepartmentEmployee(DepartmentEmployee departmentEmployee) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO departments_employees (department_id, employee_id) " +
                    "VALUES (?, ?)");
            ps.setInt(1, departmentEmployee.getDepartmentId());
            ps.setInt(2, departmentEmployee.getEmployeeId());
            ps.executeUpdate();
        } catch (SQLException e) {
            if (getDepartmentEmployeeByIds(departmentEmployee.getDepartmentId(), departmentEmployee.getEmployeeId()) != null) {
                alertMessage.showWarningAlert("Employee works at this department!");
                return;
            }
            throw new RuntimeException("Error adding employee to department!", e);
        }
    }

    public void updateDepartmentEmployee(DepartmentEmployee newDepartmentEmployee, DepartmentEmployee oldDepartmentEmployee) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE departments_employees SET department_id = ?," +
                    "employee_id = ? WHERE id = ?");
            ps.setInt(1, newDepartmentEmployee.getDepartmentId());
            ps.setInt(2, newDepartmentEmployee.getEmployeeId());
            ps.setInt(3, oldDepartmentEmployee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating departmentEmployee!", e);
        }
    }

    public void deleteDepartmentEmployee(DepartmentEmployee departmentEmployee) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM departments_employees WHERE id = ?");
            ps.setDouble(1, departmentEmployee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting employee from department!", e);
        }
    }
}
