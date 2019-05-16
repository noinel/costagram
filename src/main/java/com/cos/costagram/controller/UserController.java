package com.cos.costagram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	@GetMapping("/auth/login")
	public String userLogin() {
		return "auth/login";
	}
	
}
