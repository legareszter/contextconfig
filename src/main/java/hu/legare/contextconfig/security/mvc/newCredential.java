package hu.legare.contextconfig.security.mvc;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class newCredential {

    @NotNull
    @Size(min = 8, max = 100)
    public String login;

    @NotNull
    @Size(min = 8, max = 50)
    public String password;

    public newCredential(String login, String password) {
        this.login = login;
        this.password = password;
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
