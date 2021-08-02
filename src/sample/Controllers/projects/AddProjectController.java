package sample.Controllers.projects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Controllers.Controller;
import sample.Entity.Department;
import sample.Entity.Project;
import sample.Main;
import sample.Service.DepartmentsService;
import sample.Service.ProjectsService;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddProjectController implements Controller {
    private Main mainApp;
    private Connection connection;
    private ProjectsService projectsService;
    private DepartmentsService departmentsService;
    private Integer depId;

    @FXML
    TextField name;

    @FXML
    TextField cost;

    @FXML
    ComboBox<String> depChoice;

    @FXML
    TextField begin;

    @FXML
    TextField end;

    @FXML
    TextField endReal;

    @Override
    public void updatePage() {
        projectsService = mainApp.getProjectsService();
        departmentsService = mainApp.getDepartmentsService();

        depChoice.setOnMouseClicked(event -> {
            ObservableList<Department> depList = FXCollections.observableArrayList(departmentsService.getDepartments());
            ObservableList<String> newDepList = FXCollections.observableArrayList();

            for (Department dep : depList) {
                if (dep != null)
                    newDepList.add(dep.getName());
            }

            ObservableList<String> oldDep = depChoice.getItems();
            if (!newDepList.equals(oldDep)) {
                depChoice.setItems(newDepList);
            }
        });

        depChoice.setOnAction(event -> {
            String dep = depChoice.getValue();
            if (!dep.equals("")) {
                depId = departmentsService.getDepartmentByName(dep).getId();
            }
        });
    }

    @FXML
    public void addProject() {
        if (depId == null || name.getText().equals("") || cost.getText().equals("") || begin.getText().equals("")) {
            mainApp.showAlert("You should set project name, cost begin date and choose department!");
        } else {
            String begD = begin.getText();
            String endD = end.getText();
            String endR = endReal.getText();

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateBegTmp;
            java.util.Date dateEndTmp = null;
            java.util.Date dateEndRealTmp = null;

            try {
                dateBegTmp = format.parse(begD);
            } catch (ParseException e) {
                mainApp.showAlert("Wrong begin date format!");
                return;
            }

            java.sql.Date endDate = null;
            java.sql.Date endRealDate = null;

            if (!(endD.equals(""))) {
                try {
                    dateEndTmp = format.parse(endD);
                } catch (ParseException e) {
                    mainApp.showAlert("Wrong end date format!");
                }
                endDate = new java.sql.Date(dateEndTmp.getTime());
            }

            if (!(endR.equals(""))) {
                try {
                    dateEndRealTmp = format.parse(endR);
                } catch (ParseException e) {
                    mainApp.showAlert("Wrong real end date format!");
                }
                endRealDate = new java.sql.Date(dateEndRealTmp.getTime());
            }

            java.sql.Date beginDate = new java.sql.Date(dateBegTmp.getTime());

            Project project = new Project(name.getText(), Integer.parseInt(cost.getText()), depId,
                    beginDate, endDate, endRealDate);
            projectsService.addProject(project);
            mainApp.openMainPage(0);
        }
    }

    @FXML
    public void cancel() {
        mainApp.openMainPage(0);
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
