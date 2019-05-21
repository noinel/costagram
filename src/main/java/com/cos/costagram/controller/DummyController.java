package com.cos.costagram.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.costagram.Service.CustomUserDetails;
import com.cos.costagram.model.Follow;
import com.cos.costagram.model.Image;
import com.cos.costagram.model.User;
import com.cos.costagram.repository.FollowRepository;
import com.cos.costagram.repository.ImageRepository;
import com.cos.costagram.repository.TagRepository;
import com.cos.costagram.repository.UserRepository;

//더미데이터 생성용 컨트롤러
@Controller
@RequestMapping("/dummy")
public class DummyController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private FollowRepository followRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/create")
	public @ResponseBody User create(User user) {
		String rawPassword = user.getPassword();
		String encPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		userRepository.save(user);
		return user;
	}
	
	@GetMapping("/images")
	public @ResponseBody List<Image> image(@AuthenticationPrincipal CustomUserDetails userDetail, Model model) {
		
		//1. user(one)
		User user  = userDetail.getUser();
		System.out.println(user);
				
		//2.User:follow(List)
		List<Follow> followList = followRepository.findByFromUserId(user.getId());
		//3.User:Follow:Image(List)4 . Follow:image:Like(count)(One)
		List<Image> imageList = new ArrayList<Image>();
		
		for(Follow f : followList) {
			List<Image> list= imageRepository.findByUserId(f.getToUser().getId());
			for(Image i : list)
			{
				imageList.add(i);
			}
			
		}
		//4.Model에 담기
		model.addAttribute("user", user);
		model.addAttribute("imageList", imageList);
		
		return imageList;
	}
}
