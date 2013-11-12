package hu.legare.contextconfig.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Credential {

    @Id
    @GeneratedValue(generator = "credentialSeq")
    @SequenceGenerator(name = "credentialSeq", sequenceName = "credential_seq", allocationSize = 1)
    private Integer id;

    @NotNull
    @Column(unique = true, nullable = false, length = 255, updatable = false)
    private String login;

    @NotNull
    @Column(nullable = false, length = 255)
    private String password;

    public Credential() {
    }

    public Credential(String login, String password) {
        this.login = login;
        this.password = password;
    }

    Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Credential other = (Credential) obj;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        return true;
    }

}
