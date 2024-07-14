package com.example.todo.service;

import com.example.todo.model.User;
import com.example.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // These modifications introduce basic role management to your application. The role is stored as a simple String in
    // the User entity. In a real-world application, you might want to use a more sophisticated approach, like a
    // many-to-many relationship with a separate Role
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                //.roles("USER")
                // Díky tomuto se všude odkazuji na role jako: ROLE_ADMIN, atd.
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())))
                .build();
    }
}
