package com.udtt.backend.admin.entity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class forEncoding {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoded = encoder.encode("12345678");
        System.out.println();
        System.out.println(encoded);
    }
}
