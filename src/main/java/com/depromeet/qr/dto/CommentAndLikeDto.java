package com.depromeet.qr.dto;

import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.LikeEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentAndLikeDto {
	private Comment comment;
	private LikeEntity like;
}
