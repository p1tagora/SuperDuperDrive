package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {
    private final FileService fileService;
    private final CredentialService credentialService;
    private final NoteService noteService;
    private final UserService userService;

    public HomeController(FileService fileService, CredentialService credentialService, NoteService noteService, UserService userService) {
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getFiles(Authentication authentication, Model model) {
        Integer userid = this.userService.getUser(authentication.getName()).getUserid();
        model.addAttribute("newNote", new NoteForm());
        model.addAttribute("newCredential", new CredentialForm());
        model.addAttribute("filesList", this.fileService.getFileFormsList(userid));
        model.addAttribute("notesList", this.noteService.getNoteFormsList(userid));
        model.addAttribute("credentialsList", this.credentialService.getCredentialFormsList(userid));
        return "home";
    }

    @PostMapping("/logout")
    public String doLogout(Authentication authentication) {
        authentication.setAuthenticated(false);
        return "forward:/login";
    }

}
