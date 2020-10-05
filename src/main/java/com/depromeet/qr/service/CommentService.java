package com.depromeet.qr.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.depromeet.qr.dto.CommentCreateDto;
import com.depromeet.qr.dto.CommentResponseDto;
import com.depromeet.qr.dto.SpeakerAndCommentList;
import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.LikeEntity;
import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.entity.Speaker;
import com.depromeet.qr.exception.BadRequestException;
import com.depromeet.qr.exception.NotFoundException;
import com.depromeet.qr.repository.CommentRepository;
import com.depromeet.qr.repository.LikeEntityRepository;
import com.depromeet.qr.repository.MemberRepository;
import com.depromeet.qr.repository.SeminarRoomRepository;
import com.depromeet.qr.repository.SpeakerRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	private final SeminarRoomRepository seminarRoomRepository;

	private final MemberRepository memberRepository;

	private final LikeEntityRepository likeEntityRepository;

	private final SpeakerRepository speakerRepository;

	private final SeminarRoomService seminarRoomService;

	@Transactional
	public CommentResponseDto createComment(CommentCreateDto commentDto, Long seminarId) {
		SeminarRoom seminar = seminarRoomService.findSeminar(seminarId);
		Member member = memberRepository.findById(commentDto.getMemberId()).orElseThrow(() -> new NotFoundException());
		Speaker speaker = speakerRepository.findById(commentDto.getSpeakerId()).orElseThrow(() -> new NotFoundException("존재하지 않는 스피커입니다"));
		Comment comment = Comment.builder().content(commentDto.getContent()).speaker(speaker).likeCount(0)
				.member(member).build();
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
	public List<SpeakerAndCommentList> getCommentsBySeminarRoom(Long seminarId) {
		List<SpeakerAndCommentList> result = new ArrayList<SpeakerAndCommentList>();
		SeminarRoom seminar = seminarRoomService.findSeminar(seminarId);
		List<Speaker> speakers = speakerRepository.findAllBySeminarRoom(seminar);
		for (Speaker speaker : speakers) {
			List<Comment> comments = commentRepository.findAllBySpeaker(speaker);
			List<Comment> commentRankingList = commentRepository.findTop3BySpeakerOrderByLikeCountDesc(speaker);
			if (comments != null) {
				SpeakerAndCommentList temp = SpeakerAndCommentList.builder().speaker(speaker).commentList(comments)
						.commentRankingList(commentRankingList).build();
				result.add(temp);
			}
		}
		return result;
	}

	@Transactional
	public CommentResponseDto upLikeCount(Long commentId, Long memberId) {
		Comment comment = getComment(commentId);
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException());
		if (likeEntityRepository.findOneByCommentAndMember(comment, member) != null)
			throw new NotFoundException();
		LikeEntity like = LikeEntity.builder().comment(comment).member(member).build();
		likeEntityRepository.save(like);
		comment.setLikeCount(comment.getLikeCount() + 1);
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
		comment.setLikeCount(comment.getLikeCount() - 1);
		commentRepository.save(comment);
		return CommentResponseDto.builder().comment(comment).type("UNLIKE").build();
	}

	@Transactional
	public CommentResponseDto deleteCommentByAdmin(Long commentId, Long memberId) {
		Comment comment = getComment(commentId);
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException());
		if (member.getRole() != "ADMIN")
			throw new BadRequestException();
		commentRepository.delete(comment);
		return CommentResponseDto.builder().comment(comment).type("DELETE").build();
	}

	@Transactional
	public List<Comment> getCommentRankListBySpeaker(Long speakerId) {
		Speaker speaker = speakerRepository.findById(speakerId).orElseThrow(() -> new NotFoundException("존재하지 않는 speakerId입니다"));
		List<Comment> commentRankingList = commentRepository.findTop3BySpeakerOrderByLikeCountDesc(speaker);
		return commentRankingList;
	}
}
