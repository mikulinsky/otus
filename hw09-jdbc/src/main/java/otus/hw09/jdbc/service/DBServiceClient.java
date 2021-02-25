package otus.hw09.jdbc.service;

import otus.hw09.jdbc.models.Client;

import java.util.Optional;

public interface DBServiceClient {
    long saveClient(Client client);

    Optional<Client> getClient(long id);
}
