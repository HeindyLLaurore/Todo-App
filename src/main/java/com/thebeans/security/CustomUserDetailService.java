package com.thebeans.security;

import com.thebeans.models.User;
import com.thebeans.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        {
            try {
                User user = userRepository.getUserByEmail(email);
                if (user == null) {
                    throw new UsernameNotFoundException("Could not find user");
                }
                return new CustomUserDetails(user);
            } catch (NumberFormatException ex) {
                throw new UsernameNotFoundException("Username is not valid");
            }
        }

    }
}


