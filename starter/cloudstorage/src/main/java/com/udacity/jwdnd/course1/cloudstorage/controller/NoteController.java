package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/home/note-add")
    public String noteAdd(Authentication authentication, @ModelAttribute("newNote") NoteForm newNote, Model model) {
        Note note = new Note();
        Integer userid = this.userService.getUser(authentication.getName()).getUserid();
        note.setNoteid(newNote.getNoteid());
        note.setUserid(userid);
        note.setNotetitle(newNote.getNotetitle());
        note.setNotedescription(newNote.getNotedescription());
        if (this.noteService.getNote(note.getNoteid()) != null) {
            this.noteService.updateNote(note);
        } else {
            this.noteService.createNote(note);
        }
        model.addAttribute("notesList", this.noteService.getNoteFormsList(userid));
        return "redirect:/home#nav-notes";
    }

    @PostMapping("/home/note-delete")
    public String noteDelete(Authentication authentication, @RequestParam("noteid") Integer noteid, Model model) {
        this.noteService.deleteNote(noteid);
        model.addAttribute("notesList", this.noteService.getNoteFormsList(this.userService.getUser(authentication.getName()).getUserid()));
        return "redirect:/home#nav-notes";
    }
}
