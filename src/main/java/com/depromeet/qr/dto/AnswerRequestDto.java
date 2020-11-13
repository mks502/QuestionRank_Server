package com.depromeet.qr.dto;

import lombok.Data;

@Data
public class AnswerRequestDto {
	private Long commentId;
	private Long answerId;
	private Long memberId;
}
