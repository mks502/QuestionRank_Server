package com.depromeet.qr.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.depromeet.qr.dto.CommentDto;
import com.depromeet.qr.dto.CommentResponseDto;
import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.LikeEntity;
import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.exception.BadRequestException;
import com.depromeet.qr.exception.NotFoundException;
import com.depromeet.qr.repository.CommentRepository;
import com.depromeet.qr.repository.LikeEntityRepository;
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
	@Autowired
	LikeEntityRepository likeEntityRepository;
	@Autowired
	SeminarRoomService seminarRoomService;

	@Transactional
	public CommentResponseDto createComment(CommentDto commentDto) {
		SeminarRoom seminar = seminarRoomService.findSeminar(commentDto.getSeminarId());
		Member member = memberRepository.findById(commentDto.getMid()).orElseThrow(() -> new NotFoundException());

		Comment comment = Comment.builder().content(commentDto.getContent()).target(commentDto.getTarget()).likeCount(0)
				.member(member).seminarRoom(seminar).build();
		Comment newComment = commentRepository.save(comment);
		return CommentResponseDto.builder().comment(newComment).type("COMMENT").build();
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
		SeminarRoom seminar = seminarRoomService.findSeminar(seminarId);
		List<Comment> comments = commentRepository.findAllBySeminarRoom(seminar);
		if (comments == null)
			throw new NotFoundException();
		commentRepository.deleteInBatch(comments);
		return true;
	}

	@Transactional
	public List<Comment> getCommentsBySeminarRoom(Long seminarId) {
		SeminarRoom seminar = seminarRoomService.findSeminar(seminarId);
		List<Comment> comments = commentRepository.findAllBySeminarRoom(seminar);
		if (comments == null)
			throw new NotFoundException();
		return comments;
	}

	@Transactional
	public CommentResponseDto upLikeCount(Long commentId, Long memberId) {
		Comment comment = getComment(commentId);
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException());
		if (likeEntityRepository.findOneByCommentAndMember(comment, member) != null)
			throw new NotFoundException();
		LikeEntity like = LikeEntity.builder().comment(comment).member(member)
				.build();
		likeEntityRepository.save(like);
		comment.setLikeCount(comment.getLikeCount()+1);
		commentRepository.save(comment);
		return CommentResponseDto.builder().comment(comment).type("LIKE").build();
	}
	
	@Transactional
	public CommentResponseDto downLikeCount(Long commentId, Long memberId) {
		Comment comment = getComment(commentId);
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException());
		LikeEntity like = likeEntityRepository.findOneByCommentAndMember(comment, member);
		if (like == null)
			throw new NotFoundException();
		likeEntityRepository.delete(like);
		comment.setLikeCount(comment.getLikeCount()-1);
		commentRepository.save(comment);
		return CommentResponseDto.builder().comment(comment).type("UNLIKE").build();
	}
	
	@Transactional
	public boolean deleteCommentByAdmin(Long commentId,Long memberId) {
		Comment comment = getComment(commentId);
		Member member = memberRepository.findById(memberId).orElseThrow(()-> new NotFoundException());
		if(member.getRole() != "Admin")
			throw new BadRequestException();
		commentRepository.delete(comment);
		return true;
	}
	
	@Transactional
	public List<Comment> getCommentRankListBySeminar(Long seminarId) {
		SeminarRoom seminarRoom = seminarRoomService.findSeminar(seminarId);
		List<Comment> commentRankingList = commentRepository.findTop3BySeminarRoomOrderByLikeCountDesc(seminarRoom);
		return commentRankingList;
	}
}
