package com.shop.ease.user_service.controller;

import com.shop.ease.user_service.dto.LoginRequest;
import com.shop.ease.user_service.dto.LoginResponse;
import com.shop.ease.user_service.model.User;
import com.shop.ease.user_service.security.JwtUtil;
import com.shop.ease.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil){
        this.userService=userService;
        this.jwtUtil=jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        UserDetails userDetails=userService.loadUserByUsername(request.getUsername());
        if (!new BCryptPasswordEncoder().matches(request.getPassword(), userDetails.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }

//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers(){
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
//
//    @PostMapping
//    public User createUser(@Valid @RequestBody User user){
//        return userService.createUser(user);
//    }
//
//    @GetMapping("/{id}")
//    public Optional<User> getUserById(@PathVariable Long id){
//        return userService.getUserById(id);
//    }
//
//    @GetMapping("/username/{userName}")
//    public Optional<User> getUserByName(@PathVariable String userName){
//        return userService.getUserByName(userName);
//
//    }
//
//    @PutMapping("/{id}")
//    public User updateUser(@PathVariable Long id, @Valid @RequestBody User updatedUser){
//        User getUser=userService.getUserById(id).get();
//        getUser.setPassword(updatedUser.getPassword());
//        getUser.setUserName(updatedUser.getUserName());
//        getUser.setEmail(updatedUser.getEmail());
//
//        return getUser;
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Long id){
//        userService.deleteUser(id);
//    }
}
