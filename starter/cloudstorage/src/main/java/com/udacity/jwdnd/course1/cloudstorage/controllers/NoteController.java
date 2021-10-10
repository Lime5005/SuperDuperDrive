package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.dto.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public String insertOrUpdateNote(Authentication authentication,
                                     @ModelAttribute("newNote") NoteDto newNote,
                                     Model model){
        boolean success = false;
        int errorType = 1;
        String username = authentication.getName();

        success = noteService.insertOrUpdateNote(newNote, username);

        model.addAttribute("success", success);
        model.addAttribute("errorType", errorType);
        return "result";

    }

    @GetMapping(value = "/delete/{noteId}")
    public String deleteNote(Authentication authentication,
                             @PathVariable Integer noteId,
                             Model model){

        Integer errorType = 1;
        boolean success = false;

        success = noteService.deleteNote(noteId);

        model.addAttribute("success", success);
        model.addAttribute("errorType", errorType);
        return "result";
    }
}
