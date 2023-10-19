module com.currencyexchange.currency_exchange {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires lombok;

    opens com.currencyexchange to javafx.fxml;

    exports com.currencyexchange;
    exports com.currencyexchange.controllers;
    exports com.currencyexchange.models;
    exports com.currencyexchange.views;
    exports com.currencyexchange.views.dialogs;
}