//package com.blogapp.security;
//	
//import org.jetbrains.annotations.NotNull;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.blogapp.model.User;
//
//import lombok.Data;
//
//@Data
//public class RegistrationForm {
//
//	private String fullname;
//	private String email;
//	private String password;
//	
//	
//
//	public String getFullname() {
//		return fullname;
//	}
//
//
//
//	public void setFullname(String fullname) {
//		this.fullname = fullname;
//	}
//
//
//
//	public String getEmail() {
//		return email;
//	}
//
//
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//
//
//	public String getPassword() {
//		return password;
//	}
//
//
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//
//
//	public User toUser(@NotNull PasswordEncoder passwordEncoder) {
//		return new User(
//				fullname, 
//				email,
//				passwordEncoder.encode(password));
//	}
//}
