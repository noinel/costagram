package com.cos.costagram.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="fromUser")
	private User fromUser;
	@ManyToOne
	@JoinColumn(name="toUser")
	private User toUser;
	
	@CreationTimestamp
	private LocalDate createDate;
	@CreationTimestamp
	private LocalDate updateDate;
}

