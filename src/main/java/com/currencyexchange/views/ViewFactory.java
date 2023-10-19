package com.currencyexchange.views;

import com.currencyexchange.controllers.ChangeController;
import com.currencyexchange.controllers.OperationController;
import com.currencyexchange.controllers.OrderController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    public static void showCashierScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/cashier.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene(scene);
        stage.show();
    }

    public static void showOperationScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/operation.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        OperationController controller = fxmlLoader.getController();
        controller.setupLayout();
        stage.setScene(scene);
        stage.show();
    }

    public static void showChangeScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/change.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        ChangeController controller = fxmlLoader.getController();
        controller.setupLayout();
        stage.setScene(scene);
        stage.show();
    }

    public static void showManagerScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/manager.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene(scene);
        stage.show();
    }

    public static void showOrderScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/order.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        OrderController controller = fxmlLoader.getController();
        controller.setupLayout();
        stage.setScene(scene);
        stage.show();
    }
}
