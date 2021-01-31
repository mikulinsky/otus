package otus.hw06.atm.models;

import otus.hw06.atm.exceptions.BalanceException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ATMBalance {
    private final HashMap<Money, Integer> balance = new HashMap<>();
    private static final List<Integer> moneys = new ArrayList<>();

    {
        for (Money money : Money.values()) {
            this.balance.put(money, 10);
            moneys.add(money.toInt());
        }
        moneys.sort(Collections.reverseOrder());
    }

    public void getBanknote(Money money) {
        if (this.balance.get(money) == 0)
            throw new BalanceException();

        this.balance.put(money, this.balance.get(money)-1);
    }

    public HashMap<Money, Integer> getBanknotesBySum(Integer sum) {
        HashMap<Money, Integer> banknotes = new HashMap<>(){{
            for (Money money : Money.values()) {
                put(money, 0);
            }
        }};
        while (sum > 0) {
            Money tmp = this.getMoneyBySum(sum);
            this.getBanknote(tmp);
            banknotes.put(tmp, banknotes.get(tmp)+1);
            sum -= tmp.toInt();
        }
        return banknotes;
    }

    public void pushBanknote(Money money) {
        this.balance.put(money, this.balance.get(money)+1);
    }

    public boolean checkBanknote(Money money) {
        return this.balance.get(money) != 0;
    }

    private Money getMoneyBySum(Integer sum) {
        for (Integer money : moneys) {
            if (sum >= money){
                Money tmp = Money.getMoney(money.toString());
                if (this.checkBanknote(tmp))
                    return tmp;
            }
        }
        throw new BalanceException();
    }
}
