package com.currencyexchange.controllers;

import com.currencyexchange.App;
import com.currencyexchange.models.MoneyModel;
import com.currencyexchange.models.OrderModel;
import com.currencyexchange.utilities.CommonUtils;
import com.currencyexchange.views.InitialView;
import com.currencyexchange.views.ViewFactory;
import com.currencyexchange.views.dialogs.AlertView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class OrderController {
    public BorderPane mainBorderPane;
    public Button backButton;
    public Button exitButton;
    public VBox vBox;
    public TableView<OrderModel> orderTable;
    public TableView<MoneyModel> moneyTable;
    public Tab moneyTab;
    public Tab orderTab;
    private ComboBox<String> currencyCB;
    private TextField amountTF;
    private ComboBox<String> bankCB;

    public void onBackButtonClick() {
        try {
            ViewFactory.showManagerScene(App.stage);
        } catch (IOException ignored) {}
    }

    public void onExitButtonClick() {
        App.currentUser = null;
        InitialView.showAuthorizationScene(App.stage);
    }

    public void setupLayout() {
        CommonUtils.configureOrderTable(orderTable);
        CommonUtils.configureMoneyTable(moneyTable);
        configureInputFields();
        configureConfirmationButton();
    }

    private void configureInputFields() {
        Label currencyLabel = new Label("Валюта");
        ObservableList<String> opt = MoneyModel.getAllCurrencies();
        opt.addAll("UAH", "<Не обрано>");
        ComboBox<String> currencyCB = new ComboBox<>(opt);
        currencyCB.setValue("<Не обрано>");
        Label amountLabel = new Label("Сума");
        TextField amountTF = new TextField();
        Label bankLabel = new Label("Банк");
        ObservableList<String> banks = FXCollections.observableArrayList("<Не обрано>", "Ексімбанк", "Аурора Фінанс", "Прогрес Банк");
        ComboBox<String> bankCB = new ComboBox<>(banks);
        bankCB.setValue("<Не обрано>");

        vBox.getChildren().addAll(currencyLabel, currencyCB, amountLabel, amountTF, bankLabel, bankCB);

        this.currencyCB = currencyCB;
        this.amountTF = amountTF;
        this.bankCB = bankCB;
    }

    private void configureConfirmationButton() {
        Button confirmButton = new Button("Підтвердити");
        confirmButton.setOnAction(e -> {
            if (!isValid(currencyCB.getValue(), amountTF.getText(), bankCB.getValue())){
                AlertView.showAlert("Будь ласка, перевірте усі поля.");
                return;
            }
            performOrder(currencyCB.getValue(), amountTF.getText(), bankCB.getValue());
            orderTable.setItems(OrderModel.getOrders());
            moneyTable.setItems(MoneyModel.getMoneyModels(""));
        });
        vBox.getChildren().add(confirmButton);
    }

    private static void performOrder(String currency, String amount, String bank) {
        double a = Double.parseDouble(amount);
        OrderModel.addOrder(new OrderModel(-1, currency, a, bank, LocalDateTime.now()));
        double m = MoneyModel.getMoneyModels(currency).get(0).getAmount();
        MoneyModel.updateAmount(currency, m + a);
    }

    private boolean isValid(String currency, String amount, String bank) {
        if (!CommonUtils.isValidAmount(amount)) return false;
        if (!CommonUtils.isValidOption(bank)) return false;
        if (Objects.equals(currency, "UAH")) {
            return !(Double.parseDouble(amount) > 500000);
        } else {
            double rate = MoneyModel.getMoneyModels(currency).get(0).getSell();
            return !(Double.parseDouble(amount) * rate > 500000);
        }
    }
}
