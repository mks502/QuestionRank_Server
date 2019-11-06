package com.depromeet.qr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long commentId;

	@Column(nullable = false)
	private String content;
	private String target;

	@Builder.Default
	private Integer likeCount = 0;

	@ManyToOne
	@JoinColumn(name = "seminarId")
	private SeminarRoom seminarRoom;
	
	@OneToOne
	@JoinColumn(name = "mid")
	private Member member;

}