package com.depromeet.qr.dto;

import com.depromeet.qr.entity.Comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponseDto {
	private String type;
	private Comment comment;
}
