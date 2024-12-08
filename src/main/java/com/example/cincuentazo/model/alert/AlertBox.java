package com.example.cincuentazo.model.alert;

import javafx.scene.control.Alert;

public class AlertBox implements IAlertBox {

    @Override
    public void showAlert( String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
