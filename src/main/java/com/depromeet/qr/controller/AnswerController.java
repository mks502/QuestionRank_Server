package com.depromeet.qr.controller;

import com.depromeet.qr.constant.AccessionRole;
import com.depromeet.qr.dto.*;
import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.entity.Speaker;
import com.depromeet.qr.exception.BadRequestException;
import com.depromeet.qr.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnswerController {
	private final AnswerService answerService;
	@ApiOperation(value="comment에 대한 답변리스트")
	@GetMapping("/api/answers/comment/{commentId}")
	public List<AnswerDto> getAnswerListByComment (@PathVariable Long commentId) {
		return answerService.findAllByAnswerByComment(commentId);
	}

	@ApiOperation(value="답변 삭제")
	@DeleteMapping("/api/answers/{answerId}")
	public AnswerResponseDto deleteAnswerByAnswerId(@PathVariable Long answerId){
		return answerService.deleteAnswerById(answerId);
	}
}