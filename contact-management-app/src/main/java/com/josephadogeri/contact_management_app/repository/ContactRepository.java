package com.josephadogeri.contact_management_app.repository;

import com.josephadogeri.contact_management_app.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {

    List<Contact> getAllContactsByUserId(@Param("userID")Integer userID);



//    @Query(value = "SELECT * FROM users WHERE id = :userId", nativeQuery = true)
//    Optional<User> findUserByIdNative(@Param("userId") Long id);
//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO Contact (name, email, phone, user_id) VALUES (:name, :age, :phone, :user_id)", nativeQuery = true)
//    void addContact(@Param("name") String name, @Param("age") int age);
//}
//    Contact addContact(ContactAddRequestDTO contactAddRequestDTO);


}

