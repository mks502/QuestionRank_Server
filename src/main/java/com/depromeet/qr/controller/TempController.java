package com.depromeet.qr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.qr.dto.CommentCreateDto;
import com.depromeet.qr.dto.CommentRequestDto;
import com.depromeet.qr.dto.CommentResponseDto;
import com.depromeet.qr.service.CommentService;

@RestController
@RequiredArgsConstructor
public class TempController {

	private final CommentService commentService;
	
	@PostMapping("/comment/{seminarid}")
	public CommentResponseDto commentMessage(@PathVariable Long seminarid, CommentCreateDto commentDto) {
		CommentResponseDto response = commentService.createComment(commentDto,seminarid);
		response.setCommentRankingList(commentService.getCommentRankListBySpeaker(commentDto.getSpeakerId()));
		return response;
	}
	@PostMapping("/comment/{seminarid}/like")
	public CommentResponseDto commentLike(@PathVariable Long seminarid, CommentRequestDto commentRequestDto) {
		CommentResponseDto response = commentService.upLikeCount(commentRequestDto.getCommentId(), commentRequestDto.getMemberId());
		response.setCommentRankingList(commentService.getCommentRankListBySpeaker(commentRequestDto.getSpeakerId()));
		return response;
	}
	@PostMapping("/comment/{seminarid}/unlike")
	public CommentResponseDto commentUnLike(@PathVariable Long seminarid, CommentRequestDto commentRequestDto) {
		CommentResponseDto response = commentService.downLikeCount(commentRequestDto.getCommentId(), commentRequestDto.getMemberId());
		response.setCommentRankingList(commentService.getCommentRankListBySpeaker(commentRequestDto.getSpeakerId()));
		return response;
	}
	@PostMapping("/comment/{seminarid}/delete")
	public CommentResponseDto commentDelete(@PathVariable Long seminarid, CommentRequestDto commentRequestDto) {
		CommentResponseDto response = commentService.deleteCommentByAdmin(commentRequestDto.getCommentId(), commentRequestDto.getMemberId());
		response.setCommentRankingList(commentService.getCommentRankListBySpeaker(commentRequestDto.getSpeakerId()));
		return response;
	}
}
