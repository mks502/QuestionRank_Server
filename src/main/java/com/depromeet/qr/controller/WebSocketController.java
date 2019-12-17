package com.depromeet.qr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.depromeet.qr.dto.CommentCreateDto;
import com.depromeet.qr.dto.CommentRequestDto;
import com.depromeet.qr.dto.CommentResponseDto;
import com.depromeet.qr.service.CommentService;
import com.depromeet.qr.service.WebSocketService;

@Controller
public class WebSocketController {
	@Autowired
	CommentService commentService;
	@Autowired
	WebSocketService webSocketService;

	@MessageMapping("/comment/{seminarid}")
	@SendTo("/subscribe/seminar/{seminarid}")
	public CommentResponseDto commentMessage(@DestinationVariable Long seminarid, CommentCreateDto commentDto) {
		CommentResponseDto response = commentService.createComment(commentDto,seminarid);
		response.setCommentRankingList(commentService.getCommentRankListBySpeaker(commentDto.getSpeakerId()));
		return response;
	}

	@MessageMapping("/comment/{seminarid}/like")
	@SendTo("/subscribe/seminar/{seminarid}")
	public CommentResponseDto commentLike(@DestinationVariable Long seminarid, CommentRequestDto commentRequestDto) {
		CommentResponseDto response = commentService.upLikeCount(commentRequestDto.getCommentId(), commentRequestDto.getMid());
		response.setCommentRankingList(commentService.getCommentRankListBySpeaker(commentRequestDto.getSpeakerId()));
		return response;
	}

	@MessageMapping("/comment/{seminarid}/unlike")
	@SendTo("/subscribe/seminar/{seminarid}")
	public CommentResponseDto commentUnLike(@DestinationVariable Long seminarid, CommentRequestDto commentRequestDto) {
		CommentResponseDto response = commentService.downLikeCount(commentRequestDto.getCommentId(), commentRequestDto.getMid());
		response.setCommentRankingList(commentService.getCommentRankListBySpeaker(commentRequestDto.getSpeakerId()));
		return response;
	}

	@MessageMapping("/comment/{seminarid}/delete")
	@SendTo("/subscribe/seminar/{seminarid}")
	public CommentResponseDto commentDelete(@DestinationVariable Long seminarid, CommentRequestDto commentRequestDto) {
		CommentResponseDto response = commentService.deleteCommentByAdmin(commentRequestDto.getCommentId(), commentRequestDto.getMid());
		response.setCommentRankingList(commentService.getCommentRankListBySpeaker(commentRequestDto.getSpeakerId()));
		return response;
	}
}
