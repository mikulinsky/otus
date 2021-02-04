package otus.hw09.jdbc.db.helper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

public class QueryHelper<T> {

    public static <T> String getInsertQuery(MetaData<T> classMetaData) {
        return "INSERT INTO " + classMetaData.getName() + "(" +
                classMetaData.getFieldsWithoutId().stream()
                        .map(Field::getName).collect(Collectors.joining(", ")) +
                ") VALUES (" + String.join(", ", Collections.nCopies(
                        classMetaData.getFieldsWithoutId().size(), "?"))
                + ")";
    }

    public static <T> String getUpdateQuery(MetaData<T> classMetaData) {
        return "UPDATE " + classMetaData.getName() + " SET " +
                classMetaData.getFieldsWithoutId().stream().map(field -> field.getName() + " = ?")
                        .collect(Collectors.joining(", ")) +
                " WHERE " + classMetaData.getIdField().getName() + " = ?";
    }

    public static <T> String getSelectQuery(MetaData<T> classMetaData) {
        return "SELECT * FROM " + classMetaData.getName() + " WHERE " +
                classMetaData.getIdField().getName() + " = ?";
    }

    public static <T> String getExistsQuery(MetaData<T> classMetaData) {
        return "SELECT 1 FROM " + classMetaData.getName() + " WHERE " +
                classMetaData.getIdField().getName() + " = ?";
    }

    public static <T> String getCreateTableQuery(MetaData<T> classMetaData) {
        HashMap<Class<?>, String> javaTypesToSQL = new HashMap<>() {{
            put(String.class, "VARCHAR(255)");
            put(Integer.class, "INT");
            put(long.class, "BIGINT");
            put(BigDecimal.class, "DECIMAL");
            put(Double.class, "DOUBLE");
            put(Float.class, "FLOAT");
        }};
        Field idField = classMetaData.getIdField();
        String idFieldCreate = "";
        String primaryKeyId = "";
        if (idField != null) {
            idFieldCreate = idField.getName() + " INT NOT NULL AUTO_INCREMENT, ";
            primaryKeyId = ", PRIMARY KEY ( " + idField.getName() + ")";
        }
        return "CREATE TABLE if not exists " + classMetaData.getName() + "(" + idFieldCreate +
                classMetaData.getFieldsWithoutId().stream()
                        .map(field -> field.getName() + " " + javaTypesToSQL.get(field.getType()) + " NOT NULL")
                .collect(Collectors.joining(", ")) + primaryKeyId + ");";
    }

}
