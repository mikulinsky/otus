package otus.hw11.cache.hibernate.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw11.cache.core.dao.ClientDao;
import otus.hw11.cache.core.dao.ClientDaoException;
import otus.hw11.cache.core.model.Client;
import otus.hw11.cache.core.sessionmanager.SessionManager;
import otus.hw11.cache.hibernate.sessionmanager.DatabaseSessionHibernate;
import otus.hw11.cache.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class ClientDaoHibernate implements ClientDao {
    private static final Logger logger = LoggerFactory.getLogger(ClientDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public ClientDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public Optional<Client> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(Client.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long insert(Client client) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.persist(client);
            hibernateSession.flush();
            return client.getId();
        } catch (Exception e) {
            throw new ClientDaoException(e);
        }
    }

    @Override
    public void update(Client client) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(client);
        } catch (Exception e) {
            throw new ClientDaoException(e);
        }
    }

    @Override
    public long insertOrUpdate(Client client) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (client.getId() > 0) {
                hibernateSession.merge(client);
            } else {
                hibernateSession.persist(client);
                hibernateSession.flush();
            }
            return client.getId();
        } catch (Exception e) {
            throw new ClientDaoException(e);
        }
    }


    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
