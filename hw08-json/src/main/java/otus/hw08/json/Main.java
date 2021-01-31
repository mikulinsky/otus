package otus.hw08.json;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Logger logger = LoggerFactory.getLogger(Main.class);
        Gson gson = new Gson();
        Collection<String> testCollection = Collections.singleton("test");
        TestObject testObject = new TestObject(10, "test", testCollection);
        String jsonObject = CustomGSON.toJson(testObject);
        logger.info(jsonObject);

        TestObject testObject1 = gson.fromJson(jsonObject, TestObject.class);
        logger.info("testObject equals testObject1 is " + testObject.equals(testObject1));
    }
}