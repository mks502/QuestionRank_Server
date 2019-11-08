package com.depromeet.qr.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.depromeet.qr.dto.CommentDto;
import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.exception.NotFoundException;
import com.depromeet.qr.repository.CommentRepository;
import com.depromeet.qr.repository.MemberRepository;
import com.depromeet.qr.repository.SeminarRoomRepository;

@Service
public class CommentService {
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	SeminarRoomRepository seminarRoomRepository;
	@Autowired
	MemberRepository memberRepository;

	@Transactional
	public Comment createComment(CommentDto commentDto) {
		SeminarRoom seminar = seminarRoomRepository.findById(commentDto.getSeminarId())
				.orElseThrow(() -> new NotFoundException());
		Member member = memberRepository.findById(commentDto.getMid())
				.orElseThrow(() -> new NotFoundException());
		
		Comment comment = Comment.builder().content(commentDto.getContent()).target(commentDto.getTarget()).likeCount(0)
				.member(member).seminarRoom(seminar).build();
		return commentRepository.save(comment);
	}
	
	@Transactional
	public Comment getComment(Long commentId) {
		return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException());
	}

	@Transactional
	public void deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}

	@Transactional
	public boolean deleteCommentsBySeminar(Long seminarId) {
		List<Comment> comments = commentRepository.findAllBySeminarRoom(seminarId);
		if (comments == null)
			throw new NotFoundException();
		commentRepository.deleteInBatch(comments);
		return true;
	}
}
