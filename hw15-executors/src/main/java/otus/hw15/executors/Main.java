package otus.hw15.executors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Sequence sequence = new Sequence();
        new Thread(() -> sequence.action(1)).start();
        new Thread(() -> sequence.action(2)).start();
    }
}