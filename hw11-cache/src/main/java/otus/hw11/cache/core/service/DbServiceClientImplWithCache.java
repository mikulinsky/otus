package otus.hw11.cache.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw11.cache.core.dao.ClientDao;
import otus.hw11.cache.core.model.Client;
import otus.hw11.cache.core.sessionmanager.SessionManager;
import otus.hw11.cache.hwcache.HwCache;

import java.util.Optional;

public class DbServiceClientImplWithCache implements DBServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceClientImplWithCache.class);

    private final ClientDao clientDao;
    private final HwCache<Long, Client> clientCache;

    public DbServiceClientImplWithCache(ClientDao clientDao, HwCache<Long, Client> clientCache) {
        this.clientDao = clientDao;
        this.clientCache = clientCache;
    }

    @Override
    public long saveClient(Client client) {
        try (SessionManager sessionManager = clientDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long clientId = clientDao.insertOrUpdate(client);
                sessionManager.commitSession();
                clientCache.put(clientId, client);
                logger.debug("created client: {}", clientId);
                return clientId;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<Client> getClient(long id) {
        Client clientInCache = clientCache.get(id);

        if (clientInCache != null) {
            return Optional.of(clientInCache);
        }

        return this.getClientFromDB(id);
    }

    private Optional<Client> getClientFromDB(long id) {
        try (SessionManager sessionManager = clientDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Client> clientOptional = clientDao.findById(id);

                logger.debug("client: {}", clientOptional.orElse(null));
                return clientOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
