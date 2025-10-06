package com.josephadogeri.contact_management_app.entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Tag(name = "Contacts", description = "the Contact Api")
public class Contact {

    @Id
    @GeneratedValue
    private Long id;
}
