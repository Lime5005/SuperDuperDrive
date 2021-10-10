package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.NoteDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserService userService;


    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    // Read
    public List<Note> getNotesByUserId(Integer userId){
        return noteMapper.getNotesByUserId(userId);
    }

    public List<Note> getAllNotes(){
        return noteMapper.getAllNotes();
    }

    // Create / Update
    public boolean insertOrUpdateNote(NoteDto newNote, String username){

        Integer userId = userService.getUserByUsername(username).getUserId();

        if (newNote.getNoteId() == null) {
            Note note = new Note();
            note.setUserId(userId);
            note.setNoteTitle(newNote.getNoteTitle());
            note.setNoteDescription(newNote.getNoteDescription());
            noteMapper.add(note);
        } else {
            noteMapper.update(newNote.getNoteId(), newNote.getNoteTitle(), newNote.getNoteDescription());
        }
        return true;
    }

    // Delete
    public boolean deleteNote(Integer noteId){
        noteMapper.delete(noteId);
        return true;
    }

}
