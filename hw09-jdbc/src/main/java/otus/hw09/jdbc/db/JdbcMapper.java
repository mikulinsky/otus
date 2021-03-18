package otus.hw09.jdbc.db;

public interface JdbcMapper<T> {
    void insert(T objectData);

    void update(T objectData);

    void insertOrUpdate(T objectData);

    T findById(Object id);
}
