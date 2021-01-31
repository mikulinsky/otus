package otus.hw06.atm.commands;

import otus.hw06.atm.User;
import otus.hw06.atm.exceptions.NotValidMoney;
import otus.hw06.atm.models.ATMBalance;
import otus.hw06.atm.models.Money;
import otus.hw06.atm.util.ConsoleHelper;

import java.io.IOException;

public class PushCommand implements Command{
    @Override
    public void execute(User user, ATMBalance atmBalance) {
        ConsoleHelper.write("Введите номнал купюры, что хотите внести (5000, 2000, 1000, 500, 200, 100, 50, 10, 5, 2, 1):");
        try {
            Money money = Money.getMoney(ConsoleHelper.read());
            user.pushMoney(money);
            atmBalance.pushBanknote(money);
            new InfoCommand().execute(user, atmBalance);
        } catch (NotValidMoney em) {
            ConsoleHelper.write("Внесена некорректная купюра");
            this.execute(user, atmBalance);
        } catch (IOException e) {
            ConsoleHelper.write("Ошибка чтения данных");
        }
    }
}
