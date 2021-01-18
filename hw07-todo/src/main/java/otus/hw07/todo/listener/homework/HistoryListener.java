package otus.hw07.todo.listener.homework;

import otus.hw07.todo.listener.Listener;
import otus.hw07.todo.model.Message;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.HashMap;

public class HistoryListener implements Listener {

    private final HashMap<LocalDateTime, AbstractMap.SimpleEntry<Message, Message>> history = new HashMap<>();

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        history.put(LocalDateTime.now(), new AbstractMap.SimpleEntry<>(oldMsg, newMsg));
    }

    public void getHistory() {
        System.out.println("History: ");
        history.forEach((a, b) -> System.out.println(a + ": " + b));
    }
}
