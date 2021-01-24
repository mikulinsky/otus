package otus.hw07.todo;

import otus.hw07.todo.handler.ComplexProcessor;
import otus.hw07.todo.listener.ListenerPrinter;
import otus.hw07.todo.listener.homework.HistoryListener;
import otus.hw07.todo.model.Message;
import otus.hw07.todo.processor.LoggerProcessor;
import otus.hw07.todo.processor.ProcessorConcatFields;
import otus.hw07.todo.processor.ProcessorUpperField10;
import otus.hw07.todo.processor.homework.ErrorProcessor;
import otus.hw07.todo.processor.homework.SubProcessor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
       4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */

        var processors = List.of(new SubProcessor(),
                new ErrorProcessor(LocalDateTime::now));

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {System.out.println(ex.toString());});
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(historyListener);
        historyListener.getHistory();
    }
}
