package com.example.mvcSpring;

import com.example.mvcSpring.model.User;
import com.example.mvcSpring.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    User user = User.builder()
            .first_name("user1")
            .last_name("apellido1")
            .phone(91878723L)
            .username("user1")
            .email("user1@gmail.com")
            .build();
    User user2 = User.builder()
            .first_name("user2")
            .last_name("apellido2")
            .phone(91878723L)
            .username("user2")
            .email("user2@gmail.com")
            .build();

    @BeforeEach
    void setUp() {

        userRepository.deleteAll();

    }

    @Test
    public void getUserById_then_return_User() throws Exception {

        User savedUser = userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.username").value(savedUser.getUsername()))
                .andExpect(jsonPath("$.first_name").value(savedUser.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(savedUser.getLast_name()))
                .andExpect(jsonPath("$.email").value(savedUser.getEmail()));
    }

    @Test
    public void getAllUsers_then_return_AllUsers() throws Exception {
        userRepository.save(user);
        userRepository.save(user2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.[0].username").value(user.getUsername()))
                        .andExpect(jsonPath("$.[1].username").value(user2.getUsername()));
    }

    @Test
    public void saveUser_then_return_CREATED() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));

    }

    @Test
    public  void saveUser_then_return_UPDATED() throws Exception{
        User savedUser = userRepository.save(user);
        savedUser.setUsername("user_update");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/{id}", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(savedUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.first_name").value(savedUser.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(savedUser.getLast_name()))
                .andExpect(jsonPath("$.email").value(savedUser.getEmail()))
                .andExpect(jsonPath("$.username").value("user_update"));

    }

    @Test
    public  void deleteUser_then_return_OK_userDeleted() throws Exception{
        User savedUser = userRepository.save(user);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/{id}", savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
