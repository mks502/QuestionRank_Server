package com.depromeet.qr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeminarRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long seminarId;

	@Column(nullable = false)
	private String seminarTitle;

	private String fullURL;
	private String shortURL;
	private String seminarPassword;

}
