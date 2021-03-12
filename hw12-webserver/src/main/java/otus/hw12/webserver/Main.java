package otus.hw12.webserver;

import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw12.webserver.core.dao.UserDao;
import otus.hw12.webserver.core.model.User;
import otus.hw12.webserver.core.model.UserRole;
import otus.hw12.webserver.core.service.DBServiceUser;
import otus.hw12.webserver.core.service.DbServiceUserImpl;
import otus.hw12.webserver.flyway.MigrationsExecutorFlyway;
import otus.hw12.webserver.hibernate.HibernateUtils;
import otus.hw12.webserver.hibernate.dao.UserDaoHibernate;
import otus.hw12.webserver.hibernate.sessionmanager.SessionManagerHibernate;
import otus.hw12.webserver.server.UsersWebServer;
import otus.hw12.webserver.server.UsersWebServerWithFilterBasedSecurity;
import otus.hw12.webserver.services.TemplateProcessor;
import otus.hw12.webserver.services.TemplateProcessorImpl;
import otus.hw12.webserver.services.UserAuthService;
import otus.hw12.webserver.services.UserAuthServiceImpl;

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

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, User.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        logger.info("Create user");
        User user = new User("User", "usrPassword", UserRole.USER);
        logger.info("Create admin");
        User admin = new User("Admin", "admPassword", UserRole.ADMIN);

        logger.info("Save user");
        long userId = dbServiceUser.saveUser(user);
        logger.info("Save user");
        long adminId = dbServiceUser.saveUser(admin);

        Gson gson = new Gson();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl("/templates/");
        UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

        UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(
                8080,
                authService,
                dbServiceUser,
                gson,
                templateProcessor
        );
        try {
            usersWebServer.start();
            usersWebServer.join();
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }
}