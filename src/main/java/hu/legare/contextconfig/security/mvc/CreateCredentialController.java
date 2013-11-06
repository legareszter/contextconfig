package hu.legare.contextconfig.security.mvc;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreateCredentialController {

    @Autowired
    private CredentialCreator credentialCreator;

    @RequestMapping(value = "/credential", produces = "text/html", method = RequestMethod.POST)
    public String create(@Valid newCredential newCredential, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "credential/new";

        credentialCreator.create(newCredential.getLogin(), newCredential.getPassword());
        return "redirect:/credentials";
    }
}
