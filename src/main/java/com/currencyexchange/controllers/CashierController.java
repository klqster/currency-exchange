package com.currencyexchange.controllers;

import com.currencyexchange.App;
import com.currencyexchange.views.InitialView;
import com.currencyexchange.views.ViewFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class CashierController {
    public BorderPane mainBorderPane;
    public Button exitButton;
    public Button exchangeButton;
    public Button changeButton;

    public void onExitButtonClick(ActionEvent actionEvent) {
        App.currentUser = null;
        InitialView.showAuthorizationScene(App.stage);
    }

    public void onExchangeButtonClick(ActionEvent actionEvent) {
        try {
            ViewFactory.showOperationScene(App.stage);
        } catch (IOException ignored) {}
    }

    public void onChangeButtonClick(ActionEvent actionEvent) {
        try {
            ViewFactory.showChangeScene(App.stage);
        } catch (IOException ignored) {}
    }
}
