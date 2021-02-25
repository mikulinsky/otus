package otus.hw09.jdbc.dao;

import otus.hw09.jdbc.db.DBExecutorImpl;
import otus.hw09.jdbc.db.JdbcMapper;
import otus.hw09.jdbc.db.JdbcMapperImpl;
import otus.hw09.jdbc.models.Account;
import otus.hw09.jdbc.sessionManager.SessionManager;
import otus.hw09.jdbc.sessionManager.SessionManagerImpl;

import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private final JdbcMapper<Account> jdbcMapper;
    private final SessionManager sessionManager;

    public AccountDaoImpl(SessionManagerImpl sessionManager, DBExecutorImpl<Account> dbExecutor) {
        this.sessionManager = sessionManager;
        this.jdbcMapper = new JdbcMapperImpl<>(sessionManager, dbExecutor, Account.class);
    }

    @Override
    public Optional<Account> findById(long id) {
        return Optional.ofNullable(this.jdbcMapper.findById(id));
    }

    @Override
    public long insert(Account account) {
        this.jdbcMapper.insert(account);
        return account.getId();
    }

    @Override
    public SessionManager getSessionManager() {
        return this.sessionManager;
    }
}
