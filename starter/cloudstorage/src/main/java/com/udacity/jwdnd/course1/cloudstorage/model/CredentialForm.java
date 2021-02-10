package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CredentialForm {
    private Integer credentialid;
    private String url;
    private String username;
    private String encryptedpassword;
    private String decryptedpassword;
}
