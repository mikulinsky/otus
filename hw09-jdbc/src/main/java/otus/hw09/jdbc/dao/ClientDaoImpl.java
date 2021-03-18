package otus.hw09.jdbc.dao;

import otus.hw09.jdbc.db.DBExecutorImpl;
import otus.hw09.jdbc.db.JdbcMapper;
import otus.hw09.jdbc.db.JdbcMapperImpl;
import otus.hw09.jdbc.models.Client;
import otus.hw09.jdbc.sessionManager.SessionManager;
import otus.hw09.jdbc.sessionManager.SessionManagerImpl;

import java.util.Optional;

public class ClientDaoImpl implements ClientDao {

    private final JdbcMapper<Client> jdbcMapper;
    private final SessionManager sessionManager;

    public ClientDaoImpl(SessionManagerImpl sessionManager, DBExecutorImpl<Client> dbExecutor) {
        this.sessionManager = sessionManager;
        this.jdbcMapper = new JdbcMapperImpl<>(sessionManager, dbExecutor, Client.class);
    }

    @Override
    public Optional<Client> findById(long id) {
        return Optional.ofNullable(this.jdbcMapper.findById(id));
    }

    @Override
    public long insert(Client client) {
        this.jdbcMapper.insert(client);
        return client.getId();
    }

    @Override
    public SessionManager getSessionManager() {
        return this.sessionManager;
    }
}
