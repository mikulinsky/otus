package otus.hw12.webserver.core.service;

import otus.hw12.webserver.core.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> findById(long id);

    Optional<User> findByName(String name);

    Optional<User> findRandomClient();

    List<User> findAll();
}
