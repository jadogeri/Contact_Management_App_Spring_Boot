package com.josephadogeri.contact_management_app.controller;

import com.josephadogeri.contact_management_app.Auditable;
import com.josephadogeri.contact_management_app.dto.request.ContactAddRequestDTO;
import com.josephadogeri.contact_management_app.dto.response.ContactAddResponseDTO;
import com.josephadogeri.contact_management_app.entity.Contact;
import com.josephadogeri.contact_management_app.entity.User;
import com.josephadogeri.contact_management_app.repository.UserRepository;
import com.josephadogeri.contact_management_app.service.ContactService;
import com.josephadogeri.contact_management_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contacts") // Base URL for all endpoints in this controller
public class ContactController extends Auditable {

    @Autowired
    private final ContactService contactService;

    private record Product(
            Integer productId,
            String productName,
            double price
    ){ }

    List<Product> products = new ArrayList<>(
            List.of(
                    new Product(1,"iPhone",250.99),
                    new Product(2,"samsung",700.99)
            )
    );

    public ContactController(ContactService contactService) {

        this.contactService = contactService;
    }

    // Create a new Contact
    @PostMapping // Handles POST requests to /contacts/
    public Contact createContact(@RequestBody ContactAddRequestDTO contactAddRequestDTO) {

        System.out.println("get usa...................................................");
        return contactService.createContact(contactAddRequestDTO);

    }

    // Get all users
    @GetMapping // Handles GET requests to /api/users
//    public ResponseEntity<List<User>> getAllUsers() {
    public List<Contact> getAllUsers() {

        System.out.println("get usa...................................................");
        return contactService.getAllContacts();

    }

    // Get a user by ID
    @GetMapping("/{id}") // Handles GET requests to /contacts/{id}
    public Contact getUserById(@PathVariable Integer id) {
        if(!(id instanceof  Integer) ){
            throw new IllegalArgumentException(" value must be an integer");
        }

        return contactService.getSingleContact(id);
    }

    // Update an existing user
    @PutMapping("/{id}") // Handles PUT requests to /contacts/{id}
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
    public List<Product> updateUser() {
//        User updatedUser = userService.updateUser(id, userDetails);
//        if (updatedUser != null) {
//            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return products;
    }

    // Delete a user
    @DeleteMapping("/{id}") // Handles DELETE requests to /api/users/{id}
//    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
    public List<Product> deleteUser() {

//        userService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return products;
    }

    @GetMapping("/token")
    public String getToken(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            String currentUserName = authentication.getName();
            return currentUserName;
            // return "Access Token: " + accessToken;
        } else {
            return "Not a JWT authenticated user.";
        }
    }

}