package com.depromeet.qr.controller;

import com.depromeet.qr.dto.*;
import com.depromeet.qr.entity.Speaker;
import com.depromeet.qr.service.AnswerService;
import com.depromeet.qr.service.SpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.depromeet.qr.service.CommentService;
import com.depromeet.qr.service.WebSocketService;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

	private final CommentService commentService;

	private final WebSocketService webSocketService;

	private final SpeakerService speakerService;

	private final AnswerService answerService;

	@MessageMapping("/comment/{speakerId}")
	@SendTo("/topic/speaker.{speakerId}")
	public CommentResponseDto commentMessage(@DestinationVariable Long speakerId, CommentCreateDto commentDto) {
		CommentResponseDto response = commentService.createComment(commentDto);
		response.setCommentRankingList(commentService.getCommentRankListBySpeaker(commentDto.getSpeakerId()));
		return response;
	}

	@MessageMapping("/comment/{speakerId}/like")
	@SendTo("/topic/speaker.{speakerId}")
	public CommentResponseDto commentLike(@DestinationVariable Long speakerId, CommentRequestDto commentRequestDto) {
		CommentResponseDto response = commentService.upLikeCount(commentRequestDto.getCommentId(), commentRequestDto.getMemberId());
		response.setCommentRankingList(commentService.getCommentRankListBySpeaker(commentRequestDto.getSpeakerId()));
		return response;
	}

	@MessageMapping("/comment/{speakerId}/unlike")
	@SendTo("/topic/speaker.{speakerId}")
	public CommentResponseDto commentUnLike(@DestinationVariable Long speakerId, CommentRequestDto commentRequestDto) {
		CommentResponseDto response = commentService.downLikeCount(commentRequestDto.getCommentId(), commentRequestDto.getMemberId());
		response.setCommentRankingList(commentService.getCommentRankListBySpeaker(commentRequestDto.getSpeakerId()));
		return response;
	}


	@MessageMapping("/comment/{speakerId}/delete")
	@SendTo("/topic/speaker.{speakerId}")
	public CommentResponseDto commentDelete(@DestinationVariable Long speakerId, CommentRequestDto commentRequestDto) {
		Speaker speaker = speakerService.getSpeakerBySpeakerId(speakerId);
		CommentResponseDto response = commentService.deleteComment(commentRequestDto.getCommentId(), commentRequestDto.getMemberId(), speaker.getSeminarRoom().getSeminarId());
		response.setCommentRankingList(commentService.getCommentRankListBySpeaker(commentRequestDto.getSpeakerId()));
		return response;
	}

	@MessageMapping("/answer/{commentId}")
	@SendTo("/topic/comment.{commentId}")
	public AnswerResponseDto answerMessage(@DestinationVariable Long commentId, AnswerCreateDto answerCreateDto) {
		AnswerDto answerDto = answerService.createAnswer(commentId, answerCreateDto.getMemberId(), answerCreateDto.getContent());
		return AnswerResponseDto.builder()
				.answer(answerDto).type("ANSWER").build();
	}

	@MessageMapping("/answer/{commentId}/delete")
	@SendTo("/topic/comment.{commentId}")
	public AnswerResponseDto answerDelete(@DestinationVariable Long commentId, AnswerRequestDto answerRequestDto) {
		return answerService.deleteAnswerById(answerRequestDto.getAnswerId());
	}
}
