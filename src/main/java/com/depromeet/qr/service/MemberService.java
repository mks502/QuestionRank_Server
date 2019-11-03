package com.depromeet.qr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.repository.MemberRepository;
import com.depromeet.qr.repository.SeminarRoomRepository;

@Service
public class MemberService {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	SeminarRoomRepository seminarRoomRepository;

	public Member createMember(Long seminarId) {
		SeminarRoom seminarRoom = seminarRoomRepository.findOneBySeminarId(seminarId);
		if (seminarRoom == null)
			return null;
		Member member = Member.builder().role("USER").seminarRoom(seminarRoom).build();
		if (memberRepository.findOneBySeminarRoom(seminarRoom) == null)
			member.setRole("ADMIN");
		return memberRepository.save(member);
	}

	public Member getMember(Long mid) {
		Member member = memberRepository.findOneByMid(mid);
		if (member == null)
			return null;
		return member;
	}

	public List<Member> getMembersBySeminarRoom(Long seminarId) {
		SeminarRoom seminarRoom = seminarRoomRepository.findOneBySeminarId(seminarId);
		if (seminarRoom == null)
			return null;
		List<Member> members = memberRepository.findAllBySeminarRoom(seminarRoom);
		if (members == null)
			return null;
		return members;
	}

	public boolean deleteMembersBySeminarRoom(Long seminarId) {
		List<Member> members = getMembersBySeminarRoom(seminarId);
		if(members == null)
			return false;
		memberRepository.deleteInBatch(members);
		return true;
	}
}