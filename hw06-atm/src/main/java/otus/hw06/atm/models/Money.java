package otus.hw06.atm.models;

import otus.hw06.atm.exceptions.NotValidMoney;
import otus.hw06.atm.util.ConsoleHelper;

public enum Money {
    M5000, M2000, M1000, M500, M200, M100, M50, M10, M5, M2, M1;

    public static Money getMoney(String money) throws NotValidMoney {
        try {
            return Money.valueOf("M" + money);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotValidMoney();
        }
    }

    public Integer toInt() {
        return Integer.parseInt(this.name().substring(1));
    }
}
