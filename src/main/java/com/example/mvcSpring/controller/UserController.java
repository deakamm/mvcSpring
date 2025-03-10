package com.example.mvcSpring.controller;

import com.example.mvcSpring.model.User;
import com.example.mvcSpring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping("/info")
    public String index() {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("A INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
        return "WOOW! ,check out the logs to see the output";
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/user")
    public ResponseEntity<User> setUsers(@RequestBody User user) {
       User savedUser = userService.saveUser(user);
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userD) {
        return userService.getUserById(id).map(user -> {
            user.setUsername(userD.getUsername());
            user.setFirst_name(userD.getFirst_name());
            user.setLast_name(userD.getLast_name());
            userService.saveUser(user);
            return ResponseEntity.ok(user);
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/user/{id}")
    public void deleteUserId(@PathVariable Long id) {
       userService.deleteUser(id);
    }

}

