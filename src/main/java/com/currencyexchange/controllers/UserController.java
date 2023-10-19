package com.currencyexchange.controllers;

import com.currencyexchange.App;
import com.currencyexchange.models.UserModel;

import java.util.Objects;

public class UserController {
    public static int register(String login, String password, String confirmPassword, String role) {
        if (login.length() < 3) return 1;
        if (password.length() < 5) return 2;
        if (Objects.equals(role, "<Не обрано>")) return 3;
        if (UserModel.checkLogin(login)) return 4;
        if (!Objects.equals(password, confirmPassword)) return 5;
        UserModel.createNewUser(new UserModel(login, password, role));
        App.currentUser = UserModel.getUser(login);
        return 0;
    }

    public static int authorize(String login, String password) {
        if (login.length() < 3) return 1;
        if (!UserModel.checkLogin(login)) return 1;
        if (password.length() < 5) return 1;
        if (!UserModel.checkPassword(password, login)) return 1;
        App.currentUser = UserModel.getUser(login);
        return 0;
    }
}
