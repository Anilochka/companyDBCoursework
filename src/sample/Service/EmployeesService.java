package sample.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Entity.Employee;
import java.sql.*;

public class EmployeesService {
    private Connection connection;

    public EmployeesService(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<Employee> getEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM employees");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int employeeId = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String fatherName = rs.getString("father_name");
                String position = rs.getString("position");
                int salary = rs.getInt("salary");

                employees.add(new Employee(employeeId, firstName, lastName, fatherName, position, salary));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting employee list!", e);
        }
        return employees;
    }

    public Employee getEmployeeById(int idEmployee) {
        Employee employee = new Employee();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
            ps.setInt(1, idEmployee);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String fatherName = rs.getString("father_name");
                String position = rs.getString("position");
                int salary = rs.getInt("salary");

                return new Employee(idEmployee, firstName, lastName, fatherName, position, salary);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting employee by ID!", e);
        }
        return employee;
    }

    public Employee getEmployeeByFIO(String fio) {
        Employee employee = new Employee();
        String[] subStr;
        String delimeter = " ";
        subStr = fio.split(delimeter);

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM employees WHERE last_name = ? " +
                    "AND first_name = ? AND father_name = ?");
            ps.setString(1, subStr[0]);
            ps.setString(2, subStr[1]);
            ps.setString(3, subStr[2]);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int employeeId = rs.getInt("id");
                String position = rs.getString("position");
                int salary = rs.getInt("salary");

                return new Employee(employeeId, subStr[1], subStr[0], subStr[2], position, salary);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting employee by FIO!", e);
        }
        return employee;
    }

    public void addEmployee(Employee employee) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO employees (first_name, last_name, " +
                    "father_name, position, salary) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getFatherName());
            ps.setString(4, employee.getPosition());
            ps.setInt(5, employee.getSalary());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding employee!", e);
        }
    }

    public void updateEmployee(Employee newEmployee, Employee oldEmployee) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE employees SET first_name = ?, last_name = ?, " +
                    "father_name = ?, position = ?, salary = ? WHERE id = ?");
            ps.setString(1, newEmployee.getFirstName());
            ps.setString(2, newEmployee.getLastName());
            ps.setString(3, newEmployee.getFatherName());
            ps.setString(4, newEmployee.getPosition());
            ps.setInt(5, newEmployee.getSalary());
            ps.setInt(6, oldEmployee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating employee!", e);
        }
    }

    public void deleteEmployee(Employee employee) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM employees WHERE id = ?");
            ps.setDouble(1, employee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting employee!", e);
        }
    }
}
