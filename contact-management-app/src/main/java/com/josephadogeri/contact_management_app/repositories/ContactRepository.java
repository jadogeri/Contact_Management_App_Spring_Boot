package com.josephadogeri.contact_management_app.repositories;

import com.josephadogeri.contact_management_app.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {

    List<Contact> getAllContactsByUserId(@Param("userID")Integer userID);

}

