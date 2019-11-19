package com.depromeet.qr.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CommentDto {

	private String content;
	private String target;

	private Long commentId;
	private Long seminarId;
	private Long mid;

}
