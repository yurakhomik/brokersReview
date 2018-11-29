package com.example.brokers.user;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User retrieveUser(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("User with id:" + id + " doesn't found");
        }
        return user.get();
    }

    public void deleteUser(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("User with id:" + id + " doesn't found");
        }
        userRepository.deleteById(id);
    }

    public void updateUser(User user) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (!userOptional.isPresent()) {
            throw new NotFoundException("User id:" + user.getId() + " doesn't found");
        }
        userRepository.save(user);
    }

    public void createUser(User user) {
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());

        if (user.getEmail() == null || user.getPassword().length() == 0) {
            throw new InvalidParameterException("Incorrect user");
        }

        if (userByEmail.isPresent()) {
            throw new InvalidParameterException("User with email:" + user.getEmail() + " already exist!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
