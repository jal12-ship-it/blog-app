//package com.blogapp.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//
//import com.blogapp.model.User;
//import com.blogapp.repository.UserRepository;
//import com.blogapp.service.UserDetailsService;
//
//@Configuration
//public class SecurityConfig {
//	
//	@Autowired
//	private UserRepository userRepository;
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//	public UserDetailsService userDetailsService(UserRepository userRepo) {
//		return username -> {
//			User user = userRepository.findByUsername(username);
//			if (user != null) return user;
//			throw new UsernameNotFoundException("User '" + username + "' not found");
//		};
//	}
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		return http
//				.authorizeRequests()
////					.antMatchers("/newpost", "/update", "/delete", "/comment").hasRole("auhor")
//					.antMatchers("/update", "/delete").hasRole("admin")
//					.antMatchers("/", "/**").permitAll()
//				.and()
//					.formLogin()
//						.loginPage("/login")
//						.usernameParameter("email")
//						.passwordParameter("password")
//				
//				.and()
//				.build();
//	}
//
//}
