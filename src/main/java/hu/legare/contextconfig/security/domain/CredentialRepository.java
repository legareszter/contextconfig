package hu.legare.contextconfig.security.domain;

import java.util.List;

public interface CredentialRepository {

    void save(Credential credential);

    Credential findByLogin(String login);

    List<Credential> findAll();

}