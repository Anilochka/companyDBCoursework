package sample.Controllers.employees;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.Controllers.Controller;
import sample.Entity.Employee;
import sample.Main;
import sample.Service.EmployeesService;
import java.sql.Connection;

public class AddEmployeeController implements Controller {
    private Main mainApp;
    private Connection connection;
    private EmployeesService employeesService;

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
    }

    @FXML
    public void addEmployee() {
        if (firstName.getText().equals("") || lastName.getText().equals("") || fatherName.getText().equals("") ||
                position.getText().equals("") || salary.getText().equals("")) {
            mainApp.showAlert("Enter all information!");
            return;
        }

        if ((Integer.parseInt(salary.getText()) <= 0)) {
            mainApp.showAlert("Salary can't be negative or 0!");
            return;
        }

        Employee employee = new Employee(firstName.getText(), lastName.getText(), fatherName.getText(),
                position.getText(), Integer.parseInt(salary.getText()));
        employeesService.addEmployee(employee);
        mainApp.openMainPage(1);
    }

    @FXML
    public void cancel() {
        mainApp.openMainPage(1);
    }

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
