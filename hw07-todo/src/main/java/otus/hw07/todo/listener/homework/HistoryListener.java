package otus.hw07.todo.listener.homework;

import otus.hw07.todo.listener.Listener;
import otus.hw07.todo.model.Message;

import java.util.AbstractMap;
import java.util.HashMap;

public class HistoryListener implements Listener {

    private final HashMap<Long, AbstractMap.SimpleEntry<Message, Message>> history = new HashMap<>();
    private Long nextID = 0L;

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        history.put(nextID, new AbstractMap.SimpleEntry<>(oldMsg.clone(), newMsg.clone()));
        nextID++;
    }

    public void getHistory() {
        System.out.println("History: ");
        history.forEach((a, b) -> System.out.println(a + ": " + b));
    }
}
