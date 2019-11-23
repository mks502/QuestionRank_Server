package com.depromeet.qr.controller;

import java.io.IOException;
import java.net.MalformedURLException;
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
import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.service.CommentService;
import com.depromeet.qr.service.SeminarRoomService;

@RestController
public class SeminarRoomController {
	@Autowired
	SeminarRoomService seminarRoomService;
	@Autowired
	CommentService commentService;

	@PostMapping("api/seminar")
	public Member createSeminarRoom(@RequestBody SeminarRoom seminarRoom) throws MalformedURLException, IOException {
		return seminarRoomService.createSeminar(seminarRoom);
	}

	@GetMapping("api/seminar/enter/{seminarid}/{mid}")
	public MemberAndCommentList enterSeminarByMember(@PathVariable Long seminarid, @PathVariable Long mid) {
		List<Comment> comments = commentService.getCommentsBySeminarRoom(seminarid);
		Member member = seminarRoomService.enterSeminarByMember(seminarid, mid);
		return MemberAndCommentList.builder().member(member).commentList(comments).build();
	}

	@GetMapping("api/seminar/enter/admin")
	public MemberAndCommentList enterSeminarByAdmin(@ModelAttribute SeminarAdminDto seminarAdmin) {
		List<Comment> comments = commentService.getCommentsBySeminarRoom(seminarAdmin.getSeminarId());
		Member member = seminarRoomService.enterSeminarByAdmin(seminarAdmin.getSeminarId(), seminarAdmin.getPassword());
		return MemberAndCommentList.builder().member(member).commentList(comments).build();
	}
}