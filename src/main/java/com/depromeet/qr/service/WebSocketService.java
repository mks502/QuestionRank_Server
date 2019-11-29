package com.depromeet.qr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.depromeet.qr.entity.Comment;

@Service
public class WebSocketService {
	
	@Autowired
	CommentService commentService;
	
	private final SimpMessagingTemplate template;

	@Autowired
	public WebSocketService(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	public void sendCommentRankingListBySeminar(Long seminarId) {
		List<Comment> commentRankingList = commentService.getCommentRankListBySeminar(seminarId);
		this.template.convertAndSend("/subscribe/seminar/"+seminarId, commentRankingList);
	}
}
