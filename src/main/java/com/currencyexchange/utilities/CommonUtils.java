package com.currencyexchange.utilities;

import com.currencyexchange.models.MoneyModel;
import com.currencyexchange.models.OperationModel;
import com.currencyexchange.models.OrderModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.Objects;

public class CommonUtils {
    public static void configureMoneyTable(TableView<MoneyModel> moneyTable) {
        TableColumn<MoneyModel, String> currencyColumn1 = new TableColumn<>("Валюта");
        currencyColumn1.setCellValueFactory(new PropertyValueFactory<>("Currency"));
        TableColumn<MoneyModel, Double> amountColumn1 = new TableColumn<>("Сума");
        amountColumn1.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        TableColumn<MoneyModel, Double> sellColumn = new TableColumn<>("Курс продажу");
        sellColumn.setCellValueFactory(new PropertyValueFactory<>("Sell"));
        TableColumn<MoneyModel, Double> buyColumn = new TableColumn<>("Курс закупівлі");
        buyColumn.setCellValueFactory(new PropertyValueFactory<>("Buy"));
        moneyTable.getColumns().addAll(currencyColumn1, amountColumn1, sellColumn, buyColumn);
        moneyTable.setItems(MoneyModel.getMoneyModels(""));
    }

    public static void configureOperationTable(TableView<OperationModel> operationTable) {
        TableColumn<OperationModel, String> currencyColumn = new TableColumn<>("Валюта");
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("Currency"));
        TableColumn<OperationModel, Double> amountColumn = new TableColumn<>("Сума");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        TableColumn<OperationModel, String> typeColumn = new TableColumn<>("Тип операції");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        TableColumn<OperationModel, Double> rateColumn = new TableColumn<>("Курс валют");
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("Rate"));
        TableColumn<OperationModel, LocalDateTime> dateColumn = new TableColumn<>("Дата і час");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        operationTable.getColumns().addAll(currencyColumn, amountColumn, typeColumn, rateColumn, dateColumn);
        operationTable.setItems(OperationModel.getOperations(""));
    }

    public static void configureOrderTable(TableView<OrderModel> orderTable) {
        TableColumn<OrderModel, String> currencyColumn1 = new TableColumn<>("Валюта");
        currencyColumn1.setCellValueFactory(new PropertyValueFactory<>("currency"));
        TableColumn<OrderModel, Double> amountColumn1 = new TableColumn<>("Сума");
        amountColumn1.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TableColumn<OrderModel, String> bankColumn = new TableColumn<>("Банк");
        bankColumn.setCellValueFactory(new PropertyValueFactory<>("bank"));
        TableColumn<OrderModel, LocalDateTime> dateColumn = new TableColumn<>("Дата");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderTable.getColumns().addAll(currencyColumn1, amountColumn1, bankColumn, dateColumn);
        orderTable.setItems(OrderModel.getOrders());
    }

    public static boolean isValidOption(String option) {
        return !Objects.equals(option, "<Не обрано>");
    }

    public static boolean isValidAmount(String amount) {
        try {
            double a = Double.parseDouble(amount);
            return a > 0;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static boolean isValidType(String type) {
        return Objects.equals(type, "Продаж") || Objects.equals(type, "Купівля");
    }
}
