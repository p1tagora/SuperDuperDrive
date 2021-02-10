package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/home/credential-add")
    public String credentialAdd(Authentication authentication, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        Credential credential = new Credential();
        Integer userid = this.userService.getUser(authentication.getName()).getUserid();
        credential.setCredentialid(newCredential.getCredentialid());
        credential.setUserid(userid);
        credential.setUrl(newCredential.getUrl());
        credential.setUsername(newCredential.getUsername());
        credential.setPassword(newCredential.getDecryptedpassword());
        if (this.credentialService.getCredential(credential.getCredentialid()) != null) {
            this.credentialService.updateCredential(credential);
        } else {
            this.credentialService.createCredential(credential);
        }
        model.addAttribute("credentialsList", this.credentialService.getCredentialFormsList(userid));
        return "redirect:/home#nav-credentials";
    }

    @PostMapping("/home/credential-delete")
    public String credentialDelete(Authentication authentication, @RequestParam("credentialid") Integer credentialid, Model model) {
        this.credentialService.deleteCredential(credentialid);
        model.addAttribute("credentialsList", this.credentialService.getCredentialFormsList(this.userService.getUser(authentication.getName()).getUserid()));
        return "redirect:/home#nav-credentials";
    }
}
