package otus.hw09.jdbc;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw09.jdbc.dao.AccountDao;
import otus.hw09.jdbc.dao.AccountDaoImpl;
import otus.hw09.jdbc.dao.ClientDao;
import otus.hw09.jdbc.dao.ClientDaoImpl;
import otus.hw09.jdbc.db.DBExecutorImpl;
import otus.hw09.jdbc.db.DataSourceImpl;
import otus.hw09.jdbc.db.JdbcMapper;
import otus.hw09.jdbc.db.JdbcMapperImpl;
import otus.hw09.jdbc.models.Account;
import otus.hw09.jdbc.models.Client;
import otus.hw09.jdbc.service.DBServiceAccountImpl;
import otus.hw09.jdbc.service.DBServiceClientImpl;
import otus.hw09.jdbc.sessionManager.SessionManagerImpl;

import javax.sql.DataSource;
import java.util.Optional;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var dataSource = new DataSourceImpl();
        flywayMigrations(dataSource);
        var sessionManager = new SessionManagerImpl(dataSource);

// Работа с пользователем
        DBExecutorImpl<Client> dbExecutorClient = new DBExecutorImpl<>();
        DBExecutorImpl<Account> dbExecutorAccount = new DBExecutorImpl<>();
        JdbcMapper<Client> jdbcMapperClient = new JdbcMapperImpl<>(sessionManager, dbExecutorClient, Client.class); //
        ClientDao clientDao = new ClientDaoImpl(sessionManager, dbExecutorClient);
        AccountDao accountDao = new AccountDaoImpl(sessionManager, dbExecutorAccount);

// Код дальше должен остаться, т.е. clientDao должен использоваться
        var dbServiceClient = new DBServiceClientImpl(clientDao);
        var id = dbServiceClient.saveClient(new Client( "dbServiceClient", 23));
        Optional<Client> clientOptional = dbServiceClient.getClient(id);

        clientOptional.ifPresentOrElse(
                client -> logger.info("created client:{}", client.toString()),
                () -> logger.info("client was not created")
        );
// Работа со счетом

        var dbServiceAccount = new DBServiceAccountImpl(accountDao);
        var accountId = dbServiceAccount.saveAccount(new Account());
        Optional<Account> accountOptional = dbServiceAccount.getAccount(accountId);

        accountOptional.ifPresentOrElse(
                account -> logger.info("created account:{}", account.toString()),
                () -> logger.info("account was not created")
        );
    }

    private static void flywayMigrations(DataSource dataSource) {
        logger.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }
}