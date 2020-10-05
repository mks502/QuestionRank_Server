package com.depromeet.qr.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.depromeet.qr.constant.AccessionRole;
import com.depromeet.qr.dto.*;
import com.depromeet.qr.exception.BadRequestException;
import com.depromeet.qr.service.*;
import com.depromeet.qr.util.UtilEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/api/seminar/enter/{seminarid}/{memberId}")
	public MemberAndCommentList enterSeminarByMember(@PathVariable Long seminarid, @PathVariable(name="memberId",required=false) Long memberId) {
		List<SpeakerAndCommentList> comments = commentService.getCommentsBySeminarRoom(seminarid);
		Member member = seminarRoomService.enterSeminarByMember(seminarid, memberId);
		return MemberAndCommentList.builder().member(member).commentListBySpeaker(comments).build();
	}

	@ApiOperation(value="방 번호와 비밀번호를 통한 admin 입장")
	@GetMapping("/api/seminar/enter/admin")
	public MemberAndCommentList enterSeminarByAdmin(@ModelAttribute SeminarAdminDto seminarAdmin) {
		List<SpeakerAndCommentList> comments = commentService.getCommentsBySeminarRoom(seminarAdmin.getSeminarId());
		Member member = seminarRoomService.enterSeminarByAdmin(seminarAdmin.getSeminarId(), seminarAdmin.getPassword());
		return MemberAndCommentList.builder().member(member).commentListBySpeaker(comments).build();
	}
	
	@ApiOperation(value="seminarId를 통한 방조회")
	@GetMapping("/api/seminar/room/{seminarId}")
	public SeminarRoom getSeminarRoom (@PathVariable Long seminarId) {
		return seminarRoomService.findSeminar(seminarId);
	}

	@PostMapping("/api/seminar/{seminarId}")
	public MemberAndCommentList getSeminarRoomInfo(@PathVariable Long seminarId, @RequestBody SeminarEnterDto seminarEnterDto) {
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
		List<SpeakerAndCommentList> comments = commentService.getCommentsBySeminarRoom(seminarId);
		return MemberAndCommentList.builder().member(member).commentListBySpeaker(comments).build();
	}

}