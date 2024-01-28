package com.greenfoxacademy.backendapp.controllers;

import com.greenfoxacademy.backendapp.dtos.AuthResponseDTO;
import com.greenfoxacademy.backendapp.dtos.LoginDTO;
import com.greenfoxacademy.backendapp.dtos.RegisterDTO;
import com.greenfoxacademy.backendapp.models.UserEntity;
import com.greenfoxacademy.backendapp.security.JwtGenerator;
import com.greenfoxacademy.backendapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtGenerator jwtGenerator;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
        if (userService.existsUserByName(registerDTO.getUsername())){
            return ResponseEntity.badRequest().body("Username is taken");
        }

        UserEntity user = userService.createUser(registerDTO);
        if(user == null) {
            return ResponseEntity.badRequest().body("Missing parameters");
        } else {
            if(user.getName() == null) {
                return ResponseEntity.badRequest().body("Missing name");
            } else if (user.getPassword() == null) {
                return ResponseEntity.badRequest().body("Missing pwd");
            } else if (user.getEmail() == null) {
                return ResponseEntity.badRequest().body("Missing email");
            }
        }
        return ResponseEntity.ok("User successfully registered");
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        System.out.println("login controller, username: " + loginDTO.getUsername());
        System.out.println("login controller, password: " + loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        System.out.println("this executes only if authentication passes. in this particular scenario it means that role USER has to exist in database and user must have this role assigned");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}


/*
de - d
* eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZSIsImlhdCI6MTcwMDIzODQxMCwiZXhwIjoxNzAwMjM5MDEwfQ.2PiC_t8wZmoLZZPxBV_XRzQOn3qBMitxHg3yX8KW1x8
*

slavo - s
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbGF2byIsImlhdCI6MTcwMDIzODQ1OCwiZXhwIjoxNzAwMjM5MDU4fQ.nYwTHnYk0gLcwhtndT16danCrsadsvNpeLdZSeMxUJ0
*

c - c
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjIiwiaWF0IjoxNzAwMjQwMDUyLCJleHAiOjE3MDAyNDA2NTJ9.Iv3E9gT-szOA_NpIUvKXTI3bYT5nQr9AX1c660co4PM
*/