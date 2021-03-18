package otus.hw15.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sequence {
    private static final Logger logger = LoggerFactory.getLogger(Sequence.class);
    private Integer currentNumber = 1;
    private Integer counter = 1;

    synchronized void action(Integer init) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                while ((currentNumber + init) % 2 != 0) {
                    this.wait();
                }

                logger.info("Поток {}: {}", init, currentNumber);

                if ((currentNumber == 10 && counter == 1) || (currentNumber == 1 && counter == -1))
                    counter = -counter; // При достижении крайних положений меняем знак счетчика

                currentNumber += counter;
                sleep();
                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new NotInterestingException(ex);
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
