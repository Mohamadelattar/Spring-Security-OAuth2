package com.openclassroom.login;

import com.openclassroom.login.controller.LoginController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SpringSecurityAuthApplicationTests {
    @Autowired
    private LoginController loginController;
    @Test
    void contextLoads() {
    assertThat(loginController).isNotNull();
    }

}
