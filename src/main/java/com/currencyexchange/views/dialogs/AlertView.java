package com.currencyexchange.views.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertView {
    public static void showAlert(String text) {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Увага");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
