package otus.hw06.atm.commands;

import otus.hw06.atm.User;
import otus.hw06.atm.exceptions.BalanceException;
import otus.hw06.atm.exceptions.NotValidMoney;
import otus.hw06.atm.models.ATMBalance;
import otus.hw06.atm.models.Money;
import otus.hw06.atm.util.ConsoleHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetCommand implements Command{
    @Override
    public void execute(User user, ATMBalance atmBalance) {
        ConsoleHelper.write("Введите сумму, что хотите снять:");
        try {
            Integer sum = Integer.parseInt(ConsoleHelper.read());
            HashMap<Money, Integer> banknotes = atmBalance.getBanknotesBySum(sum);
            user.cashingMoney(sum);
            for (Map.Entry<Money, Integer> entity: banknotes.entrySet()){
                if (entity.getValue() == 0)
                    continue;
                ConsoleHelper.write(entity.getKey() + ": " + entity.getValue() + " купюр");
            }
            new InfoCommand().execute(user, atmBalance);
        } catch (BalanceException be) {
            ConsoleHelper.write("Недостаточно средств для осуществления операции");
        } catch (NotValidMoney em) {
            ConsoleHelper.write("Введена некорректная купюра");
            this.execute(user, atmBalance);
        } catch (IOException e) {
            ConsoleHelper.write("Ошибка чтения данных");
        }
    }
}
