package com.currencyexchange;

import com.currencyexchange.models.UserModel;
import com.currencyexchange.views.InitialView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App extends Application {

    public static Connection connection;
    public static Stage stage;
    public static UserModel currentUser;

    private final static String url = "jdbc:mysql://localhost:3306/currency_exchange";
    private final static String login = "root";
    private final static String password = "";

    @Override
    public void start(Stage stage) {
        App.stage = stage;
        App.currentUser = null;
        stage.setTitle("Пункт обміну валют");
        InitialView.showAuthorizationScene(stage);
    }

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        launch();
    }
}