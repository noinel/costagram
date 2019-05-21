package com.cos.costagram.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JsonIgnoreProperties({"password","name","email","website","bio","gender","phone","createDate","updateDate"})
	@ManyToOne
	@JoinColumn(name="fromUser")
	private User fromUser;
	@JsonIgnoreProperties({"password","name","email","website","bio","gender","phone","createDate","updateDate"})
	@ManyToOne
	@JoinColumn(name="toUser")
	private User toUser;
	
	@Transient
	private Boolean doFollowing; //true(혼팔), false(맞팔)
	
	@CreationTimestamp
	private LocalDate createDate;
	@CreationTimestamp
	private LocalDate updateDate;
}

