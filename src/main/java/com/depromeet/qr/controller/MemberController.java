package com.depromeet.qr.controller;

import java.util.List;

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
public class MemberController {
	@Autowired
	MemberService memberService;

	@PostMapping("api/member/join")
	public ResponseEntity<Member> join(@RequestBody Long seminarId) {
		Member member = memberService.createMember(seminarId);
		
		return ResponseEntity.ok().body(member);
	}
	@GetMapping("api/member/{mid}")
	public ResponseEntity<Member> getMember(@PathVariable Long mid) {
		Member member = memberService.getMember(mid);
		return ResponseEntity.ok().body(member);
	}
	@GetMapping("api/members/{seminarId}")
	public ResponseEntity<List<Member>> getMembersBySeminarId(@PathVariable Long seminarId) {
		List<Member> members = memberService.getMembersBySeminarRoom(seminarId);
	
		return ResponseEntity.ok().body(members);
	}
	@DeleteMapping("api/members/{seminarId}")
	public ResponseEntity<Boolean> deleteMembersBySeminarId(@PathVariable Long seminarId) {
		if(memberService.deleteMembersBySeminarRoom(seminarId))
			return ResponseEntity.ok().body(true);
		return ResponseEntity.badRequest().body(false);
	}
}