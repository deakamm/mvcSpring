package com.example.mvcSpring.users;

import com.example.mvcSpring.model.Phone;
import com.example.mvcSpring.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Test
    public void testConstructor(){
        Phone phone = new Phone(34, 64654654);
        assertEquals(34, phone.getCountryPrefix());
        assertEquals(64654654, phone.getNumber());
        Assertions.assertTrue(true);

        User user = new User(1L, "username",
                65L, "uuuu",
                "ffffff","ddgdf@ggfg.com");

        // Setters
        user.setEmail("email");
        user.setUsername("uuuuu");
        user.setLast_name("last Name");
        user.setFirst_name("First Name");
        // Builder
        User user2 = User.builder()
                .email("email")
                .first_name("first name")
                .last_name("last name")
                .build();
        assertNotNull(user);
        assertNotNull(user2);
    }

}
