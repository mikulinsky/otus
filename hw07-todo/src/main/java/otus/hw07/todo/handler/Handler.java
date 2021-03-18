package otus.hw07.todo.handler;

import otus.hw07.todo.model.Message;
import otus.hw07.todo.listener.Listener;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);
    void removeListener(Listener listener);
}
