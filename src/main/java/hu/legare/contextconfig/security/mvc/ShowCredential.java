package hu.legare.contextconfig.security.mvc;

public class ShowCredential {

    public String login;

    public ShowCredential(String login) {
        this.login = login;
    }

    public ShowCredential() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
