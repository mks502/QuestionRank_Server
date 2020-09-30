package com.depromeet.qr.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.depromeet.qr.entity.Comment;

@Service
@RequiredArgsConstructor
public class WebSocketService {

	private final CommentService commentService;
	
	private final SimpMessagingTemplate template;

//	@Autowired
//	public WebSocketService(SimpMessagingTemplate template) {
//		this.template = template;
//	}
	
	public void sendCommentRankingListBySpeaker(Long seminarId,Long speakerId) {
		List<Comment> commentRankingList = commentService.getCommentRankListBySpeaker(speakerId);
		this.template.convertAndSend("/subscribe/seminar/"+seminarId, commentRankingList);
	}
}
