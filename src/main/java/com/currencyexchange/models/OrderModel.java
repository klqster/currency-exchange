package com.currencyexchange.models;

import com.currencyexchange.App;
import com.currencyexchange.controllers.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

@Data
public class OrderModel {
    private int id;
    private String currency;
    private double amount;
    private String bank;
    private LocalDateTime date;

    public OrderModel (int id, String currency, double amount, String bank, LocalDateTime date) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
        this.bank = bank;
        this.date = date;
    }

    public static ObservableList<OrderModel> getOrders() {
        try {
            Statement statement = App.connection.createStatement();
            String sql = "SELECT * FROM `Order`";
            ObservableList<OrderModel> res = FXCollections.observableArrayList();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int rID = rs.getInt("id");
                String rCurrency = rs.getString("Currency");
                double rAmount = rs.getDouble("Amount");
                String rBank = rs.getString("Bank");
                LocalDateTime rDate = LocalDateTime.parse(rs.getString("Date").replace(" ", "T"));
                res.add(new OrderModel(rID, rCurrency, rAmount, rBank, rDate));
            }
            statement.close();
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int addOrder(OrderModel model) {
        try {
            Statement statement = App.connection.createStatement();
            String sql = "INSERT INTO `Order` (Currency, Amount, Bank, Date) VALUES" +
                    " ('" + model.currency + "', " + model.amount + ", '" + model.bank + "', '" + model.date + "')";
            System.out.println(sql);
            int res = statement.executeUpdate(sql);
            statement.close();
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
