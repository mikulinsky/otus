package otus.hw09.jdbc.db.helper;

import otus.hw09.jdbc.annotation.Id;
import otus.hw09.jdbc.exception.DBException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MetaData<T> {

    private final Field idField;
    private final List<Field> fields;
    private final List<Field> fieldsWithoutId;
    private final String name;
    private final Class<T> clazz;

    public MetaData(Class<T> clazz) {
        this.idField = this.getIdField(clazz);
        this.fields = Arrays.asList(clazz.getDeclaredFields());
        this.fieldsWithoutId = this.getFieldsWithoutId(clazz);
        this.name = clazz.getSimpleName();
        this.clazz = clazz;
    }

    public Field getIdField() {
        return idField;
    }

    public List<Field> getFields() {
        return fields;
    }

    public Constructor<T> getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new DBException(e.toString());
        }

    }

    public String getName() {
        return name;
    }

    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }

    private Field getIdField(Class<T> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class))
                return field;
        }
        return null;
    }

    private List<Field> getFieldsWithoutId(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(field -> !field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }

}
