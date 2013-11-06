package hu.legare.contextconfig.security.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class DatabaseCredentialRepository implements CredentialRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Credential credential) {
        if (credential.getId() == null) {
            entityManager.persist(credential);
        } else {
            entityManager.merge(credential);
        }
    }

    @Override
    public Credential findByLogin(String login) {
        return entityManager.createQuery("SELECT c FROM Credential c WHERE c.login = :login", Credential.class)
                .setParameter("login", login).getSingleResult();
    }
}
