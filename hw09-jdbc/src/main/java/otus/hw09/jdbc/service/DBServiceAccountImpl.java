package otus.hw09.jdbc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw09.jdbc.dao.AccountDao;
import otus.hw09.jdbc.exception.DBServiceException;
import otus.hw09.jdbc.models.Account;

import java.util.Optional;

public class DBServiceAccountImpl implements DBServiceAccount{
    private static final Logger logger = LoggerFactory.getLogger(DBServiceAccountImpl.class);

    private final AccountDao accountDao;

    public DBServiceAccountImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public long saveAccount(Account account) {
        try (var sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var clientId = accountDao.insert(account);
                sessionManager.commitSession();

                logger.info("created client: {}", clientId);
                return clientId;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DBServiceException(e.toString());
            }
        }
    }

    @Override
    public Optional<Account> getAccount(long id) {
        try (var sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Account> clientOptional = accountDao.findById(id);

                logger.info("client: {}", clientOptional.orElse(null));
                return clientOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
