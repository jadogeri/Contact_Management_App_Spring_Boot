package com.josephadogeri.contact_management_app.controller;

import com.josephadogeri.contact_management_app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/contacts") // Base URL for all endpoints in this controller
public class ContactController {

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

    //@Autowired // Injects the UserService dependency
    // private UserService userService;

    // Create a new user
    @PostMapping // Handles POST requests to /api/users
//    public ResponseEntity<User> createUser(@RequestBody User user) {
    public List<Product> createUser() {

//        User savedUser = userService.saveUser(user);
//        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        return products;
    }

    // Get all users
    @GetMapping // Handles GET requests to /api/users
//    public ResponseEntity<List<User>> getAllUsers() {
    public List<Product> getAllUsers() {

//        List<User> users = userService.getAllUsers();
//        return new ResponseEntity<>(users, HttpStatus.OK);
        return products;
    }

    // Get a user by ID
    @GetMapping("/{id}") // Handles GET requests to /api/users/{id}
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
    public List<Product> getUserById() {
//        Optional<User> user = userService.getUserById(id);
//        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return products;
    }

    // Update an existing user
    @PutMapping("/{id}") // Handles PUT requests to /api/users/{id}
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

}