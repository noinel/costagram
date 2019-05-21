package com.cos.costagram.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cos.costagram.model.Follow;
import com.cos.costagram.model.Image;
import com.cos.costagram.model.Tag;
import com.cos.costagram.model.User;
import com.cos.costagram.repository.FollowRepository;
import com.cos.costagram.repository.ImageRepository;
import com.cos.costagram.repository.TagRepository;
import com.cos.costagram.Service.CustomUserDetails;
import com.cos.costagram.Util.UtilCos;
@Controller
public class ImageController {
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private FollowRepository followRepository;

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/images")
	public  String image(@AuthenticationPrincipal CustomUserDetails userDetail, Model model) {
		
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
		return "images/image";
	}

	@PostMapping("/image/upload")
	public @ResponseBody Image imageUpload(@AuthenticationPrincipal CustomUserDetails userDetail, @RequestParam("file") MultipartFile file, String caption, String location, String tags)
			throws IOException {
		Path filePath = Paths.get(UtilCos.getResourcePath() + file.getOriginalFilename());
		
		Files.write(filePath, file.getBytes());

		User user = userDetail.getUser() ;
		
		List<String> tagList = UtilCos.tagParser(tags);
	
		Image image = Image.builder().caption(caption).location(location).user(user).mimeType(file.getContentType())
				.fileName(file.getOriginalFilename()).filePath("/image/" + file.getOriginalFilename()).build();
		System.out.println(image);
		imageRepository.save(image);

		for (String t : tagList) {
			Tag tag = new Tag();
			tag.setName(t);
			tag.setImage(image);
			tag.setUser(user);
			tagRepository.save(tag);
			image.getTags().add(tag); // DB에 영향을 미치지 않음.
		}

		return image;
	}
}
