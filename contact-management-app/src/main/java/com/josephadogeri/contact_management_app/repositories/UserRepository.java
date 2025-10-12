package com.josephadogeri.contact_management_app.repositories;

import com.josephadogeri.contact_management_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    User findByEmail(String email);

}

