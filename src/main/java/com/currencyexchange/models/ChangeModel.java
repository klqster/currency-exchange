package com.currencyexchange.models;

import lombok.Data;

@Data
public class ChangeModel {
    private String currency;
    private double buy;
    private double sell;
    private double old;

    public ChangeModel(String currency, double buy, double sell) {
        this.currency = currency;
        this.buy = buy;
        this.sell = sell;
    }

    public static boolean isAllowedSell(String currency, double amount) {
        double old = MoneyModel.getMoneyModels(currency).get(0).getSell();
        return !(Math.abs(amount - old) > old / 10);
    }

    public static boolean isAllowedBuy(String currency, double amount) {
        double old = MoneyModel.getMoneyModels(currency).get(0).getBuy();
        return !(Math.abs(amount - old) > old / 10);
    }
}
