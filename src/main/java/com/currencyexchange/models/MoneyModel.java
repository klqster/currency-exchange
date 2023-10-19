package com.currencyexchange.models;

import com.currencyexchange.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Data
public class MoneyModel {
    private String currency;
    private double amount;
    private double buy;
    private double sell;

    public MoneyModel(String currency, double amount, double buy, double sell) {
        this.currency = currency;
        this.amount = amount;
        this.buy = buy;
        this.sell = sell;
    }

    public static ObservableList<MoneyModel> getMoneyModels(String currency) {
        String query = "SELECT * FROM Money WHERE 1 = 1";
        if (!currency.isEmpty()) {
            query += " AND Currency = ?";
        }
        try (PreparedStatement statement = App.connection.prepareStatement(query)) {
            if (!currency.isEmpty()) {
                statement.setString(1, currency);
            }
            ResultSet rs = statement.executeQuery();
            ObservableList<MoneyModel> res = FXCollections.observableArrayList();
            while (rs.next()) {
                String rCurrency = rs.getString("Currency");
                double rAmount = rs.getDouble("Amount");
                double rBuy = rs.getDouble("Buy");
                double rSell = rs.getDouble("Sell");
                res.add(new MoneyModel(rCurrency, rAmount, rBuy, rSell));
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<String> getAllCurrencies() {
        String query = "SELECT Currency FROM Money WHERE Currency != 'UAH'";
        try (PreparedStatement statement = App.connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            ObservableList<String> res = FXCollections.observableArrayList();
            while (rs.next()) {
                String rCurrency = rs.getString("Currency");
                res.add(rCurrency);
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static int updateField(String field, String currency, double value) {
        String query = "UPDATE Money SET " + field + " = ? WHERE Currency = ?";
        try (PreparedStatement statement = App.connection.prepareStatement(query)) {
            statement.setDouble(1, value);
            statement.setString(2, currency);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int updateAmount(String currency, double amount) {
        return updateField("Amount", currency, amount);
    }

    public static int updateBuy(String currency, double buy) {
        return updateField("Buy", currency, buy);
    }

    public static int updateSell(String currency, double sell) {
        return updateField("Sell", currency, sell);
    }
}
