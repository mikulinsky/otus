package otus.hw06.atm;

import otus.hw06.atm.exceptions.BalanceException;
import otus.hw06.atm.models.Money;
import otus.hw06.atm.util.ConsoleHelper;

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

    public Integer pushMoney(Money money) {
        return balance += money.toInt();
    }

    public Integer getMoney(Integer money) {
        if (balance < money)
            throw new BalanceException();
        return balance -= money;
    }

    public Boolean ValidateCardNumber(String cardNumber) {
        return User.cardNumber.equals(cardNumber);
    }

    public Boolean ValidatePin(String pin) {
        return User.pin.equals(pin);
    }
}
