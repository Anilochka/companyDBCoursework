package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oracle.jdbc.pool.OracleDataSource;
import sample.Controllers.*;
import sample.Entity.*;
import sample.Service.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    private Stage primaryStage;
    private User user;
    private Connection connection;
    private DepartmentsEmployeesService departmentsEmployeesService;
    private DepartmentsService departmentsService;
    private ProjectsService projectsService;
    private EmployeesService employeesService;
    private UsersService usersService;
    private Project project;
    private Department department;
    private Employee employee;
    private DepartmentEmployee departmentEmployee;
    private AlertMessage alertMessage;

    @Override
    public void start(Stage primaryStage) {
        connection = getConnection();
        departmentsService = new DepartmentsService(connection);
        projectsService = new ProjectsService(connection, departmentsService);
        employeesService = new EmployeesService(connection);
        departmentsEmployeesService = new DepartmentsEmployeesService(connection, departmentsService, employeesService);
        usersService = new UsersService(connection);
        alertMessage = new AlertMessage();

        this.primaryStage = primaryStage;
        openPage("login/Authorization");
        primaryStage.show();
    }

    public Controller openPage(String path) {
        Controller controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlfiles/" + path + "Page.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.setTitle("Company");

            controller = loader.getController();
            controller.setMainApp(this);
            controller.setConnection(connection);
            controller.updatePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }

    public void openMainPage(int idTab) {
        Controller controller = openPage("Main");
        controller.setTab(idTab);
    }

    public Connection getConnection() {
        try {
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            String USER = "C##test";
            String PASSWORD = "MyPass";

            OracleDataSource ds = new OracleDataSource();
            ds.setURL(URL);
            ds.setUser(USER);
            ds.setPassword(PASSWORD);

            try (Connection connection = ds.getConnection()) {

            } catch (SQLException e) {
                throw new RuntimeException("Error while connecting to database", e);
            }

            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public DepartmentsEmployeesService getDepartmentsEmployeesService() { return departmentsEmployeesService; }

    public DepartmentsService getDepartmentsService() { return departmentsService; }

    public ProjectsService getProjectsService() { return projectsService; }

    public EmployeesService getEmployeesService() { return employeesService; }

    public UsersService getUsersService() { return usersService; }

    public Project getProject() { return project; }

    public void setProject(Project project) { this.project = project; }

    public Department getDepartment() { return department; }

    public void setDepartment(Department department) { this.department = department; }

    public Employee getEmployee() { return employee; }

    public void setEmployee(Employee employee) { this.employee = employee; }

    public DepartmentEmployee getDepartmentEmployee() { return departmentEmployee; }

    public void setDepartmentEmployee(DepartmentEmployee departmentEmployee) { this.departmentEmployee = departmentEmployee; }

    public static void main(String[] args) {
        launch(args);
    }

    public void showAlert(String message) {
        alertMessage.showWarningAlert(message);
    }
}
