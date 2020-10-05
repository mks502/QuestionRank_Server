package com.depromeet.qr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.depromeet.qr.base.BaseEntity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Comment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long commentId;

	@Column(nullable = false)
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "speakerId")
	private Speaker speaker;

	@Builder.Default
	private Integer likeCount = 0;
	
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

}