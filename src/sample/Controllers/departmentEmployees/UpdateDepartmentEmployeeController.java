package sample.Controllers.departmentEmployees;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import sample.Controllers.Controller;
import sample.Entity.Department;
import sample.Entity.DepartmentEmployee;
import sample.Entity.Employee;
import sample.Main;
import sample.Service.DepartmentsEmployeesService;
import sample.Service.DepartmentsService;
import sample.Service.EmployeesService;
import java.sql.Connection;

public class UpdateDepartmentEmployeeController implements Controller {
    private Main mainApp;
    private Connection connection;
    private DepartmentsEmployeesService departmentsEmployeesService;
    private DepartmentEmployee departmentEmployee;
    private DepartmentsService departmentsService;
    private EmployeesService employeesService;
    private Integer depId;
    private Integer empId;

    @FXML
    Text departmentEmployeeId;

    @FXML
    ComboBox<String> depChoice;

    @FXML
    ComboBox<String> empChoice;

    @Override
    public void updatePage() {
        departmentsEmployeesService = mainApp.getDepartmentsEmployeesService();
        departmentsService = mainApp.getDepartmentsService();
        employeesService = mainApp.getEmployeesService();
        departmentEmployee = mainApp.getDepartmentEmployee();

        departmentEmployeeId.setText(String.valueOf(departmentEmployee.getId()));

        depChoice.setValue(departmentEmployee.getDepartmentName());
        empChoice.setValue(employeesService.getEmployeeById(departmentEmployee.getEmployeeId()).getLastName() + " "
            + employeesService.getEmployeeById(departmentEmployee.getEmployeeId()).getFirstName() + " "
                + employeesService.getEmployeeById(departmentEmployee.getEmployeeId()).getFatherName());
        depId = departmentEmployee.getDepartmentId();
        empId = departmentEmployee.getEmployeeId();

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

        empChoice.setOnMouseClicked(event -> {
            ObservableList<Employee> empList = FXCollections.observableArrayList(employeesService.getEmployees());
            ObservableList<String> newEmpList = FXCollections.observableArrayList();

            for (Employee emp : empList) {
                if (emp != null)
                    newEmpList.add(emp.getLastName() + " " + emp.getFirstName() + " " + emp.getFatherName());
            }

            ObservableList<String> oldEmp = empChoice.getItems();
            if (!newEmpList.equals(oldEmp)) {
                empChoice.setItems(newEmpList);
            }
        });

        empChoice.setOnAction(event -> {
            String emp = empChoice.getValue();
            if (!emp.equals("")) {
                empId = employeesService.getEmployeeByFIO(emp).getId();
            }
        });
    }

    @FXML
    public void updateDepartmentEmployee() {
        String departmentName = departmentsService.getDepartmentById(depId).getName();
        String employeeName = employeesService.getEmployeeById(empId).getLastName() + " " +
                employeesService.getEmployeeById(empId).getFirstName() + " " +
                employeesService.getEmployeeById(empId).getFatherName();

        DepartmentEmployee newDepartmentEmployee = new DepartmentEmployee(departmentEmployee.getId(), depId, empId, departmentName, employeeName);
        departmentsEmployeesService.updateDepartmentEmployee(newDepartmentEmployee, departmentEmployee);
        mainApp.openMainPage(3);
    }

    @FXML
    public void cancel() {
        mainApp.openMainPage(3);
    }

    public DepartmentEmployee getDepartmentEmployee() { return departmentEmployee; }

    public void setDepartmentEmployee(DepartmentEmployee departmentEmployee) { this.departmentEmployee = departmentEmployee; }

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
