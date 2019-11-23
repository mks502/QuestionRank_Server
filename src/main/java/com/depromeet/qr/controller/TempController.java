package com.depromeet.qr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.qr.dto.CommentAndLikeDto;
import com.depromeet.qr.dto.CommentDto;
import com.depromeet.qr.dto.CommentRequestDto;
import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.service.CommentService;

@RestController
public class TempController {
	@Autowired
	CommentService commentService;
	
	@PostMapping("/comment/{seminarid}")
	public Comment commentMessage(@DestinationVariable Long seminarid, CommentDto commentDto) {
		return commentService.createComment(commentDto);
	}
	@PostMapping("/comment/{seminarid}/like")
	public CommentAndLikeDto commentLike(@DestinationVariable Long seminarid, CommentRequestDto commentRequestDto) {
		return commentService.upLikeCount(commentRequestDto.getCommentId(), commentRequestDto.getMid());
	}
	@PostMapping("/comment/{seminarid}/unlike")
	public CommentAndLikeDto commentUnLike(@DestinationVariable Long seminarid, CommentRequestDto commentRequestDto) {
		return commentService.downLikeCount(commentRequestDto.getCommentId(), commentRequestDto.getMid());
	}
	@PostMapping("/comment/{seminarid}/delete")
	public boolean commentDelete(@DestinationVariable Long seminarid, CommentRequestDto commentRequestDto) {
		return commentService.deleteCommentByAdmin(commentRequestDto.getCommentId(), commentRequestDto.getMid());
	}
}
