package com.blogapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blogapp.model.Comments;
import com.blogapp.model.Posts;
import com.blogapp.model.Tags;
import com.blogapp.service.HomeService;

@RequestMapping("/post")
public class PostController {

	private HomeService homeService;


}
