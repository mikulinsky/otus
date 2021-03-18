package otus.hw10.hibernate.core.service;

import otus.hw10.hibernate.core.model.Client;

import java.util.Optional;

public interface DBServiceClient {

    long saveClient(Client client);

    Optional<Client> getClient(long id);

    //List<Client> findAll();
}
