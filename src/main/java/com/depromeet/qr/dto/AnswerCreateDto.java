package com.depromeet.qr.dto;

import lombok.Data;

@Data
public class AnswerCreateDto {
	private String content;
	private Long memberId;
	private Long commentId;
}
