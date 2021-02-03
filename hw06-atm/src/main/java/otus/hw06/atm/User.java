package otus.hw06.atm;

import otus.hw06.atm.exceptions.BalanceException;
import otus.hw06.atm.models.Money;

public class User {
    private Integer balance;
    private static final String cardNumber = "0000 0000 0000 0000";
    private static final String pin = "0000";

    public User() {
        balance = 10_000;
    }

    public Integer getBalance() {
        return balance;
    }

    public void pushMoney(Money money) {
        balance += money.toInt();
    }

    public void cashingMoney(Integer money) {
        if (balance < money)
            throw new BalanceException();
        balance -= money;
    }

    public Boolean validateCardNumber(String cardNumber) {
        return User.cardNumber.equals(cardNumber);
    }

    public Boolean validatePin(String pin) {
        return User.pin.equals(pin);
    }
}
