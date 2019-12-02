//package com.depromeet.qr.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.depromeet.qr.dto.CommentDto;
//import com.depromeet.qr.dto.CommentRequestDto;
//import com.depromeet.qr.dto.CommentResponseDto;
//import com.depromeet.qr.entity.Comment;
//import com.depromeet.qr.service.CommentService;
//
//@RestController
//public class TempController {
//	@Autowired
//	CommentService commentService;
//	
//	@PostMapping("/comment/{seminarid}")
//	public CommentResponseDto commentMessage(@PathVariable Long seminarid, CommentDto commentDto) {
//		CommentResponseDto response = commentService.createComment(commentDto);
//		response.setCommentRankingList(commentService.getCommentRankListBySeminar(seminarid));
//		return response;
//	}
//	@PostMapping("/comment/{seminarid}/like")
//	public CommentResponseDto commentLike(@PathVariable Long seminarid, CommentRequestDto commentRequestDto) {
//		CommentResponseDto response = commentService.upLikeCount(commentRequestDto.getCommentId(), commentRequestDto.getMid());
//		response.setCommentRankingList(commentService.getCommentRankListBySeminar(seminarid));
//		return response;
//	}
//	@PostMapping("/comment/{seminarid}/unlike")
//	public CommentResponseDto commentUnLike(@PathVariable Long seminarid, CommentRequestDto commentRequestDto) {
//		CommentResponseDto response = commentService.downLikeCount(commentRequestDto.getCommentId(), commentRequestDto.getMid());
//		response.setCommentRankingList(commentService.getCommentRankListBySeminar(seminarid));
//		return response;
//	}
//	@PostMapping("/comment/{seminarid}/delete")
//	public boolean commentDelete(@PathVariable Long seminarid, CommentRequestDto commentRequestDto) {
//		return commentService.deleteCommentByAdmin(commentRequestDto.getCommentId(), commentRequestDto.getMid());
//	}
//}
