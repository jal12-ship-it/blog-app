package com.blogapp;

import org.junit.jupiter.api.AfterAll;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPass = "password";
        String encodedPassword = encoder.encode(rawPass);

        System.out.println(encodedPassword);
    }

}
