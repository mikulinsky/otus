package otus.hw07.todo.processor;

import otus.hw07.todo.model.Message;

public interface Processor {

    Message process(Message message);

    //todo: 2(done). Сделать процессор, который поменяет местами значения field11 и field12
}
