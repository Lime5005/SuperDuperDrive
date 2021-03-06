package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Controller
@RequestMapping("/file")
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }


    @GetMapping(
            value = "/get-file/{fileName}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody byte[] getFile(@PathVariable String fileName) {
        return fileService.getFileByName(fileName).getFileData();
    }

    @PostMapping
    public String uploadFile(Authentication authentication,
                             @RequestParam("fileUpload") MultipartFile file,
                             Model model){

        boolean success = false;
        boolean isDuplicate = false;
        int errorType = 1;
        Integer userId = userService.getUserByUsername(authentication.getName()).getUserId();
        String[] filesList = fileService.getFilesListByUserId(userId);

        if (file.getSize() < 3 * 1024 * 1024) {
            for (String fileName : filesList) {
                if (Objects.equals(file.getOriginalFilename(), fileName)){
                    isDuplicate = true;
                    errorType = 3;
                }
            }
            if (!isDuplicate && !file.isEmpty()){
                success = fileService.uploadFile(file, userId);
                errorType = 0;
            }
        } else {
            return "error-400";
        }

        model.addAttribute("success", success);
        model.addAttribute("errorType", errorType);
        return "result";
    }

    @GetMapping("/delete-file/{fileName}")
    public String deleteFile(@PathVariable String fileName,
                             Model model){
        boolean success = false;
        Integer errorType = 1;

        success = fileService.deleteFile(fileName);

        model.addAttribute("success", success);
        model.addAttribute("errorType", errorType);
        return "result";
    }

}
