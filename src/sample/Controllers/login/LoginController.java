package sample.Controllers.login;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Controllers.Controller;
import sample.Entity.User;
import sample.Main;
import sample.Service.UsersService;
import java.sql.Connection;

public class LoginController implements Controller {
    private Main mainApp;
    private User user;
    private Connection connection;

    @FXML
    public TextField uLogin;

    @FXML
    public PasswordField uPassword;

    public LoginController() {}

    @FXML
    void onLoginClicked() {
        String login = uLogin.getText();
        String password = uPassword.getText();

        if (login.equals("")) {
            mainApp.showAlert("Enter username!");
            return;
        } else if (password.equals("")) {
            mainApp.showAlert("Enter password!");
            return;
        }

        UsersService usersService = new UsersService(connection);
        User user = usersService.getUserByLogin(login);
        this.user = user;

        if (!user.getPassword().equals(password)) {
            mainApp.showAlert("Wrong password!");
            return;
        }
        mainApp.setUser(this.user);
        mainApp.openMainPage(0);
    }

    public void setMainApp(Main main) {
        this.mainApp = main;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void updatePage() { }

    @Override
    public void setTab(int idTab) { }
}
