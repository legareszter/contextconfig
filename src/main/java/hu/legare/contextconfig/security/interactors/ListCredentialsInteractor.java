package hu.legare.contextconfig.security.interactors;

import hu.legare.contextconfig.security.domain.Credential;
import hu.legare.contextconfig.security.domain.CredentialRepository;
import hu.legare.contextconfig.security.mvc.CredentialLister;
import hu.legare.contextconfig.security.mvc.ShowCredential;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCredentialsInteractor implements CredentialLister {

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public List<ShowCredential> listAll() {
        List<Credential> all = credentialRepository.findAll();
        List<ShowCredential> credentials = convertForResponse(all);
        return credentials;
    }

    private List<ShowCredential> convertForResponse(List<Credential> all) {
        List<ShowCredential> credentials = new ArrayList<ShowCredential>();
        for (Credential c : all)
            credentials.add(new ShowCredential(c.getLogin()));

        return credentials;
    }

}
