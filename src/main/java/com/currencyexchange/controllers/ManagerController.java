package com.currencyexchange.controllers;

import com.currencyexchange.App;
import com.currencyexchange.views.InitialView;
import com.currencyexchange.views.ViewFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ManagerController {
    public BorderPane mainBorderPane;
    public Button exitButton;
    public Button orderButton;

    public void onExitButtonClick(ActionEvent actionEvent) {
        App.currentUser = null;
        InitialView.showAuthorizationScene(App.stage);
    }

    public void onOrderButtonClick(ActionEvent actionEvent) {
        try {
            ViewFactory.showOrderScene(App.stage);
        } catch (IOException ignored) {}
    }
}
