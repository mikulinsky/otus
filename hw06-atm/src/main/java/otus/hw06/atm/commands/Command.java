package otus.hw06.atm.commands;

import otus.hw06.atm.User;
import otus.hw06.atm.models.ATMBalance;

import java.io.IOException;

public interface Command {
    void execute(User user, ATMBalance atmBalance) throws IOException;
}
