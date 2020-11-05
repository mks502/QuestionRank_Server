package com.depromeet.qr.controller;

import java.util.List;

import com.depromeet.qr.dto.SeminarRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.qr.entity.Member;
import com.depromeet.qr.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/api/member/login")
	public ResponseEntity<Member> login(@RequestBody String accessToken) {
		System.out.println(accessToken);
		Member member = memberService.getOrCreateMember(accessToken);
		return ResponseEntity.ok().body(member);
	}

}