package com.josephadogeri.contact_management_app.entity;


import com.josephadogeri.contact_management_app.Auditable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity(name = "Users")
@Data
@Tag(name = "Users", description = "the User Api")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "email_unique" , columnNames = "email"),
                @UniqueConstraint(name = "username_unique" , columnNames = "username")
        }
)
public class User extends Auditable {
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
//
//    @CreatedDate
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    @Column(name = "last_modified_date")
//    private LocalDateTime lastModifiedDate;

    public User() {
        super();
    }

    public User(String username, String email, String passwordeatedDate, LocalDateTime lastModifiedDate) {
        super();
        this.username = username;
        this.password = passwordeatedDate;
        this.email = email;
    }
    public User(Integer id,String username, String email, String password){
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
