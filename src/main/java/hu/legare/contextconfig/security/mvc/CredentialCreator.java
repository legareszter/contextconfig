package hu.legare.contextconfig.security.mvc;

public interface CredentialCreator {

    void create(String login, String password);

}
