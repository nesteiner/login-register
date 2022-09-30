package com.example.loginregister.service;

import com.example.loginregister.model.ImageItem;
import com.example.loginregister.model.User;
import com.example.loginregister.model.RegisterRequest;
import com.example.loginregister.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageItemService imageItemService;

    public Optional<User> findOne(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findOne(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByName(username);
        return optionalUser.map(user -> {
                    return new org.springframework.security.core.userdetails.User(user.getName(), user.getPasswordHash(), new ArrayList<>());
                })
                .orElseThrow(() -> new UsernameNotFoundException("no such user"));
    }

    public User insertOne(RegisterRequest request) {
        ImageItem avatar = imageItemService.findDefault();
        User user = new User(null, request.getUsername(), request.getEmail(), request.getPasswordHash(), avatar);
        return userRepository.save(user);
    }

    public User updateOne(User user) {
        return userRepository.save(user);
    }
}
