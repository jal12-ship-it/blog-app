package com.blogapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blogapp.repository.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserRepository userRepository;
    @Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping
	public String registerForm() {
		return "register";
	}

	@PostMapping
	public String processRegistration(RegistrationForm form) {
		System.out.println("..........Here.........");
		userRepository.save(form.toUser(passwordEncoder));
		return "redirect:/login";
	}
}