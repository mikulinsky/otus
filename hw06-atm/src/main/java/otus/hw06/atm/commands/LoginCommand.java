package otus.hw06.atm.commands;

import otus.hw06.atm.User;
import otus.hw06.atm.models.ATMBalance;
import otus.hw06.atm.util.ConsoleHelper;

import java.io.IOException;

public class LoginCommand implements Command {
    @Override
    public void execute(User user, ATMBalance atmBalance) throws IOException {
        ConsoleHelper.write("Введите номер карты (в формате: 0000 0000 0000 0000):");
        String cardNumber = ConsoleHelper.read();
        if (!user.validateCardNumber(cardNumber)) {
            ConsoleHelper.write("Введен неверный номер карты. Попробуйте: 0000 0000 0000 0000");
            this.execute(user, atmBalance);
            return;
        }
        ConsoleHelper.write("Введите пин код (0000):");
        String pin = ConsoleHelper.read();
        if (!user.validatePin(pin)) {
            ConsoleHelper.write("Введен неверный пин. Попробуйте: 0000");
            this.execute(user, atmBalance);
        }
    }
}
