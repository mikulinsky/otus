package otus.hw07.todo.processor.homework;

import otus.hw07.todo.model.Message;
import otus.hw07.todo.processor.Processor;

import java.time.Instant;

public class ErrorProcessor implements Processor {
    @Override
    public Message process(Message message) {
        if (Instant.now().getEpochSecond() % 2 == 0)
            throw new RuntimeException();
        return message;
    }
}
