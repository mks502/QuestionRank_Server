package com.depromeet.qr.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.depromeet.qr.constant.AccessionRole;
import com.depromeet.qr.dto.*;
import com.depromeet.qr.exception.BadRequestException;
import com.depromeet.qr.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.entity.Speaker;

import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
public class SeminarRoomController {

	private final SeminarRoomService seminarRoomService;
	private final SpeakerService speakerService;
	private final CommentService commentService;
	private final AccessionService accessionService;
	private final MemberService memberService;

	@ApiOperation(value="방 만들기")
	@PostMapping("/api/seminar")
	public SeminarCreateResponse createSeminarRoom(@RequestBody SeminarCreateRequest request) throws MalformedURLException, IOException {
		Member member = memberService.getMember(request.getMemberId());
		SeminarRoom seminarRoom = seminarRoomService.createSeminar(request.getSeminarRoomDto());
		SeminarCreateResponse result = seminarRoom.toResponse();
		List<SpeakerResponse> speakerList = new ArrayList<>();
		for (SpeakerDto speakerDto : request.getSpeakerList()) {
			speakerDto.setSeminarId(seminarRoom.getSeminarId());
			Speaker s = speakerService.createSpeaker(speakerDto);
			speakerList.add(s.toResponseDto());
		}
		result.setSpeakerList(speakerList);
		accessionService.accessSeminarRoom(member,seminarRoom, AccessionRole.MANAGER);
		return result;
	}

	@ApiOperation(value="seminarId를 통한 세미나방에 대한 정보만 조회")
	@GetMapping("/api/seminar/{seminarId}")
	public SeminarRoom getSeminarRoom (@PathVariable Long seminarId) {
		return seminarRoomService.findSeminar(seminarId);
	}

	@ApiOperation(value="seminarId와 memberId를 통해 세미나 방에 대환 관련정보들 조회")
	@PostMapping("/api/seminar/{seminarId}")
	public SeminarAndCommentList getSeminarRoomInfo(@PathVariable Long seminarId, @RequestBody SeminarEnterDto seminarEnterDto) {
		SeminarRoom seminarRoom = seminarRoomService.findSeminar(seminarId);
		Member member = memberService.getMember(seminarEnterDto.getMemberId());
		try {
			accessionService.findAccessionByMemberAndSeminar(member,seminarRoom);
		}
		catch (Exception e){
			String password = seminarEnterDto.getPassword();
			if(seminarRoom.getSeminarPassword().equals(password)){
				accessionService.accessSeminarRoom(member,seminarRoom,AccessionRole.MEMBER);
			}
			else
				throw new BadRequestException("WRONG SEMINAR ROOM PASSWORD");
		}
		List<SpeakerAndCommentList> comments = commentService.getCommentsBySeminarRoom(seminarId, seminarEnterDto.getMemberId());
		return SeminarAndCommentList.builder().seminarRoom(seminarRoom).commentListBySpeaker(comments).build();
	}

	@ApiOperation(value="seminar 비밀번호를 통한 방 입장")
	@PutMapping("/api/seminar")
	public SeminarAndCommentList accessSeminarByPassword(@RequestBody SeminarEnterDto seminarEnterDto){

		SeminarRoom seminarRoom = seminarRoomService.findBySeminarByPassword(seminarEnterDto.getPassword());
		Member member = memberService.getMember(seminarEnterDto.getMemberId());
		try {
			accessionService.findAccessionByMemberAndSeminar(member,seminarRoom);
		}
		catch (Exception e){
			String password = seminarEnterDto.getPassword();
			if(seminarRoom.getSeminarPassword().equals(password)){
				accessionService.accessSeminarRoom(member,seminarRoom,AccessionRole.MEMBER);
			}
			else
				throw new BadRequestException("WRONG SEMINAR ROOM PASSWORD");
		}
		List<SpeakerAndCommentList> comments = commentService.getCommentsBySeminarRoom(seminarRoom.getSeminarId(),seminarEnterDto.getMemberId());
		return SeminarAndCommentList.builder().seminarRoom(seminarRoom).commentListBySpeaker(comments).build();
	}

	@GetMapping("/api/seminar/{seminarId}/accession/role/{accessionRole}")
	public MemberResponseDto getAnswerListByComment (@PathVariable Long seminarId,@PathVariable String accessionRole) {
		return seminarRoomService.findByMemberListBySeminarAndRole(seminarId,accessionRole);
	}

	@GetMapping("/api/seminar/member/{memberId}")
	public List<SeminarRoom> getSeminarByMember(@PathVariable Long memberId){
		return seminarRoomService.getSeminarRoomListByMember(memberId);
	}
}