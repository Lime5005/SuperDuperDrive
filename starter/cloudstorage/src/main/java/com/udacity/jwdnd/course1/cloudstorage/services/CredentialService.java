package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.CredentialDto;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    // Read
    public List<Credential> getCredentialsByUserId(Integer userId) {
        return credentialMapper.getByUserId(userId);
    }

    // Create / Update
    public boolean insertOrUpdateCredential(CredentialDto newCredential, String username) {

        Integer userId = userService.getUserByUsername(username).getUserId();
        Integer credentialId = newCredential.getCredentialId();
        String url = newCredential.getUrl();
        String usernameForUrl = newCredential.getUsername();
        String password = newCredential.getPassword();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        if (newCredential.getCredentialId() == null) {
            Credential credential = new Credential();
            credential.setUserId(userId);
            credential.setKey(encodedKey);
            credential.setCredentialId(credentialId);
            credential.setUrl(url);
            credential.setUsername(usernameForUrl);
            credential.setPassword(encryptedPassword);
            credentialMapper.add(credential);
        } else {
            credentialMapper.update(credentialId, url, usernameForUrl,encodedKey, encryptedPassword);
        }
        return true;
    }

    // Delete
    public boolean deleteCredential(Integer credentialId){
        credentialMapper.delete(credentialId);
        return true;
    }
}
