package com.depromeet.qr.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.qr.dto.MemberAndCommentList;
import com.depromeet.qr.dto.SeminarAdminDto;
import com.depromeet.qr.dto.SeminarCreateRequest;
import com.depromeet.qr.dto.SpeakerAndCommentList;
import com.depromeet.qr.dto.SpeakerDto;
import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.Speaker;
import com.depromeet.qr.service.CommentService;
import com.depromeet.qr.service.SeminarRoomService;
import com.depromeet.qr.service.SpeakerService;

import io.swagger.annotations.ApiOperation;

@RestController
public class SeminarRoomController {
	@Autowired
	SeminarRoomService seminarRoomService;
	@Autowired
	SpeakerService speakerService;
	@Autowired
	CommentService commentService;

	@ApiOperation(value="방 만들기")
	@PostMapping("api/seminar")
	public List<Speaker> createSeminarRoom(@RequestBody SeminarCreateRequest request) throws MalformedURLException, IOException {
		Member admin = seminarRoomService.createSeminar(request.getSeminarRoomDto());
		List<Speaker> result = new ArrayList<Speaker>();
		for (SpeakerDto speakerDto : request.getSpeakerList()) {
			speakerDto.setSeminarId(admin.getSeminarRoom().getSeminarId());
			Speaker s = speakerService.createSpeaker(speakerDto);
			result.add(s);
		}
		return result;
	}

	@GetMapping("api/seminar/enter/{seminarid}/{mid}")
	public MemberAndCommentList enterSeminarByMember(@PathVariable Long seminarid, @PathVariable(name="mid",required=false) Long mid) {
		List<SpeakerAndCommentList> comments = commentService.getCommentsBySeminarRoom(seminarid);
		Member member = seminarRoomService.enterSeminarByMember(seminarid, mid);
		return MemberAndCommentList.builder().member(member).commentListBySpeaker(comments).build();
	}

	@ApiOperation(value="방 번호와 비밀번호를 통한 admin 입장")
	@GetMapping("api/seminar/enter/admin")
	public MemberAndCommentList enterSeminarByAdmin(@ModelAttribute SeminarAdminDto seminarAdmin) {
		List<SpeakerAndCommentList> comments = commentService.getCommentsBySeminarRoom(seminarAdmin.getSeminarId());
		Member member = seminarRoomService.enterSeminarByAdmin(seminarAdmin.getSeminarId(), seminarAdmin.getPassword());
		return MemberAndCommentList.builder().member(member).commentListBySpeaker(comments).build();
	}
}