package otus.hw11.cache.core.dao;

import otus.hw11.cache.core.model.Client;
import otus.hw11.cache.core.sessionmanager.SessionManager;

import java.util.Optional;


public interface ClientDao {
    Optional<Client> findById(long id);
    //List<Client> findAll();

    long insert(Client client);

    void update(Client client);

    long insertOrUpdate(Client client);

    SessionManager getSessionManager();
}
