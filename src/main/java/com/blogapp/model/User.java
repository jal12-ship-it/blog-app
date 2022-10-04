package com.blogapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String email;
	private String password;
	private boolean active;
	private String roles;

	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "user", orphanRemoval = true)
	private Set<Post> post = new HashSet<>();

	public User(String username, String email, String password, Boolean active, String roles) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.active = active;
		this.roles = roles;
	}

}
