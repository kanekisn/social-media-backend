package com.socialmedia.service;

import com.socialmedia.model.User;
import com.socialmedia.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.findAll());
    }
}
