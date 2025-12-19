package com.siddarthavadavalasa.SwiftCollab.controller;
import com.siddarthavadavalasa.SwiftCollab.service.UserService;
import com.siddarthavadavalasa.SwiftCollab.dto.UserLoginDTO;
import com.siddarthavadavalasa.SwiftCollab.dto.UserRegisterDTO;
import com.siddarthavadavalasa.SwiftCollab.dto.UserResponseDTO;
import com.siddarthavadavalasa.SwiftCollab.model.User;
import com.siddarthavadavalasa.SwiftCollab.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // test endpoint
    @GetMapping("/test")
    public String test() {
        return "User API is working ✅";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO dto) {
        String token = userService.loginUser(dto);
        return ResponseEntity.ok(token);
    }


    // register endpoint
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO dto) {
        System.out.println("Received DTO: " + dto); // log incoming object
        return ResponseEntity.ok(userService.registerUser(dto));
    }

    // get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
