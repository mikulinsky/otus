package otus.hw09.jdbc.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw09.jdbc.db.helper.MetaData;
import otus.hw09.jdbc.db.helper.QueryHelper;
import otus.hw09.jdbc.exception.DBException;
import otus.hw09.jdbc.sessionManager.SessionManagerImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private final SessionManagerImpl sessionManager;
    private final DBExecutor<T> dbExecutor;
    private final MetaData<T> metaData;

    private final String SELECT_QUERY;
    private final String INSERT_QUERY;

    public JdbcMapperImpl(SessionManagerImpl sessionManager, DBExecutor<T> dbExecutor, Class<T> clazz) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.metaData = new MetaData<>(clazz);
        this.SELECT_QUERY = QueryHelper.getSelectQuery(this.metaData);
        this.INSERT_QUERY = QueryHelper.getInsertQuery(this.metaData);
    }

    @Override
    public void insert(T obj) {
        List<Object> values = getValues(obj);
        try {
            dbExecutor.executeInsert(getConnection(), this.INSERT_QUERY, values);
        } catch (SQLException e) {
            throw new DBException(e.toString());
        }
    }

    @Override
    public void update(T objectData) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void insertOrUpdate(T objectData) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public T findById(Object id) {
        try {
            return dbExecutor.executeSelect(getConnection(), this.SELECT_QUERY, id, this::handlerResultSet).orElse(null);
        } catch (SQLException e) {
            throw new DBException(e.toString());
        }
    }

    private T handlerResultSet(ResultSet rs) {
        try {
            if (rs.next()) {
                Constructor<T> tConstructor = this.metaData.getConstructor();
                T obj = tConstructor.newInstance();
                List<Field> fields = this.metaData.getFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(obj, rs.getObject(field.getName()));
                }
                return obj;
            }
            return null;
        } catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            logger.error(e.toString());
        }
        return null;
    }

    private List<Object> getValues(T objectData) {
        List<Field> fields = this.metaData.getFields();
        return fields.stream().map(field -> {
            try {
                field.setAccessible(true);
                return field.get(objectData);
            } catch (IllegalAccessException e) {
                throw new DBException(e.toString());
            }
        }).collect(Collectors.toList());
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
