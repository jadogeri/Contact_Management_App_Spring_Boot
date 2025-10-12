package com.josephadogeri.contact_management_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.josephadogeri.contact_management_app.Auditable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity(name = "contacts")
@Data
@Setter
@Getter
@Tag(name = "Contacts", description = "the Contact Api")
@Table(
//        uniqueConstraints = {
//                @UniqueConstraint(name = "contact_email_unique" , columnNames = "email"),
//                @UniqueConstraint(name = "username_unique" , columnNames = "username")
//        }
)
public class Contact extends Auditable{

    @Column(name = "id")
    @Id
    @SequenceGenerator(
            name = "contact_sequence",
            sequenceName = "contact_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "contact_sequence"

    )
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String name;

    private String email;

    private String phone;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false//, referencedColumnName = "id"
    )
    private User user;

    public Contact(){ super(); }

    public Contact( String name, String email, String phone, User user) {
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.user = user;
    }

}
