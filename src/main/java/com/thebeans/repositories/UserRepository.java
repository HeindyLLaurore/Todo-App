package com.thebeans.repositories;

import com.thebeans.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
}

