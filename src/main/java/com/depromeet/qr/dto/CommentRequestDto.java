package com.depromeet.qr.dto;

import lombok.Data;

@Data
public class CommentRequestDto {
	private Long speakerId;
	private Long commentId;
	private Long mid;
}
