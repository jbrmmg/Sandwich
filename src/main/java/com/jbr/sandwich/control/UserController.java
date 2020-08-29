package com.jbr.sandwich.control;

import com.jbr.sandwich.data.User;
import com.jbr.sandwich.dataaccess.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
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
}
