package otus.hw08.json;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class CustomGSON {

    public static String toJson(Object obj) throws IllegalAccessException {
        JSONObject jsonObject = new JSONObject();
        Class clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(obj);
            jsonObject.put(name, value);
        }
        return jsonObject.toString();
    }

}
