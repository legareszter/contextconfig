package hu.legare.contextconfig.security.mvc;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewCredential {

    @NotNull
    @Size(min = 8, max = 100)
    public String login;

    @NotNull
    @Size(min = 8, max = 50)
    public String password;

    public NewCredential(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public NewCredential() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
