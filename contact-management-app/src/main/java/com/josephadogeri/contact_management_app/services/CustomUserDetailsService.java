package com.josephadogeri.contact_management_app.services;


import com.josephadogeri.contact_management_app.CustomUserDetails;
import com.josephadogeri.contact_management_app.entities.User;
import com.josephadogeri.contact_management_app.repositories.UserRepository;
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