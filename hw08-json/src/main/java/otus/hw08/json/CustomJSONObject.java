package otus.hw08.json;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomJSONObject {
    private final HashMap<String, Object> data = new HashMap<>();

    public CustomJSONObject(){

    }

    public void put(String name, Object value) {
        data.put(name, value);
    }

    public String toString() {
        StringBuilder strData = new StringBuilder("{");

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            strData.append(String.format("\"%s\": ", entry.getKey()));
            strData.append(this.getStrByObject(entry.getValue()));
            strData.append(", ");
        }
        strData.setLength(strData.length()-2);
        strData.append("}");
        return strData.toString();
    }

    private String getStrByObject(Object obj) {
        if (obj instanceof String)
            return String.format("\"%s\"", obj);

        if (obj instanceof Collection) {
            Collection<Object> listObjects = (Collection<Object>) obj;
            StringBuilder strData = new StringBuilder("[");
            for (Object item : listObjects) {
                strData.append(this.getStrByObject(item));
                strData.append(", ");
            }
            strData.setLength(strData.length()-2);
            strData.append("]");
            return strData.toString();
        }

        return String.valueOf(obj);
    }
}
