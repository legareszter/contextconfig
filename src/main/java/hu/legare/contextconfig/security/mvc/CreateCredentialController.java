package hu.legare.contextconfig.security.mvc;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/credential", produces = "text/html")
public class CreateCredentialController {

    @Autowired
    private CredentialCreator credentialCreator;

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String getForm(Model uiModel) {
        uiModel.addAttribute("newCredential", new NewCredential());
        return "credential/new";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid NewCredential newCredential, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "credential/new";

        credentialCreator.create(newCredential.getLogin(), newCredential.getPassword());
        return "redirect:/credentials";
    }
}
