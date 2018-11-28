package com.example.brokers.user;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    public User getUserById(@PathVariable Long id) throws NotFoundException {
        return userService.retrieveUser(id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable Long id) throws NotFoundException {
        userService.deleteUser(id);
    }

    @PutMapping
    public void updateUser(@RequestBody User user) throws NotFoundException {
        userService.updateUser(user);
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }
}

