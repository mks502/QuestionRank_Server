package com.depromeet.qr.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CommentCreateDto {

	private String content;
	private Long mid;
	private Long speakerId;

}
