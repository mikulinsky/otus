package otus.hw09.jdbc.sessionManager;

import otus.hw09.jdbc.exception.SessionManagerException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SessionManagerImpl implements SessionManager{

    private static final int TIMEOUT_IN_SECONDS = 5;
    private final DataSource dataSource;
    private Connection connection;
    private DatabaseSessionImpl databaseSession;

    public SessionManagerImpl(DataSource dataSource) {
        if (dataSource == null) {
            throw new SessionManagerException("DataSource is null");
        }
        this.dataSource = dataSource;
    }

    @Override
    public void beginSession() {
        try {
            connection = dataSource.getConnection();
            databaseSession = new DatabaseSessionImpl(connection);
        } catch (SQLException e) {
            throw new SessionManagerException(e.toString());
        }
    }

    @Override
    public void commitSession() {
        checkConnection();
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new SessionManagerException(e.toString());
        }
    }

    @Override
    public void rollbackSession() {
        checkConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new SessionManagerException(e.toString());
        }
    }

    @Override
    public void close() {
        checkConnection();
        try {
            connection.close();
        } catch (SQLException e) {
            throw new SessionManagerException(e.toString());
        }
    }

    @Override
    public DatabaseSession getCurrentSession() {
        checkConnection();
        return databaseSession;
    }

    private void checkConnection() {
        try {
            if (connection == null || !connection.isValid(TIMEOUT_IN_SECONDS)) {
                throw new SessionManagerException("Connection is invalid");
            }
        } catch (SQLException ex) {
            throw new SessionManagerException(ex.toString());
        }
    }
}
