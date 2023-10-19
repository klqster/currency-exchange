package com.currencyexchange.views.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfirmationView {
    public static boolean showConfirmation(String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Підтвердження");
        alert.setHeaderText(text);

        ButtonType buttonTypeYes = new ButtonType("Так");
        ButtonType buttonTypeNo = new ButtonType("Ні");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        var wrapper = new Object(){ boolean res = false; };

        alert.showAndWait().ifPresent(response -> { if (response == buttonTypeYes) wrapper.res = true; });
        return wrapper.res;
    }
}
