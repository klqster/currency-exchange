package com.currencyexchange.controllers;

import com.currencyexchange.App;
import com.currencyexchange.models.MoneyModel;
import com.currencyexchange.models.OperationModel;
import com.currencyexchange.utilities.CommonUtils;
import com.currencyexchange.views.InitialView;
import com.currencyexchange.views.ViewFactory;
import com.currencyexchange.views.dialogs.AlertView;
import com.currencyexchange.views.dialogs.ConfirmationView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class OperationController {
    public BorderPane mainBorderPane;
    public Button exitButton;
    public VBox vBox;
    public Tab operationTab;
    public Tab moneyTab;
    public TableView<OperationModel> operationTable;
    public TableView<MoneyModel> moneyTable;
    public Button backButton;
    private ComboBox<String> currencyCB;
    private TextField amountTF;
    private ComboBox<String> typeCB;

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
        CommonUtils.configureOperationTable(operationTable);
        CommonUtils.configureMoneyTable(moneyTable);
        configureInputFields();
        configureConfirmationButton();
    }

    private void configureInputFields() {
        Label currencyLabel = new Label("Валюта");
        ObservableList<String> opt = MoneyModel.getAllCurrencies();
        opt.add("<Не обрано>");
        ComboBox<String> currencyCB = new ComboBox<>(opt);
        currencyCB.setValue("<Не обрано>");
        Label amountLabel = new Label("Сума");
        TextField amountTF = new TextField();
        Label typeLabel = new Label("Тип операції");
        ObservableList<String> types = FXCollections.observableArrayList("<Не обрано>", "Продаж", "Купівля");
        ComboBox<String> typeCB = new ComboBox<>(types);
        typeCB.setValue("<Не обрано>");

        vBox.getChildren().addAll(currencyLabel, currencyCB, amountLabel, amountTF, typeLabel, typeCB);

        this.currencyCB = currencyCB;
        this.amountTF = amountTF;
        this.typeCB = typeCB;
    }

    private void configureConfirmationButton() {
        Button confirmButton = new Button("Підтвердити");
        confirmButton.setOnAction(e -> {
            if (isValid(currencyCB.getValue(), amountTF.getText(), typeCB.getValue())) {
                performOperation(currencyCB.getValue(), amountTF.getText(), typeCB.getValue());
                moneyTable.setItems(MoneyModel.getMoneyModels(""));
                operationTable.setItems(OperationModel.getOperations(""));
            } else {
                AlertView.showAlert("Будь ласка, перевірте усі поля.");
            }
        });

        vBox.getChildren().add(confirmButton);
    }

    private void performOperation(String currency, String amount, String type) {
        double a = Double.parseDouble(amount);
        double rate = getRate(currency, type);

        if (rate == 0) {
            return;
        }

        String confirmationMessage = generateConfirmationMessage(a, rate, type);

        if (ConfirmationView.showConfirmation(confirmationMessage)) {
            updateMoneyModels(currency, a, rate, type);
            OperationModel.addOperation(new OperationModel(currency, a, rate, type, LocalDateTime.now()));
        }
    }

    private double getRate(String currency, String type) {
        double rate;
        if (Objects.equals(type, "Продаж")) {
            rate = MoneyModel.getMoneyModels(currency).get(0).getSell();
        } else {
            rate = MoneyModel.getMoneyModels(currency).get(0).getBuy();
        }
        return rate;
    }

    private String generateConfirmationMessage(double amount, double rate, String type) {
        String action = (Objects.equals(type, "Продаж")) ? "сплати клієнтом" : "видачі";
        double totalAmount = amount * rate;
        return "До " + action + ": " + totalAmount + " UAH. Клієнт згоден?";
    }

    private void updateMoneyModels(String currency, double amount, double rate, String type) {
        double uah = MoneyModel.getMoneyModels("UAH").get(0).getAmount();
        double cur = MoneyModel.getMoneyModels(currency).get(0).getAmount();

        if (Objects.equals(type, "Продаж")) {
            MoneyModel.updateAmount("UAH", uah + amount * rate);
            MoneyModel.updateAmount(currency, cur - amount);
        } else {
            MoneyModel.updateAmount(currency, cur + amount);
            MoneyModel.updateAmount("UAH", uah - amount * rate);
        }
    }

    private static boolean isValid(String currency, String amount, String type) {
        if (!CommonUtils.isValidAmount(amount)) return false;
        if (!CommonUtils.isValidType(type)) return false;
        double uah = MoneyModel.getMoneyModels("UAH").get(0).getAmount();
        double cur = MoneyModel.getMoneyModels(currency).get(0).getAmount();
        if (Objects.equals(type, "Продаж")) {
            return !(cur < Double.parseDouble(amount));
        }
        else {
            double rate = MoneyModel.getMoneyModels(currency).get(0).getBuy();
            return !(uah < Double.parseDouble(amount) * rate);
        }
    }
}
