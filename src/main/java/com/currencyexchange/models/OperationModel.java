package com.currencyexchange.models;

import com.currencyexchange.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

@Data
public class OperationModel {
    private String currency;
    private double amount;
    private double rate;
    private String type;
    private LocalDateTime date;

    public OperationModel(String currency, double amount, double rate, String type, LocalDateTime date) {
        this.currency = currency;
        this.amount = amount;
        this.rate = rate;
        this.type = type;
        this.date = date;
    }

    public static ObservableList<OperationModel> getOperations(String currency) {
        try {
            Statement statement = App.connection.createStatement();
            StringBuilder sb = new StringBuilder("SELECT * FROM Operation WHERE 1 = 1");
            if (currency.length() > 0) sb.append(" AND Currency = '").append(currency).append("'");
            String sql = sb.toString();
            System.out.println(sql);
            ObservableList<OperationModel> res = FXCollections.observableArrayList();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String rCurrency = rs.getString("Currency");
                double rAmount = rs.getDouble("Amount");
                double rBuy = rs.getDouble("Rate");
                String rSell = rs.getString("Type");
                LocalDateTime rDate = LocalDateTime.parse(rs.getString("Date").replace(" ", "T"));
                res.add(new OperationModel(rCurrency, rAmount, rBuy, rSell, rDate));
            }
            statement.close();
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int addOperation(OperationModel model) {
        try {
            Statement statement = App.connection.createStatement();
            String sql = "INSERT INTO Operation (Currency, Amount, Type, Rate, Date) VALUES" +
                    " ('" + model.currency + "', " + model.amount + ", '" + model.type + "', '" + model.rate + "', '" + model.date + "')";
            System.out.println(sql);
            int res = statement.executeUpdate(sql);
            statement.close();
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
