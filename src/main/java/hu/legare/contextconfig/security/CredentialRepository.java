package hu.legare.contextconfig.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class CredentialRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Credential credential) {
        if (credential.getId() == null) {
            entityManager.persist(credential);
        } else {
            entityManager.merge(credential);
        }
    }

    public Credential findByLogin(String login) {
        return entityManager.createQuery("SELECT c FROM Credential c WHERE c.login = :login", Credential.class)
                .setParameter("login", login).getSingleResult();
    }
}
