//package com.blogapp.model;
//
//import java.util.Arrays;
//
//import java.util.Collection;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//@Entity
//@Data
//@RequiredArgsConstructor
//public class User implements UserDetails {
//	private static final long serialVersionUID = 1L;
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	private Long id;
//	private String username;
//	private String email;
//	private String password;
//
//	public User(String fullname, String email, String encode) {
//		this.username = fullname;
//		this.email = email;
//		this.password = encode;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//
//	@Override
//	public String getPassword() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//
//
//}
