package com.currencyexchange.models;

import com.currencyexchange.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

@Data
public class UserModel {
    private String login;
    private String password;
    private String role;

    public UserModel(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public static int createNewUser(UserModel model) {
        try {
            Statement statement = App.connection.createStatement();
            String sql = "INSERT INTO User (Login, Password, Role) VALUES ('" + model.login + "', '" + model.password + "', '" + model.role + "')";
            int res = statement.executeUpdate(sql);
            statement.close();
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<UserModel> getUsers(UserModel model) {
        try {
            Statement statement = App.connection.createStatement();
            StringBuilder sb = new StringBuilder("SELECT * FROM User WHERE 1 = 1");
            if (model.login.length() > 0) sb.append(" AND Login = '").append(model.login).append("'");
            if (model.role.length() > 0) sb.append(" AND Role = '").append(model.role).append("'");
            String sql = sb.toString();
            System.out.println(sql);
            ObservableList<UserModel> res = FXCollections.observableArrayList();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String rLogin = rs.getString("Login");
                String rPassword = rs.getString("Password");
                String rRole = rs.getString("Role");
                res.add(new UserModel(rLogin, rPassword, rRole));
            }
            statement.close();
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int updateUser(String login, UserModel model) {
        try {
            Statement statement = App.connection.createStatement();
            DecimalFormat df = new DecimalFormat("#.##");
            String sql = "UPDATE User SET Login = '" + model.login + "', Password = '" + model.password + "', Role = '" + model.role + "' WHERE Login = '" + login + "'";
            int res = statement.executeUpdate(sql);
            statement.close();
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int deleteUser(String login) {
        try {
            Statement statement = App.connection.createStatement();
            String sql = "DELETE FROM User WHERE Login = '" + login + "'";
            int res = statement.executeUpdate(sql);
            statement.close();
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkLogin(String login) {
        try {
            Statement statement = App.connection.createStatement();
            String sql = "SELECT Login FROM User";
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<String> logins = new ArrayList<>();
            while (rs.next()) {
                logins.add(rs.getString("Login"));
            }
            boolean found = false;
            for (String l : logins) {
                if (Objects.equals(l, login)) {
                    found = true;
                    break;
                }
            }
            return found;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkPassword(String password, String login) {
        try {
            Statement statement = App.connection.createStatement();
            String sql = "SELECT Password FROM User WHERE Login = '" + login + "'";
            ResultSet rs = statement.executeQuery(sql);
            String res = "";
            if (rs.next()) {
                res = rs.getString("Password");
            }
            return Objects.equals(res, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserModel getUser(String login) {
        try {
            Statement statement = App.connection.createStatement();
            String sql = "SELECT * FROM User WHERE Login = '" + login + "'";
            ResultSet rs = statement.executeQuery(sql);
            UserModel res = null;
            if (rs.next()) {
                String rLogin = rs.getString("Login");
                String rPassword = rs.getString("Password");
                String rRole = rs.getString("Role");
                res = new UserModel (rLogin, rPassword, rRole);
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
