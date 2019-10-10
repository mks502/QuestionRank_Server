package com.depromeet.qr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class LikeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long lid;
	
	@OneToOne
	@JoinColumn(name = "commentId")
	private Comment comment;
	@OneToOne
	@JoinColumn(name = "mid")
	private Member member;

}
