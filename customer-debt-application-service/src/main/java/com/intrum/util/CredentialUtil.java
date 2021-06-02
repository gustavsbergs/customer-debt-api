package com.intrum.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class CredentialUtil {

    private static final PasswordEncoder encoder = new Pbkdf2PasswordEncoder();

    public String hash(String credential) {
        return encoder.encode(credential);
    }

}
