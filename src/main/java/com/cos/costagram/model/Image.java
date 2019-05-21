package com.cos.costagram.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String location;
	private String caption;
	private String mimeType;
	private String fileName;
	private String filePath;
	
	@JsonIgnoreProperties({"password","name","email","website","bio","gender","phone","createDate","updateDate"})
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="userId")
	private User user;
	
	//cascade = CascadeType.PERSIST 영속성 전이 (FK를 들고 있지않아도 save가능)
	//@OneToMany(mappedBy = "image",cascade = CascadeType.PERSIST)
	@OneToMany(mappedBy = "image")
	@JsonManagedReference
	@Builder.Default private List<Tag> tags = new ArrayList<>();
	
	@OneToMany(mappedBy = "image")
	@JsonManagedReference
	@Builder.Default private List<Likes> likes = new ArrayList<>();
	
	@CreationTimestamp
	private LocalDate createDate;
	@CreationTimestamp
	private LocalDate updateDate;
}
