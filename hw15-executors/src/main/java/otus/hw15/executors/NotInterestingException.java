package otus.hw15.executors;

public class NotInterestingException extends RuntimeException {
    NotInterestingException(InterruptedException ex) {
        super(ex);
    }
}
