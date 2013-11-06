package hu.legare.contextconfig.security.domain;

public interface CredentialRepository {

    void save(Credential credential);

    Credential findByLogin(String login);

}