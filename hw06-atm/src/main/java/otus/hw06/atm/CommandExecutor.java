package otus.hw06.atm;

import otus.hw06.atm.commands.*;
import otus.hw06.atm.models.ATMBalance;
import otus.hw06.atm.models.Operation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private static final Map<Operation, Command> commands = new HashMap<>(){{
        put(Operation.LOGIN, new LoginCommand());
        put(Operation.INFO, new InfoCommand());
        put(Operation.EXIT, new ExitCommand());
        put(Operation.PUSH, new PushCommand());
        put(Operation.GET, new GetCommand());
    }};

    private static final ATMBalance atmBalance = new ATMBalance();

    public static void execute(Operation operation, User user) throws IOException {
        commands.get(operation).execute(user, atmBalance);
    }
}
