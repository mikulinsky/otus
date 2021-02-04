package otus.hw09.jdbc.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw09.jdbc.db.helper.MetaData;
import otus.hw09.jdbc.db.helper.QueryHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class DBExecutor<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Connection connection;
    private final MetaData<T> metaData;

    private final String SELECT_QUERY;
    private final String INSERT_QUERY;
    private final String UPDATE_QUERY;
    private final String EXISTS_QUERY;
    private final String CREATE_TABLE_QUERY;

    public DBExecutor(Class<T> clazz) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://192.168.99.100:3306/hw09?createDatabaseIfNotExist=true";
        String username = "root";
        String password = "password";
        this.connection = DriverManager.getConnection(url, username, password);
        this.metaData = new MetaData<>(clazz);
        this.SELECT_QUERY = QueryHelper.getSelectQuery(this.metaData);
        this.INSERT_QUERY = QueryHelper.getInsertQuery(this.metaData);
        this.UPDATE_QUERY = QueryHelper.getUpdateQuery(this.metaData);
        this.EXISTS_QUERY = QueryHelper.getExistsQuery(this.metaData);
        this.CREATE_TABLE_QUERY = QueryHelper.getCreateTableQuery(this.metaData);
    }

    public void insert(T obj) {
        try {
            metaData.getIdField().setAccessible(true);
            try {
                long id = (long) this.metaData.getIdField().get(obj);
                if (isExist(id)) {
                    update(obj);
                } else {
                    create(obj);
                }
            } catch (NullPointerException ignored) {
                create(obj);
            }
        } catch (IllegalAccessException e) {
            logger.warn("Error insert in database");
        } finally {
            this.metaData.getIdField().setAccessible(false);
        }
    }

    public T select(Integer id) {
        T result = null;
        try (PreparedStatement statement = this.connection.prepareStatement(this.SELECT_QUERY)) {
            statement.setObject(1, id);
            statement.executeQuery();
            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    result = this.metaData.getClazz().getConstructor().newInstance();
                    for (Field field : this.metaData.getFields()) {
                        try {
                            field.setAccessible(true);
                            field.set(result, resultSet.getObject(field.getName()));
                        } finally {
                            field.setAccessible(false);
                        }
                    }
                }
            } catch (InvocationTargetException | InstantiationException e) {
                logger.warn("Failed create new entity in select");
            }
        } catch (SQLException | IllegalAccessException | NoSuchMethodException e) {
            logger.warn("Failed select entity");
        }
        return result;
    }

    public void createTable() {
        try (PreparedStatement statement = this.connection.prepareStatement(this.CREATE_TABLE_QUERY)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Failed create table {}", this.metaData.getName());
        }
    }

    private void create(T objectData) {
        try (PreparedStatement statement = this.connection.prepareStatement(this.INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            this.objectFieldsToStatement(statement, objectData);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    try {
                        this.metaData.getIdField().setAccessible(true);
                        this.metaData.getIdField().set(objectData, generatedKeys.getObject(1));
                    } finally {
                        this.metaData.getIdField().setAccessible(false);
                    }
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            logger.warn("Failed create entity");
        }
    }

    private void update(T objectData) {
        try (PreparedStatement statement = this.connection.prepareStatement(this.UPDATE_QUERY)) {
            this.objectFieldsToStatement(statement, objectData);
            try {
                this.metaData.getIdField().setAccessible(true);
                statement.setObject(this.metaData.getFieldsWithoutId().size() + 1,
                        this.metaData.getIdField().get(objectData));
            } finally {
                this.metaData.getIdField().setAccessible(false);
            }
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            logger.warn("Failed update entity");
        }
    }

    private boolean isExist(long id) {
        boolean result = false;
        try( PreparedStatement statement = this.connection.prepareStatement(this.EXISTS_QUERY)) {
            statement.setLong(1, id);
            statement.executeQuery();
            try(ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            logger.warn("Failed check entity from database");
        } catch (NullPointerException ignored) {}
        return result;
    }

    private void objectFieldsToStatement(PreparedStatement statement, T obj) throws IllegalAccessException, SQLException {
        for (int i = 0; i < this.metaData.getFieldsWithoutId().size(); ++i) {
            Field field = this.metaData.getFieldsWithoutId().get(i);
            try {
                field.setAccessible(true);
                statement.setObject(i+1, field.get(obj));
            } finally {
                field.setAccessible(false);
            }
        }
    }
}
