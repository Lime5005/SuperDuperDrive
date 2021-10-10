package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    private FileMapper fileMapper;
    private UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    // Read
    public String[] getFilesListByUserId(Integer userId){
        return fileMapper.getListByUserId(userId);

    }

    public File getFileByName(String fileName){
        return fileMapper.getByName(fileName);
    }

    // Create
    public boolean uploadFile(MultipartFile multipartFile, Integer userId){
        File newFile = new File();
        try {
            String fileName= multipartFile.getOriginalFilename();
            byte[] bytes = multipartFile.getBytes();
            String contentType=multipartFile.getContentType();
            String fileSize = String.valueOf(multipartFile.getSize());

            newFile.setUserId(userId);
            newFile.setFileName(fileName);
            newFile.setContentType(contentType);
            newFile.setFileData(bytes);
            newFile.setFileSize(fileSize);

            fileMapper.add(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Delete
    public boolean deleteFile(String fileName){
        fileMapper.delete(fileName);
        return true;
    }

}
