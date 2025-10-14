package com.josephadogeri.contact_management_app.controllers;

import com.josephadogeri.contact_management_app.Auditable;
import com.josephadogeri.contact_management_app.documentations.ContactDocumentation;
import com.josephadogeri.contact_management_app.documentations.UserDocumentation;
import com.josephadogeri.contact_management_app.dtos.request.ContactAddRequestDTO;
import com.josephadogeri.contact_management_app.dtos.request.ContactUpdateRequestDTO;
import com.josephadogeri.contact_management_app.dtos.response.ContactDeleteAllResponseDTO;
import com.josephadogeri.contact_management_app.dtos.response.ContactDeleteSingleResponseDTO;
import com.josephadogeri.contact_management_app.entities.Contact;
import com.josephadogeri.contact_management_app.services.ContactService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/contacts") // Base URL for all endpoints in this controller
public class ContactController extends Auditable implements ContactDocumentation{

    @Autowired
    private final ContactService contactService;

    public ContactController(ContactService contactService) {

        this.contactService = contactService;
    }

    // Create a new Contact
    @PostMapping // Handles POST requests to /contacts/
    public Contact createContact(@RequestBody ContactAddRequestDTO contactAddRequestDTO) {

        return contactService.createContact(contactAddRequestDTO);

    }

    // Get all contacts
    @GetMapping // Handles GET requests to /contacts
    public List<Contact> getAllContacts() {

        return contactService.getAllContacts();

    }

    // Get a Contact by ID
    @GetMapping("/{id}") // Handles GET requests to /contacts/{id}
    public Contact getSingleContact(@PathVariable Integer id) {
        if(!(id instanceof  Integer) ){
            throw new IllegalArgumentException(" value must be an integer");
        }

        return contactService.getSingleContact(id);
    }

    // Update an existing contact
    @PutMapping("/{id}") // Handles PUT requests to /contacts/{id}
    public Contact updateContact(@Parameter(description = "The unique identifier of the contact",
            required = true,
            schema = @Schema(type = "integer", format = "int64", example = "123"))
            @PathVariable("id") Integer id,@RequestBody ContactUpdateRequestDTO contactUpdateRequestDTO) {
        System.out.println("calling update contact...................................................");
        if(!(id instanceof Integer) ){
            throw new IllegalArgumentException(" value must be an integer");
        }
        return contactService.updateContact(id, contactUpdateRequestDTO);
    }

    // Delete a single contact
    @DeleteMapping("/{id}") // Handles DELETE requests to /contacts/{id}
    public ContactDeleteSingleResponseDTO deleteSingleContact(@PathVariable Integer id) {
        if(!(id instanceof Integer) ){
            throw new IllegalArgumentException(" value must be an integer");
        }

        return contactService.deleteSingleContact(id);
    }

    // Delete all contacts
    @DeleteMapping// Handles DELETE requests to /contacts
    public ContactDeleteAllResponseDTO deleteAllContacts() {

        return contactService.deleteAllContacts();
    }

}