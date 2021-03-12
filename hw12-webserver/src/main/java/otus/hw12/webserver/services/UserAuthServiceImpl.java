package otus.hw12.webserver.services;

import org.slf4j.LoggerFactory;
import otus.hw12.webserver.core.model.User;
import otus.hw12.webserver.core.model.UserRole;
import otus.hw12.webserver.core.service.DBServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceUser dbServiceUser;

    public UserAuthServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String login, String password) {
        var logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Founded user: {}", dbServiceUser.findByName(login).orElse(new User("Not found", "", UserRole.USER)));
        return dbServiceUser.findByName(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}