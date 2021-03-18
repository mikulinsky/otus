package otus.hw09.jdbc.sessionManager;

import java.sql.Connection;

public class DatabaseSessionImpl implements DatabaseSession{

    private final Connection connection;

    public DatabaseSessionImpl(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
