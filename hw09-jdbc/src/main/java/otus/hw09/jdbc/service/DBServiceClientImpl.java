package otus.hw09.jdbc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw09.jdbc.dao.ClientDao;
import otus.hw09.jdbc.exception.DBServiceException;
import otus.hw09.jdbc.models.Client;

import java.util.Optional;

public class DBServiceClientImpl implements DBServiceClient{
    private static final Logger logger = LoggerFactory.getLogger(DBServiceClientImpl.class);

    private final ClientDao clientDao;

    public DBServiceClientImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public long saveClient(Client client) {
        try (var sessionManager = clientDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var clientId = clientDao.insert(client);
                sessionManager.commitSession();

                logger.info("created client: {}", clientId);
                return clientId;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DBServiceException(e.toString());
            }
        }
    }

    @Override
    public Optional<Client> getClient(long id) {
        try (var sessionManager = clientDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Client> clientOptional = clientDao.findById(id);

                logger.info("client: {}", clientOptional.orElse(null));
                return clientOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
