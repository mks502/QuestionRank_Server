package com.depromeet.qr.service;

import com.depromeet.qr.constant.AccessionRole;
import com.depromeet.qr.dto.*;
import com.depromeet.qr.entity.*;
import com.depromeet.qr.exception.BadRequestException;
import com.depromeet.qr.exception.NotFoundException;
import com.depromeet.qr.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

	private final CommentRepository commentRepository;
	private final MemberService memberService;
	private final CommentService commentService;
	private final AnswerRepository answerRepository;


	public AnswerDto createAnswer(Long commentId,Long memberId,String content){
		Member member = memberService.getMember(memberId);
		Comment comment = commentService.getComment(commentId);
		Answer answer = Answer.builder()
				.comment(comment).content(content).member(member).build();
		Answer saveAnswer = answerRepository.save(answer);
		return saveAnswer.toAnswerDto();
	}

	public List<AnswerDto> findAllByAnswerByComment(Long commentId){
		Comment comment = commentService.getComment(commentId);
		List<Answer> answerList = answerRepository.findAllByComment(comment);
		List<AnswerDto> answerDtoList = new ArrayList<>();

		for(Answer answer : answerList){
			answerDtoList.add(answer.toAnswerDto());
		}
		return answerDtoList;
	}
}
