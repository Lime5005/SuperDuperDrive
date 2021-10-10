package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.dto.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.NoteDto;

import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private NoteService noteService;
    private UserService userService;
    private CredentialService credentialService;
    private FileService fileService;
    private EncryptionService encryptionService;

    public HomeController(NoteService noteService, UserService userService,
                          CredentialService credentialService, FileService fileService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.encryptionService = encryptionService;
    }


    @GetMapping
    public String getHome(Authentication authentication,
                          @ModelAttribute("newNote") NoteDto newNote,
                          @ModelAttribute("newCredential") CredentialDto newCredential,
                          Model model){
        Integer userId = userService.getUserId(authentication);

        model.addAttribute("filesList",fileService.getFilesListByUserId(userId));
        model.addAttribute("notes", noteService.getNotesByUserId(userId));
        model.addAttribute("credentials", credentialService.getCredentialsByUserId(userId));
        model.addAttribute("encryptService", encryptionService);

        return "home";
    }
}
