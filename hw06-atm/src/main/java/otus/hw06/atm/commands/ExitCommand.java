package otus.hw06.atm.commands;

import otus.hw06.atm.User;
import otus.hw06.atm.models.ATMBalance;
import otus.hw06.atm.util.ConsoleHelper;

public class ExitCommand implements Command{
    @Override
    public void execute(User user, ATMBalance atmBalance) {
        ConsoleHelper.write("Завершение работы");
        System.exit(0);
    }
}
