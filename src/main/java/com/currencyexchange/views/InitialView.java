package com.currencyexchange.views;

import com.currencyexchange.App;
import com.currencyexchange.controllers.UserController;
import com.currencyexchange.views.dialogs.AlertView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class InitialView {

    private static Scene loginScene;
    private static Scene registrationScene;

    public static void showAuthorizationScene(Stage stage) {
        Label titleLabel = new Label("Авторизація");
        Label loginLabel = new Label("Логін:");
        Label passwordLabel = new Label("Пароль:");
        TextField loginTextField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Авторизуватися");
        Button createAccountButton = new Button("Зареєструватись");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setColumnSpan(titleLabel, 2);
        gridPane.add(loginLabel, 0, 1);
        gridPane.add(loginTextField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(loginButton, 0, 3, 2, 1);
        gridPane.add(createAccountButton, 0, 4, 2, 1);

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(gridPane);

        loginScene = new Scene(vbox, 250, 200);
        stage.setScene(loginScene);
        stage.show();

        createAccountButton.setOnAction(event -> showRegistrationScene(stage));
        loginButton.setOnAction(e -> {
            int authorized = UserController.authorize(loginTextField.getText(), passwordField.getText());
            try {
                switch (authorized) {
                    case 0 -> {
                        if (Objects.equals(App.currentUser.getRole(), "Касир")) {
                            ViewFactory.showCashierScene(stage);
                        } else {
                            ViewFactory.showManagerScene(stage);
                        }
                    }
                    case 1 -> AlertView.showAlert("Некоректний логін або пароль!");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void showRegistrationScene(Stage stage) {
        Label titleLabel = new Label("Реєстрація");
        titleLabel.setAlignment(Pos.CENTER);
        Label loginLabel = new Label("Логін:");
        Label passwordLabel = new Label("Пароль:");
        Label confirmPasswordLabel = new Label("Підтвердження пароля:");
        Label roleLabel = new Label("Посада:");
        TextField loginTextField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        ObservableList<String> roles = FXCollections.observableArrayList("<Не обрано>", "Касир", "Менеджер");
        ComboBox<String> roleComboBox = new ComboBox<>(roles);
        roleComboBox.setValue("<Не обрано>");
        Button registerButton = new Button("Зареєструватися");
        Button backButton = new Button("Назад");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(15);
        gridPane.setVgap(10);

        gridPane.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setColumnSpan(titleLabel, 2);
        gridPane.add(loginLabel, 0, 1);
        gridPane.add(loginTextField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(confirmPasswordLabel, 0, 3);
        gridPane.add(confirmPasswordField, 1, 3);
        gridPane.add(roleLabel, 0, 4);
        gridPane.add(roleComboBox, 1, 4);
        gridPane.add(registerButton, 0, 5, 2, 1);
        gridPane.add(backButton, 0, 6, 2, 1);

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(gridPane);

        registrationScene = new Scene(vbox, 350, 270);
        stage.setScene(registrationScene);

        registerButton.setOnAction(event -> {
            int registered = UserController.register(loginTextField.getText(), passwordField.getText(), confirmPasswordField.getText(), roleComboBox.getValue());
            try {
                switch (registered) {
                    case 0 -> {
                        if (Objects.equals(App.currentUser.getRole(), "Касир")) {
                            ViewFactory.showCashierScene(stage);
                        } else {
                            ViewFactory.showManagerScene(stage);
                        }
                    }
                    case 1 -> AlertView.showAlert("Занадто короткий логін!");
                    case 2 -> AlertView.showAlert("Занадто короткий пароль!");
                    case 3 -> AlertView.showAlert("Ви не обрали посаду!");
                    case 4 -> AlertView.showAlert("Користувач з таки логіном вже існує!");
                    case 5 -> AlertView.showAlert("Паролі не співпадають!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        backButton.setOnAction(event -> {
            stage.setScene(loginScene);
        });
    }
}
