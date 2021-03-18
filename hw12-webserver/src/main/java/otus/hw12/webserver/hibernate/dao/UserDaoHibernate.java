package otus.hw12.webserver.hibernate.dao;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw12.webserver.core.dao.UserDao;
import otus.hw12.webserver.core.dao.UserDaoException;
import otus.hw12.webserver.core.model.User;
import otus.hw12.webserver.core.sessionmanager.SessionManager;
import otus.hw12.webserver.hibernate.sessionmanager.DatabaseSessionHibernate;
import otus.hw12.webserver.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.List;
import java.util.Optional;

public class UserDaoHibernate implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public UserDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public Optional<User> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(User.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByName(String name) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();

        User user = null;
        Query query = currentSession.getHibernateSession().createQuery("SELECT u FROM User u WHERE u.name=:name");
        query.setParameter("name", name);
        try {
            user = (User) query.getSingleResult();
        } catch (Exception ignored) {
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        return currentSession.getHibernateSession().createQuery("SELECT c FROM User c", User.class).getResultList();
    }

    @Override
    public long insert(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.persist(user);
            hibernateSession.flush();
            return user.getId();
        } catch (Exception e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(user);
        } catch (Exception e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public long insertOrUpdate(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (user.getId() > 0) {
                hibernateSession.merge(user);
            } else {
                hibernateSession.persist(user);
                hibernateSession.flush();
            }
            return user.getId();
        } catch (Exception e) {
            throw new UserDaoException(e);
        }
    }


    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
