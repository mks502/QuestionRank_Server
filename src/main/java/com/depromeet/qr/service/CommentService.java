package com.depromeet.qr.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.depromeet.qr.constant.AccessionRole;
import com.depromeet.qr.dto.CommentDto;
import com.depromeet.qr.entity.*;
import com.depromeet.qr.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.depromeet.qr.dto.CommentCreateDto;
import com.depromeet.qr.dto.CommentResponseDto;
import com.depromeet.qr.dto.SpeakerAndCommentList;
import com.depromeet.qr.exception.BadRequestException;
import com.depromeet.qr.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	private final MemberRepository memberRepository;

	private final LikeEntityRepository likeEntityRepository;

	private final SpeakerRepository speakerRepository;

	private final SeminarRoomService seminarRoomService;

	private final AccessionRepository accessionRepository;

	private final AnswerRepository answerRepository;

	@Transactional
		public CommentResponseDto createComment(CommentCreateDto commentDto) {
		Member member = memberRepository.findById(commentDto.getMemberId()).orElseThrow(() -> new NotFoundException());
		Speaker speaker = speakerRepository.findById(commentDto.getSpeakerId()).orElseThrow(() -> new NotFoundException("존재하지 않는 스피커입니다"));
		Comment comment = Comment.builder().content(commentDto.getContent()).speaker(speaker).likeCount(0)
				.member(member).build();
		Comment newComment = commentRepository.save(comment);
		return CommentResponseDto.builder().comment(newComment.toCommentDto()).type("COMMENT").build();
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
	public List<SpeakerAndCommentList> getCommentsBySeminarRoom(Long seminarId,Long memberId) {
		List<SpeakerAndCommentList> result = new ArrayList<SpeakerAndCommentList>();
		SeminarRoom seminar = seminarRoomService.findSeminar(seminarId);
		List<Speaker> speakers = speakerRepository.findAllBySeminarRoom(seminar);
		for (Speaker speaker : speakers) {
			List<Comment> comments = commentRepository.findAllBySpeaker(speaker);
			List<Comment> commentRankings = commentRepository.findTop3BySpeakerOrderByLikeCountDesc(speaker);

			List<CommentDto> commentList = toCommentDtoList(comments,memberId);
			List<CommentDto> commentRankingList = toCommentDtoList(commentRankings,memberId);

			if (comments != null) {
				SpeakerAndCommentList temp = SpeakerAndCommentList.builder().speaker(speaker.toResponseDto()).commentList(commentList)
						.commentRankingList(commentRankingList).build();
				result.add(temp);
			}
		}
		return result;
	}

	public List<CommentDto> toCommentDtoList(List<Comment> commentList , Long memberId){
		List<CommentDto> commentDtoList = new ArrayList<>();
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException());
		for(Comment comment : commentList){
			CommentDto commentDto = comment.toCommentDto();
			if(likeEntityRepository.findOneByCommentAndMember(comment, member) != null){
				commentDto.setLiked(true);
			}
			commentDtoList.add(commentDto);
		}
		return commentDtoList;
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
		return CommentResponseDto.builder().comment(comment.toCommentDto()).type("LIKE").build();
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
		return CommentResponseDto.builder().comment(comment.toCommentDto()).type("UNLIKE").build();
	}

	@Transactional
	public CommentResponseDto deleteComment(Long commentId, Long memberId,Long seminarId) {
		Comment comment = getComment(commentId);
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException());
		SeminarRoom seminarRoom = seminarRoomService.findSeminar(seminarId);
		Accession accession = accessionRepository.findOneByMemberAndSeminarRoom(member,seminarRoom).orElseThrow(()-> new NotFoundException("해당 방에 참여정보가 없습니다."));
		if( !comment.getMember().equals(member) && !accession.getAccessionRole().equals(AccessionRole.MANAGER) )
			throw new BadRequestException("BadRequest - 권한이 없습니다");

		List<LikeEntity> likeEntityList = likeEntityRepository.findAllByComment(comment);
		likeEntityRepository.deleteInBatch(likeEntityList);

		List<Answer> answerList = answerRepository.findAllByComment(comment);
		answerRepository.deleteInBatch(answerList);
		commentRepository.delete(comment);

		return CommentResponseDto.builder().comment(comment.toCommentDto()).type("DELETE").build();
	}

	@Transactional
	public List<Comment> getCommentRankListBySpeaker(Long speakerId) {
		Speaker speaker = speakerRepository.findById(speakerId).orElseThrow(() -> new NotFoundException("존재하지 않는 speakerId입니다"));
		List<Comment> commentRankingList = commentRepository.findTop3BySpeakerOrderByLikeCountDesc(speaker);
		return commentRankingList;
	}
}
