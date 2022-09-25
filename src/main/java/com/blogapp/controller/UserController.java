package com.blogapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.blogapp.model.Users;

@Controller
public class UserController {

	@GetMapping("/login")
	public String showLoginPage(Model model, Users user) {
		model.addAttribute(user);
		return "login";
	}
	
	@GetMapping("/register")
	public String showRegisterPage(Model model, Users user) {
		model.addAttribute(user);
		return "register";
	}
}
