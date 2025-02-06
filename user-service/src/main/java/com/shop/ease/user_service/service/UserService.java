package com.shop.ease.user_service.service;

import com.shop.ease.user_service.model.User;
import com.shop.ease.user_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository ){
        this.userRepository=userRepository;
    }

//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public Optional<User> getUserById(long id) {
//       return userRepository.findById(id);
//
//    }
//
//    public Optional<User> getUserByName(String userName) {
//        return userRepository.findByUserName(userName);
//    }
//
//    public void deleteUser(long id) {
//        userRepository.deleteById(id);
//    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())  // Assign roles
                .build();
    }
    }
}
