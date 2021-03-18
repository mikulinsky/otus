package otus.hw09.jdbc.dao;

import otus.hw09.jdbc.models.Account;
import otus.hw09.jdbc.sessionManager.SessionManager;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> findById(long id);

    long insert(Account account);


    SessionManager getSessionManager();
}
