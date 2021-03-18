package otus.hw07.todo.processor.homework;

import otus.hw07.todo.model.Message;
import otus.hw07.todo.processor.Processor;

public class SubProcessor implements Processor {

    @Override
    public Message process(Message message) {
        return message.toBuilder().field11(message.getField12()).field12(message.getField11()).build();
    }
}
