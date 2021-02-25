package otus.hw09.jdbc.dao;

import otus.hw09.jdbc.models.Client;
import otus.hw09.jdbc.sessionManager.SessionManager;

import java.util.Optional;

public interface ClientDao {
    Optional<Client> findById(long id);
    //List<Client> findAll();

    long insert(Client client);

    //void update(Client client);
    //long insertOrUpdate(Client client);

    SessionManager getSessionManager();
}
