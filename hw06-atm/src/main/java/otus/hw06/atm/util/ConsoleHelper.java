package otus.hw06.atm.util;

import otus.hw06.atm.exceptions.IllegalOperationException;
import otus.hw06.atm.models.Operation;

import java.io.*;

public class ConsoleHelper {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static PrintStream printStream = null;

    static {
        try {
            printStream = new PrintStream(System.out, true, "Windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void write(String msg) {
        if (printStream != null) {
            printStream.println(msg);
        } else {
            System.out.println(msg);
        }
    }

    public static String read() throws IOException {
        return reader.readLine().strip();
    }

    public static Operation getOperation() {
        ConsoleHelper.write("Список доступных операций:");
        ConsoleHelper.write("info - баланс на счету");
        ConsoleHelper.write("push - пополнить баланс");
        ConsoleHelper.write("get - снять деньги");
        ConsoleHelper.write("exit - завершение работы");
        try {
            String operation = ConsoleHelper.read();
            return Operation.getOperationByName(operation);
        } catch (IOException ignored){
        } catch (IllegalOperationException e1) {
            ConsoleHelper.write("Введена неверная операция.");
        }
        return getOperation();
    }
}
