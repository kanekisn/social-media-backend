package com.socialmedia.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.model.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserCacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public UserCacheService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void cacheUser(User user) {
        try {
            String userJson = objectMapper.writeValueAsString(user);
            redisTemplate.opsForValue().set("USER_" + user.getUsername(), userJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public User getCachedUser(String username) {
        String userJson = redisTemplate.opsForValue().get("USER_" + username);
        if (userJson != null) {
            try {
                return objectMapper.readValue(userJson, User.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void removeCachedUser(String username) {
        redisTemplate.delete("USER_" + username);
    }
}