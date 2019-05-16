package com.cos.costagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.costagram.model.User;
import com.cos.costagram.repository.UserRepository;

//더미데이터 생성용 컨트롤러
@Controller
@RequestMapping("/dummy")
public class DummyController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository; 
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@PostMapping("/create")
	public @ResponseBody User create(User user) {
		String rawPassword = user.getPassword();
		String encPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		System.out.println(encPassword);
		userRepository.save(user);
		
		
		return user;
	}
}
