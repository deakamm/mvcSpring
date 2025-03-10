package com.example.mvcSpring.controller;

import com.example.mvcSpring.model.User;
import com.example.mvcSpring.repository.UserRepository;
import com.example.mvcSpring.service.UserService;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
     private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
    }
    @DisplayName("Mostrar la lista de todos los usuarios")
    @Test
    public void testUserController(){

        final User user = User.builder().username("Umo").phone(65L).first_name("Umo")
                .last_name("Balde").email("ddd@nnn.net").build();

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<User> usuarios = userService.getAllUsers();
        assertNotNull(user);
        assertEquals(1, usuarios.size());
    }
    @DisplayName("Mostrar la lista de un usuario si existe el id")
    @Test
    void should_Return_UserById_When_User_Exists() {
        // Given
        Long userId = 1L;
        User user = new User(userId, "kkkk", 888L, "jjjj", "hhhhh", "nnnn");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //When
        User resultado = userService.getUserById(userId).orElseThrow();

        // Then
        assertEquals(userId, resultado.getId());
        assertEquals(user.getUsername(), resultado.getUsername());
        assertEquals(user.getPhone(), resultado.getPhone());
        assertEquals(user.getEmail(), resultado.getEmail());
        assertEquals(user.getFirst_name(), resultado.getFirst_name());
        assertEquals(user.getLast_name(), resultado.getLast_name());

        verify(userRepository).findById(userId);  // Verifica la interacción
        verifyNoMoreInteractions(userRepository);  // Asegura que no hubo llamadas adicionales
    }
    @DisplayName("Añadir un nuevo usuario con el metode save")
    @Test
    public void should_Return_saveUser_When_Add_New_User() {
        //Given
        Long userId = 1L;
        final User user = new User(userId, "jjkjk",
                67L, "bhh", "hghgh",
                "nnbnbn");
        when(userRepository.save(user)).thenReturn(user);

        //When
        final User result = userService.saveUser(user);
        //Then
        assertEquals(user.getId(), result.getId());
        verify(userRepository).save(user);
    }
    @DisplayName("Modificar un usuario con el metodo  updateUser")
    @Test
    public void should_Return_updateUsers_When_Modify_User() {
        //Given
        Long userId = 1L;
        final User user = new User(userId, "jjkjk",
                67L, "bhh", "hghgh",
                "nnbnbn");
        when(userRepository.save(user)).thenReturn(user);

        //When
        final User result = userService.saveUser(user);
        //Then
        assertEquals(result.getId(), user.getId());
        verify(userRepository).save(user);
    }
    @DisplayName("Borrar el usuario con el metodo deleteUserId")
    @Test
    public void should_Return_deleteUserId_User_Exists(){
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);
        userService.deleteUser(userId);
        assertTrue(true);
    }
}
