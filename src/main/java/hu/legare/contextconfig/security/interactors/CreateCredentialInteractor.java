package hu.legare.contextconfig.security.interactors;

import hu.legare.contextconfig.security.domain.Credential;
import hu.legare.contextconfig.security.domain.CredentialRepository;
import hu.legare.contextconfig.security.mvc.CredentialCreator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateCredentialInteractor implements CredentialCreator {

    @Autowired
    private CredentialRepository credentialRepository;

    @Transactional
    @Override
    public void create(String login, String password) {
        credentialRepository.save(new Credential(login, password));
    }

}
