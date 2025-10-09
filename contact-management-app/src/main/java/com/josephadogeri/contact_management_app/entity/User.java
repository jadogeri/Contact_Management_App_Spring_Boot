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

    private boolean enabled;

    private int failedAttempts;

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

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public int getFailedAttempts() {
        return this.failedAttempts;
    }
    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

}
