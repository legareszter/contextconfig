package hu.legare.contextconfig.security.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/credentials", produces = "text/html", method = RequestMethod.GET)
public class ListCredentialsContoller {

    @Autowired
    private CredentialLister credentialLister;

    @RequestMapping
    public String list(Model uiModel) {
        List<ShowCredential> credentials = credentialLister.listAll();
        uiModel.addAttribute("credentials", credentials);
        return "credential/list";
    }
}
