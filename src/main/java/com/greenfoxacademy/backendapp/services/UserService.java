package com.greenfoxacademy.backendapp.services;

import com.greenfoxacademy.backendapp.dtos.RegisterDTO;
import com.greenfoxacademy.backendapp.models.roles.Role;
import com.greenfoxacademy.backendapp.models.UserEntity;
import com.greenfoxacademy.backendapp.repositories.RoleRepository;
import com.greenfoxacademy.backendapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.greenfoxacademy.backendapp.models.roles.*;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean existsUserByName(String username) {
        return userRepository.existsByName(username);
    }

//    public UserEntity createUser(RegisterDTO registerDTO) {
//        if (registerDTO == null) {
//            return null;
//        }
//
//        UserEntity user = new UserEntity();
//        user.setName(registerDTO.getUsername());
//        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
//        user.setEmail(registerDTO.getEmail());
//
//        if (registerDTO.getPassword() == null || registerDTO.getUsername() == null || registerDTO.getEmail() == null) {
//            return user;
//        }
//
//        // old approach that worked only in demonstration video
////        Role roles = new Role();
////        if (roleRepository.findByName("USER").isPresent()){
////            roles = roleRepository.findByName("USER").get();
////            // this has some logic hole because if USER is not defined in database, any new user will not have this role and so he will not be authenticated
////        }
////        user.setRoles(Collections.singletonList(roles));
//
//        Role role = new User();
//        role.setName("USER");
//        if (!roleRepository.existsByName(role.getName())) {
//            roleRepository.save(role);
//        }
//        user.setRole(role);
//        userRepository.save(user);
//        return user;
//    }

    public UserEntity createUser(RegisterDTO registerDTO) {
        if (registerDTO == null ||
                registerDTO.getPassword() == null ||
                registerDTO.getUsername() == null ||
                registerDTO.getEmail() == null) {
            return null;
        }

        Role role = roleRepository.findByName("USER");
        if (role == null) {
            role = new User();
            role.setName("USER");
            roleRepository.save(role);
        }

        UserEntity user = new UserEntity();
        user.setName(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setRole(role);

        userRepository.save(user);
        return user;
    }
}
//eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJVU0VSIl0sInN1YiI6ImV4YyIsImlhdCI6MTcwMDUxNDQxOSwiZXhwIjoxNzAwNTE1MDE5fQ.3ZW6n6Ezspq4RGMhQu-LZWcw7cc8K7VkwLO1lPBwYdg