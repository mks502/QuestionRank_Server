package com.depromeet.qr.dto;

import com.depromeet.qr.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnswerResponseDto {
	private String type;
	private AnswerDto answer;
}
