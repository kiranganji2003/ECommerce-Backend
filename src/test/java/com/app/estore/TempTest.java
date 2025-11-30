package com.app.estore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class TempTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void tempTest() {
        System.out.println(passwordEncoder.encode("pass123"));

        System.out.println(passwordEncoder.matches("pass1213", "$2a$10$n7HZXXqwkD2dfcv9wCTB5eas72QQTousUAid6oNkElAd2x9QeuRgG"));
    }

}
