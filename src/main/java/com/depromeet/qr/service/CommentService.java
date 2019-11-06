package com.depromeet.qr.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.exception.NotFoundException;
import com.depromeet.qr.repository.CommentRepository;

@Service
public class CommentService {
	@Autowired
	CommentRepository commentRepository;
	
	@Transactional
	public Comment createComment(Comment comment) {
		return commentRepository.save(comment);
	}
	@Transactional
	public Comment getComment(Long commentId) {
		return commentRepository.findById(commentId).orElseThrow(()->new NotFoundException());
	}
	@Transactional
	public void deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}
	@Transactional
	public boolean deleteCommentsBySeminar(Long seminarId) {
		List<Comment> comments = commentRepository.findAllBySeminarRoom(seminarId);
		if(comments == null)
			throw new NotFoundException();
		commentRepository.deleteInBatch(comments);
		return true;
	}
}
