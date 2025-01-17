package com.example.mvcSpring.controller;

import com.example.mvcSpring.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserController {
    private ArrayList<User> users = new ArrayList<>();

    public UserController(){
        users.add(new User(1, "Umo", "Balde"));
        users.add(new User(2, "Gerard", "Carlo"));
        users.add(new User(3, "Raul", "Garcia"));
        users.add(new User(4, "Paula", "Rodriguez"));
        users.add(new User(5, "Jerrimy", "Compagny"));
        users.add(new User(6, "Ansu", "Faty"));
        users.add(new User(7, "Drake", "Jhonas"));
    }

    @GetMapping("/user")
    public User getUser(@RequestParam(name = "id") int id) {
        User usuario = null;

        for (User us : users) {
            if (id == us.getId()) {
                usuario = us;
            }
        }
        return usuario;
    }

    @GetMapping("/users")

    public ArrayList<User> getUsers() {
        return users;
    }

    @PostMapping("/user")
    public String setUsers(@RequestBody User user) {
        for (User existingUser : users) {
            if (existingUser.getId() == user.getId()) {
                return "Id de usuario ya existe";
            }
        }
        users.add(user);
        return "Usuario insertado corecto";
    }

    @PutMapping("/user/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User user) {
        String msgString = "No se encuentro el usuario";
        for (User existingUser : users) {
            if (existingUser.getId() == id) {
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                msgString = "Ok, usuario actualizado";
            }

        }
        return msgString;
    }

    @DeleteMapping("/user/{id}")
    public String deleteUserId(@PathVariable("id") int id) {
        String borrar = "No se encuentro el usuario";
        for (int i = 0; i < users.size(); i++) {
            if (id == users.get(i).getId()) {
                users.remove(i);
                borrar = "Usuario borrado";
            }
        }
        return borrar;
    }
}

