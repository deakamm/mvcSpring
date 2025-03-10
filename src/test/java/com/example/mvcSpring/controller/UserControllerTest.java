package com.example.mvcSpring.controller;

import com.example.mvcSpring.model.User;
import com.example.mvcSpring.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {


    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @DisplayName("Mostrar la lista de todos los usuarios")
    @Test
    public void UserController() {
        final User user = User.builder()
                .username("Umo").phone(65L)
                .first_name("Umo").last_name("Balde")
                .email("ddd@nnn.net")
                .build();

        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));
        List<User> usuarios = userController.getUsers();
        assertNotNull(user);
        assertEquals(1, usuarios.size());
        verify(userService).getAllUsers();
    }

    @DisplayName("Mostrar la lista de todos los usuarios")
    @Test
    public void getUsersTest() {
        Long userId = 1L;
        User esperado = new User(userId, "nmnmn",
                56L, "mmm", "jjjjj",
                "gjggj@kkkk.com");
        User result = new User(userId, "nmnmn",
                56L, "mmm", "jjjjj",
                "gjggj@kkkk.com");

        when(userService.getAllUsers()).thenReturn(List.of());
        userController.getUsers();

        assertEquals(esperado.getId(), result.getId());
        assertEquals(esperado.getFirst_name(), result.getFirst_name());
        assertTrue(true);
        verify(userService).getAllUsers();
    }
    @DisplayName("Mostrar la lista de un usuario si existe el id")
    @Test
    public  void getUserTest() {
        // Given
        Long userId = 1L;
        User user = new User(userId, "kkkk",
                888L, "jjjj",
                "hhhhh", "nnnn");
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        //When
        User resultado = userController.getUser(userId).getBody();

        // Then
        assert resultado != null;
        assertEquals(userId, resultado.getId());
        assertEquals(user.getUsername(), resultado.getUsername());
        assertEquals(user.getPhone(), resultado.getPhone());
        assertEquals(user.getEmail(), resultado.getEmail());
        assertEquals(user.getFirst_name(), resultado.getFirst_name());
        assertEquals(user.getLast_name(), resultado.getLast_name());

        verify(userService).getUserById(userId);
        verifyNoMoreInteractions(userService);
    }
    @DisplayName("Añadir un nuevo usuario con el metode save")
    @Test
    public void setUsersTest(){
        Long userId = 1L;
        User esperado = new User(userId, "nmnmn",
                56L, "mmm", "jjjjj",
                "gjggj@kkkk.com");

        User result = new User(userId, "nmnmn",
                56L, "mmm", "jjjjj",
                "gjggj@kkkk.com");

        when(userService.saveUser(esperado)).thenReturn(result);
        userController.setUsers(esperado);

        assertEquals(esperado.getId(), result.getId());
        assertEquals(esperado.getUsername(), result.getUsername());
        verify(userService).saveUser(esperado);
    }

    @DisplayName("Modificar un usuario con el metodo  updateUser")
    @Test
    public void updateUserTest() {
        Long userId = 1L;
        User esperado = new User(userId, "Mario",
                888L, "Mario", "Gomez",
                "mar@gmail.com");
        final User user = new User(userId, "Mario",
                67L, "Mario", "Gomez",
                "mar@gmail.com");
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        userController.updateUser(userId, user);

        Assertions.assertEquals(esperado.getId(), user.getId());
        assertEquals(esperado.getEmail(), user.getEmail());
        verify(userService).saveUser(user);

    }
    @DisplayName("Borrar el usuario con el metodo deleteUserId")
    @Test
    public void deleteUserIdTest() throws Exception {
        Long deleteUserId = 1L;
        doNothing().when(userService).deleteUser(deleteUserId);
        userController.deleteUserId(deleteUserId);
        mockMvc.perform(delete("/api/user/{id}",deleteUserId))
                .andExpect(status().isOk());
        assertTrue(true);
    }


}
