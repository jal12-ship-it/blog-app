package com.blogapp.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogapp.model.User;

import java.util.Arrays;

public class RegistrationForm {

	private String username;
	private String email;
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(
				username,
				email,
				passwordEncoder.encode(password),
				Arrays.asList(new SimpleGrantedAuthority("ROLE_AUTHOR")));
	}
}
