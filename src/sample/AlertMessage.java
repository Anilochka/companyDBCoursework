package sample;

import javafx.scene.control.Alert;

public class AlertMessage {
    public void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Wrong input");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
