package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note) {
        return noteMapper.insert(new Note(null, note.getNotetitle(), note.getNotedescription(), note.getUserid()));
    }

    public Note getNote(Integer noteid) {
        return noteMapper.getNote(noteid);
    }

    public List<Note> getNotesList(Integer userid) {
        return this.noteMapper.getNotesList(userid);
    }

    public List<NoteForm> getNoteFormsList(Integer userid) {
        List<Note> notesList = this.getNotesList(userid);
        List<NoteForm> result = new ArrayList<NoteForm>();
        for (Note note : notesList) {
            result.add(new NoteForm(note.getNoteid(), note.getNotetitle(), note.getNotedescription()));
        }
        return result;
    }

    public void updateNote(Note note) {
        noteMapper.update(note);
    }

    public void deleteNote(Integer noteid) {
        noteMapper.delete(noteid);
    }    
}
