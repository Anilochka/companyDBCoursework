package sample.Controllers.departments;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.Controllers.Controller;
import sample.Entity.Department;
import sample.Main;
import sample.Service.DepartmentsService;
import java.sql.Connection;

public class UpdateDepartmentController implements Controller {
    private Main mainApp;
    private Connection connection;
    private DepartmentsService departmentsService;
    private Department department;

    @FXML
    Text departmentId;

    @FXML
    TextField name;

    @Override
    public void updatePage() {
        departmentsService = mainApp.getDepartmentsService();
        department = mainApp.getDepartment();

        departmentId.setText(String.valueOf(department.getId()));
        name.setText(department.getName());
    }

    @FXML
    public void updateDepartment() {
        if (name.getText().equals("")) {
            mainApp.showAlert("You should enter department name!");
            return;
        }

        Department newDepartment = new Department(department.getId(), name.getText());
        departmentsService.updateDepartment(newDepartment, department);
        mainApp.openMainPage(2);
    }

    @FXML
    public void cancel() {
        mainApp.openMainPage(2);
    }

    public Department getDepartment() { return department; }

    public void setDepartment(Department department) { this.department = department; }

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
