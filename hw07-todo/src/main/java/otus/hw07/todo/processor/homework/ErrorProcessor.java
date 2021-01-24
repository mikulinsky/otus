package otus.hw07.todo.processor.homework;

import otus.hw07.todo.DateTimeProvider;
import otus.hw07.todo.model.Message;
import otus.hw07.todo.processor.Processor;

public class ErrorProcessor implements Processor {
    private final DateTimeProvider dateTimeProvider;

    public ErrorProcessor(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if (dateTimeProvider.getTime().getSecond() % 2 == 0)
            throw new RuntimeException();
        return message;
    }
}
