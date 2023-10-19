package com.currencyexchange.controllers;

import com.currencyexchange.App;
import com.currencyexchange.models.ChangeModel;
import com.currencyexchange.models.MoneyModel;
import com.currencyexchange.utilities.CommonUtils;
import com.currencyexchange.views.InitialView;
import com.currencyexchange.views.ViewFactory;
import com.currencyexchange.views.dialogs.AlertView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class ChangeController {
    public BorderPane mainBorderPane;
    public Button backButton;
    public Button exitButton;
    public VBox vBox;
    public TableView<MoneyModel> moneyTable;
    public ComboBox<String> currencyCB;
    public TextField sellTF;
    public TextField buyTF;

    public void onExitButtonClick() {
        App.currentUser = null;
        InitialView.showAuthorizationScene(App.stage);
    }

    public void onBackButtonClick() {
        try {
            ViewFactory.showCashierScene(App.stage);
        } catch (IOException ignored) {}
    }

    public void setupLayout() {
        CommonUtils.configureMoneyTable(moneyTable);
        configureCurrencySelection();
        configureChangeConfirmation();
    }

    private void configureCurrencySelection() {
        Label currencyLabel = new Label("Валюта");
        ObservableList<String> opt = MoneyModel.getAllCurrencies();
        opt.add("<Не обрано>");
        ComboBox<String> currencyCB = new ComboBox<>(opt);
        currencyCB.setValue("<Не обрано>");
        Label sellLabel = new Label("Курс продажу");
        TextField sellTF = new TextField();
        Label buyLabel = new Label("Курс купівлі");
        TextField buyTF = new TextField();
        currencyCB.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(newValue, "<Не обрано>")) {
                sellTF.setText(Double.toString(MoneyModel.getMoneyModels(newValue).get(0).getSell()));
                buyTF.setText(Double.toString(MoneyModel.getMoneyModels(newValue).get(0).getBuy()));
            }
        });
        vBox.getChildren().addAll(currencyLabel, currencyCB, sellLabel, sellTF, buyLabel, buyTF);
    }

    private void configureChangeConfirmation() {
        Button confirmButton = new Button("Підтвердити");
        confirmButton.setOnAction(e -> {
            if (!isValidInput(currencyCB.getValue(), sellTF.getText(), buyTF.getText())) {
                AlertView.showAlert("Будь ласка, перевірте усі поля.");
                return;
            }
            performChange(currencyCB.getValue(), sellTF.getText(), buyTF.getText());
            moneyTable.setItems(MoneyModel.getMoneyModels(""));
        });
        vBox.getChildren().add(confirmButton);
    }

    public static void performChange(String currency, String sell, String buy) {
        MoneyModel.updateSell(currency, Double.parseDouble(sell));
        MoneyModel.updateBuy(currency, Double.parseDouble(buy));
    }

    private boolean isValidInput(String currency, String sell, String buy) {
        if (sell.isEmpty() || buy.isEmpty() || !CommonUtils.isValidOption(currency)) {
            return false;
        }

        try {
            double sellValue = Double.parseDouble(sell);
            double buyValue = Double.parseDouble(buy);

            if (!ChangeModel.isAllowedSell(currency, sellValue) || !ChangeModel.isAllowedBuy(currency, buyValue) || sellValue <= buyValue) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
