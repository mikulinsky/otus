package otus.hw11.cache;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw11.cache.core.dao.ClientDao;
import otus.hw11.cache.core.model.AddressDataSet;
import otus.hw11.cache.core.model.Client;
import otus.hw11.cache.core.model.PhoneDataSet;
import otus.hw11.cache.core.service.DBServiceClient;
import otus.hw11.cache.core.service.DbServiceClientImpl;
import otus.hw11.cache.core.service.DbServiceClientImplWithCache;
import otus.hw11.cache.flyway.MigrationsExecutorFlyway;
import otus.hw11.cache.hibernate.HibernateUtils;
import otus.hw11.cache.hibernate.dao.ClientDaoHibernate;
import otus.hw11.cache.hibernate.sessionmanager.SessionManagerHibernate;
import otus.hw11.cache.hwcache.MyCache;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Main.speedTest();
    }

    private static void speedTest() {
        ClientDao clientDao = Main.getClientDao();
        DBServiceClient dbServiceClient = new DbServiceClientImpl(clientDao);
        DBServiceClient dbServiceClientWithCache = new DbServiceClientImplWithCache(clientDao, new MyCache<>());

        logger.info("Test without cache");
        Main.dbServiceSpeedTest(dbServiceClient);
        logger.info("Test with cache");
        Main.dbServiceSpeedTest(dbServiceClientWithCache);
    }

    private static void dbServiceSpeedTest(DBServiceClient dbServiceClient) {
        List<Client> clientsForTest = new ArrayList<>();

        for (int i = 0; i < 100; i++)
            clientsForTest.add(new Client("Client"+i, new AddressDataSet("qwerty")));

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            for (Client client : clientsForTest){
                long savedClientId = dbServiceClient.saveClient(client);
                dbServiceClient.getClient(savedClientId);
            }
        }
        logger.info("time: {}", System.currentTimeMillis() - startTime);
    }

    private static ClientDao getClientDao() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

        String dbUrl = configuration.getProperty("hibernate.connection.url");
        String username = configuration.getProperty("hibernate.connection.username");
        String password = configuration.getProperty("hibernate.connection.password");

        MigrationsExecutorFlyway migrationsExecutorFlyway = new MigrationsExecutorFlyway(dbUrl, username, password);
        migrationsExecutorFlyway.cleanDb();
        migrationsExecutorFlyway.executeMigrations();

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        return new ClientDaoHibernate(sessionManager);
    }
}