package com.blogapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean active;
    private String roles;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user")
    private Set<Post> post = new HashSet<>();

    public Users(String username, String email, String password, Boolean active, String roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

}
