package com.depromeet.qr.entity;

import com.depromeet.qr.base.BaseEntity;
import com.depromeet.qr.dto.CommentDto;
import lombok.*;

import javax.persistence.*;

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

	public CommentDto toCommentDto(){
		return CommentDto.builder()
				.commentId(this.commentId).content(this.content).likeCount(this.likeCount).member(this.member).speakerId(speaker.getSpeakerId()).build();
	}
}