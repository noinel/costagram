package com.cos.costagram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//더미데이터 생성용 컨트롤러
@Controller
@RequestMapping("/dummy")
public class DummyController {
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
}
