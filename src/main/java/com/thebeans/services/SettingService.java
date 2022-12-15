package com.thebeans.services;

import com.thebeans.models.User;


public interface SettingService {
    //saving a specific record using the method save() of CrudRepository
    void save(User user);

    User getByEmail(String userEmail);
}
