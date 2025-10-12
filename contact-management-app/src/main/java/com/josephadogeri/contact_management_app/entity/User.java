package com.josephadogeri.contact_management_app.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.josephadogeri.contact_management_app.Auditable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity(name = "Users")
@Data
@Getter
@Setter
@Tag(name = "Users", description = "the User Api")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "email_unique" , columnNames = "email"),
                @UniqueConstraint(name = "username_unique" , columnNames = "username")
        }
)
public class User extends Auditable {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"

    )
    private Integer id;

    private String username;

    private String password;

    private String email;

    private boolean enabled;

    private int failedAttempts;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    public User() {
        super();
        this.failedAttempts = 0;
        this.enabled = true;
    }

    public User(String username, String email, String password) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.failedAttempts = 0;
        this.enabled = true;
    }
    public User(Integer id,String username, String email, String password){
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.failedAttempts = 0;
        this.enabled = true;

    }

    // Helper methods to manage the relationship
    public void addContact(Contact contact) {
        contacts.add(contact);
        contact.setUser(this);
    }

    public void removeEmployee(Contact contact) {
        contacts.remove(contact);
        contact.setUser(null);
    }

}

/*
*
* // Parent Entity
public class Parent {
    // ... other fields
    @JsonManagedReference
    @OneToMany(mappedBy = "parent")
    private List<Child> children;
    // ... getters and setters
}

// Child Entity
public class Child {
    // ... other fields
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;
    // ... getters and setters
}*/