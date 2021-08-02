package sample.Controllers.employees;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.Controllers.Controller;
import sample.Entity.Employee;
import sample.Main;
import sample.Service.EmployeesService;
import java.sql.Connection;

public class UpdateEmployeeController implements Controller {
    private Main mainApp;
    private Connection connection;
    private EmployeesService employeesService;
    private Employee employee;

    @FXML
    Text employeeId;

    @FXML
    TextField firstName;

    @FXML
    TextField lastName;

    @FXML
    TextField fatherName;

    @FXML
    TextField position;

    @FXML
    TextField salary;

    @Override
    public void updatePage() {
        employeesService = mainApp.getEmployeesService();
        employee = mainApp.getEmployee();

        employeeId.setText(String.valueOf(employee.getId()));
        firstName.setText(employee.getFirstName());
        lastName.setText(String.valueOf(employee.getLastName()));
        fatherName.setText(String.valueOf(employee.getFatherName()));
        position.setText(String.valueOf(employee.getPosition()));
        salary.setText(String.valueOf(employee.getSalary()));
    }

    @FXML
    public void updateEmployee() {
        if (firstName.getText().equals("") || lastName.getText().equals("") || fatherName.getText().equals("") ||
                position.getText().equals("") || salary.getText().equals("")) {
            mainApp.showAlert("Enter all information!");
            return;
        }

        if ((Integer.parseInt(salary.getText()) <= 0)) {
            mainApp.showAlert("Salary can't be negative or 0!");
            return;
        }

        Employee newEmployee = new Employee(employee.getId(), firstName.getText(), lastName.getText(), fatherName.getText(),
                position.getText(), Integer.parseInt(salary.getText()));
        employeesService.updateEmployee(newEmployee, employee);
        mainApp.openMainPage(1);
    }

    @FXML
    public void cancel() {
        mainApp.openMainPage(1);
    }

    public Employee getEmployee() { return employee; }

    public void setEmployee(Employee employee) { this.employee = employee; }

    @Override
    public void setConnection(Connection con) {
        this.connection = con;
    }

    @Override
    public void setMainApp(Main main) {
        this.mainApp = main;
    }

    @Override
    public void setTab(int idTab) { }
}
