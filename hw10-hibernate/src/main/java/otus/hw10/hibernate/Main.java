package otus.hw10.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw10.hibernate.core.dao.ClientDao;
import otus.hw10.hibernate.core.model.AddressDataSet;
import otus.hw10.hibernate.core.model.Client;
import otus.hw10.hibernate.core.model.PhoneDataSet;
import otus.hw10.hibernate.core.service.DBServiceClient;
import otus.hw10.hibernate.core.service.DbServiceClientImpl;
import otus.hw10.hibernate.flyway.MigrationsExecutorFlyway;
import otus.hw10.hibernate.hibernate.HibernateUtils;
import otus.hw10.hibernate.hibernate.dao.ClientDaoHibernate;
import otus.hw10.hibernate.hibernate.sessionmanager.SessionManagerHibernate;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

        String dbUrl = configuration.getProperty("hibernate.connection.url");
        String username = configuration.getProperty("hibernate.connection.username");
        String password = configuration.getProperty("hibernate.connection.password");

        MigrationsExecutorFlyway migrationsExecutorFlyway = new MigrationsExecutorFlyway(dbUrl, username, password);
        migrationsExecutorFlyway.cleanDb();
        migrationsExecutorFlyway.executeMigrations();

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        ClientDao clientDao = new ClientDaoHibernate(sessionManager);
        DBServiceClient dbServiceClient = new DbServiceClientImpl(clientDao);

        logger.info("Create client");
        Client client = new Client("Client", new AddressDataSet("Street"));
        client.addPhone("phone1");
        client.addPhone("phone2");

        logger.info("Save client");
        long id = dbServiceClient.saveClient(client);
        logger.info("Client saved with id = {}", id);

        logger.info("Try get client by id.");
        dbServiceClient.getClient(id).ifPresentOrElse(
                foundedClient -> logger.info("Found client {}", foundedClient),
                () -> logger.info("Client not found")
        );

        logger.info("Update client");
        client.setName("NewNameClient");
        client.setAddress(new AddressDataSet("NewStreet"));
        id = dbServiceClient.saveClient(client);
        logger.info("Client saved with id = {}", id);

        logger.info("Try get client by id.");
        dbServiceClient.getClient(id).ifPresentOrElse(
                foundedClient -> logger.info("Found client {}", foundedClient),
                () -> logger.info("Client not found")
        );
    }
}