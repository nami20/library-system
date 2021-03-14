package net.gradle.com.springbootexpample.service;

import net.gradle.com.springbootexpample.model.User;

public interface UserService {
    User save(User user);

    User findByUsername(String username);
}