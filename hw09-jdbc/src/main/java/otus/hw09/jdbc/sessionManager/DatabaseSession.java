package otus.hw09.jdbc.sessionManager;

import java.sql.Connection;

public interface DatabaseSession {
    Connection getConnection();
}
