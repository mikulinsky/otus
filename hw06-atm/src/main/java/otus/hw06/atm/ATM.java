package otus.hw06.atm;

import otus.hw06.atm.models.Operation;
import otus.hw06.atm.util.ConsoleHelper;

public class ATM {
    public static void main(String[] args) {
        User user = new User();
        Operation operation = Operation.LOGIN;
        while (true){
            try {
                CommandExecutor.execute(operation, user);
            } catch (Exception e) {
                e.printStackTrace();
                ConsoleHelper.write("Error: Во время обработки операции произошла ошибка.");
            }
            operation = ConsoleHelper.getOperation();
        }
    }
}