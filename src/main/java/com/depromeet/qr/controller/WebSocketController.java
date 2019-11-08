package com.depromeet.qr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.depromeet.qr.dto.CommentDto;
import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.service.CommentService;

@Controller
public class WebSocketController {
	@Autowired
	CommentService commentService;
	
	@MessageMapping("/comment")
	@SendTo("/seminar/{id}")
	public Comment commentMessage(@DestinationVariable Long id,CommentDto commentDto) {
		return commentService.createComment(commentDto);
	}
}
