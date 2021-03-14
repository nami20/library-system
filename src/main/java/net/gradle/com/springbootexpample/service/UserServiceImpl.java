package net.gradle.com.springbootexpample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.gradle.com.springbootexpample.model.User;
import net.gradle.com.springbootexpample.model.UserStatus;
import net.gradle.com.springbootexpample.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserRole(user.getUserRole());
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}