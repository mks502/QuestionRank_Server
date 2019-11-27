package com.depromeet.qr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.depromeet.qr.dto.CommentDto;
import com.depromeet.qr.dto.CommentRequestDto;
import com.depromeet.qr.dto.CommentResponseDto;
import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.service.CommentService;

@Controller
public class WebSocketController {
	@Autowired
	CommentService commentService;

	@MessageMapping("/comment/{seminarid}")
	@SendTo("/seminar/{seminarid}")
	public CommentResponseDto commentMessage(@DestinationVariable Long seminarid, CommentDto commentDto) {
		return commentService.createComment(commentDto);
	}

	@MessageMapping("/comment/{seminarid}/like")
	@SendTo("/seminar/{seminarid}")
	public CommentResponseDto commentLike(@DestinationVariable Long seminarid, CommentRequestDto commentRequestDto) {
		return commentService.upLikeCount(commentRequestDto.getCommentId(), commentRequestDto.getMid());
	}
	@MessageMapping("/comment/{seminarid}/unlike")
	@SendTo("/seminar/{seminarid}")
	public CommentResponseDto commentUnLike(@DestinationVariable Long seminarid, CommentRequestDto commentRequestDto) {
		return commentService.downLikeCount(commentRequestDto.getCommentId(), commentRequestDto.getMid());
	}
	@MessageMapping("/comment/{seminarid}/delete")
	@SendTo("/seminar/{seminarid}")
	public boolean commentDelete(@DestinationVariable Long seminarid, CommentRequestDto commentRequestDto) {
		return commentService.deleteCommentByAdmin(commentRequestDto.getCommentId(), commentRequestDto.getMid());
	}
}
