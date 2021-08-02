package sample.Controllers.departments;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.Controllers.Controller;
import sample.Entity.Department;
import sample.Main;
import sample.Service.DepartmentsService;
import java.sql.Connection;

public class AddDepartmentController implements Controller {
    private Main mainApp;
    private Connection connection;
    private DepartmentsService departmentsService;

    @FXML
    TextField name;

    @Override
    public void updatePage() {
        departmentsService = mainApp.getDepartmentsService();
    }

    @FXML
    public void addDepartment() {
        if (name.getText().equals("")) {
            mainApp.showAlert("You should enter department name!");
            return;
        }

        Department department = new Department(name.getText());
        departmentsService.addDepartment(department);
        mainApp.openMainPage(2);
    }

    @FXML
    public void cancel() {
        mainApp.openMainPage(2);
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
