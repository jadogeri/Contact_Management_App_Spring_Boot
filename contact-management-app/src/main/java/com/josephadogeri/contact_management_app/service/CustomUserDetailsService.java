package com.josephadogeri.contact_management_app.service;


import com.josephadogeri.contact_management_app.CustomUserDetails;
import com.josephadogeri.contact_management_app.entity.User;
import com.josephadogeri.contact_management_app.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("user " + username + " not found");
            throw new UsernameNotFoundException("username " + username + " not found");
        }else{
            return new CustomUserDetails(user);
        }
    }
}