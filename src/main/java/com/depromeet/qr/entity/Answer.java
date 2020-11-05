package com.depromeet.qr.entity;

import com.depromeet.qr.base.BaseEntity;
import com.depromeet.qr.dto.AnswerDto;
import com.depromeet.qr.dto.CommentDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Answer extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long answerId;

	@Column(nullable = false)
	private String content;

	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "commentId")
	private Comment comment;

	public AnswerDto toAnswerDto(){
		return AnswerDto.builder()
				.answerId(this.answerId).content(this.content).member(this.member).build();
	}
}