package otus.hw12.webserver.core.dao;

import otus.hw12.webserver.core.model.User;
import otus.hw12.webserver.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;


public interface UserDao {
    Optional<User> findById(long id);

    Optional<User> findByName(String name);

    List<User> findAll();

    long insert(User user);

    void update(User user);

    long insertOrUpdate(User user);

    SessionManager getSessionManager();
}
