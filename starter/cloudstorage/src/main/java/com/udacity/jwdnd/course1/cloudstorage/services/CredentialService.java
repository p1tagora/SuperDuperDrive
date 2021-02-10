package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper.insert(new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserid()));
    }

    public Credential getCredential(Integer credentialid) {
        return credentialMapper.getCredential(credentialid);
    }

    public List<Credential> getCredentialList(Integer userid) {
        return credentialMapper.getCredentialsList(userid);
    }

    public List<CredentialForm> getCredentialFormsList(Integer userid) {
        List<Credential> credentialsList = this.getCredentialList(userid);
        List<CredentialForm> result = new ArrayList<CredentialForm>();
        for (Credential credential : credentialsList) {
            result.add(new CredentialForm(credential.getCredentialid(),
                    credential.getUrl(),
                    credential.getUsername(),
                    credential.getPassword(),
                    encryptionService.decryptValue(credential.getPassword(), credential.getKey())));
        }
        return result;
    }

    public void updateCredential(Credential credential) {
        Credential oldcredential = this.getCredential(credential.getCredentialid());
        if (credential.getKey() == null) {
            credential.setKey(oldcredential.getKey());
        }
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        credentialMapper.update(credential);
    }

    public void deleteCredential(Integer credentialid) {
        credentialMapper.delete(credentialid);
    }    
}
