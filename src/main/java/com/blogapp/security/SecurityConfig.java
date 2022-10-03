package com.blogapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogapp.model.User;
import com.blogapp.repository.UserRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig{

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public UserDetailsService userDetailsServiceAdmin() {
//		List<UserDetails> usersList = new ArrayList<>();
//		usersList.add(new User(
//				"admin", "admin@gmail.com", passwordEncoder().encode("pass"),
//				Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//		return new InMemoryUserDetailsManager(usersList);
//	}

//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user = User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("password")
//				.roles("USER")
//				.build();
//		return new InMemoryUserDetailsManager(user);
//	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		System.out.println("entered in userdetails");
		return email -> {
			User user = userRepository.findByEmail(email);
			if (user != null) return user;
			throw new UsernameNotFoundException("User '" + email + "' not found");
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeRequests()
//					.antMatchers("/newpost", "/update", "/delete", "/comment").hasRole("AUTHOR")
//					.antMatchers("/update", "/delete").hasRole("ADMIN")
					.antMatchers("/", "/**").permitAll()
				.and()
					.formLogin()
						.loginPage("/login")
						.usernameParameter("email")
				.and()
				.build();
	}

}
