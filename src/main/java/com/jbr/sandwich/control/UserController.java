package com.jbr.sandwich.control;

import com.jbr.sandwich.data.User;
import com.jbr.sandwich.data.dtoUser;
import com.jbr.sandwich.dataaccess.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository repository) {
        this.userRepository = repository;
    }

    @GetMapping("/users")
    public List<User> getIngredients() {
        return userRepository.findAll();
    }

    @GetMapping("/user")
    public @ResponseBody User getUser(@RequestParam String id) throws Exception {
        Optional<User> currentUser = userRepository.findById(id);

        if(!currentUser.isPresent()) {
            throw new Exception("Failed to find the user");
        }

        return currentUser.get();
    }

    @PostMapping("/user") User createUser(@RequestBody dtoUser user) throws Exception {
        Optional<User> currentUser = userRepository.findById(user.getId());

        if(currentUser.isPresent()) {
            throw new Exception("User already exist");
        }

        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());

        userRepository.save(newUser);

        return newUser;
    }

    @PutMapping("/user")
    public @ResponseBody User updateUser(@RequestBody dtoUser user) throws Exception {
        Optional<User> currentUser = userRepository.findById(user.getId());

        if(!currentUser.isPresent()) {
            throw new Exception("Failed to find the user");
        }

        currentUser.get().setName(user.getName());
        currentUser.get().setEmail(user.getEmail());

        userRepository.save(currentUser.get());

        return currentUser.get();
    }

    @DeleteMapping("/user")
    public @ResponseBody User deleteUser(@RequestBody dtoUser user) throws Exception {
        Optional<User> currentUser = userRepository.findById(user.getId());

        if(!currentUser.isPresent()) {
            throw new Exception("Failed to find the user");
        }
        userRepository.delete(currentUser.get());

        return currentUser.get();
    }
}
