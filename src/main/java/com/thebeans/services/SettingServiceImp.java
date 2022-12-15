package com.thebeans.services;

import com.thebeans.models.User;
import com.thebeans.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImp implements SettingService {
    @Autowired
    UserRepository userRepository;

    //getting a specific record by using the method getUserByEmail
    public User getByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    //saving a specific record by using the method save() of CrudRepository
    public void save(User user) {
        userRepository.save(user);
    }
}
