package com.blogapp.security;

import com.blogapp.model.MyUserDetails;
import com.blogapp.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogapp.repository.UserRepository;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig{

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		return email -> {
			Optional<User> user = userRepository.findByEmail(email);
			if (user.isPresent()) return user.map(MyUserDetails::new).get();

			throw new UsernameNotFoundException("User '" + email + "' not found");
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()
				.authorizeRequests()
//					.antMatchers("/newpost", "/update", "/delete", "/comment").hasRole("AUTHOR")
//					.antMatchers("/update", "/delete").hasRole("ADMIN")
					.antMatchers("/", "/**").permitAll()
				.and()
					.formLogin()
						.loginPage("/login")
						.usernameParameter("email")
						.permitAll()
				.and()
				.build();
	}

}
