package com.depromeet.qr.dto;

import java.util.List;

import com.depromeet.qr.entity.Comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponseDto {
	private String type;
	private Comment comment;
	private List<Comment> commentRankingList;
}
