package com.greenfoxacademy.backendapp.security;

import com.greenfoxacademy.backendapp.models.roles.Role;
import com.greenfoxacademy.backendapp.models.UserEntity;
import com.greenfoxacademy.backendapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername method is run");
        UserEntity user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        User result = new User(user.getName(),user.getPassword(),mapRolesToAuthorities(List.of(user.getRole())));
        System.out.println(result.getUsername());
        System.out.println(result.getPassword());
        System.out.println(result.getAuthorities());
        return result;
//        return new User(user.getName(),user.getPassword(),mapRolesToAuthorities(List.of(user.getRole())));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList());
    }
}
