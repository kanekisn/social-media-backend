package com.socialmedia.service;

import com.socialmedia.dto.UserDto;
import com.socialmedia.model.User;
import com.socialmedia.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.findAllUsersWithStack());
    }

    public User getUserById(Long id) {
        return this.userRepository.findUserById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public void updateUser(UserDto userDto, User user) {
        Optional.ofNullable(userDto.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(userDto.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(userDto.getAvatarUrl()).ifPresent(user::setAvatarUrl);
        Optional.ofNullable(userDto.getCity()).ifPresent(user::setCity);
        Optional.ofNullable(userDto.getStack()).ifPresent(user::setStack);
        Optional.ofNullable(userDto.getDescription()).ifPresent(user::setDescription);

        userRepository.save(user);
    }
}