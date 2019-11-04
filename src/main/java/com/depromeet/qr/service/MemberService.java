package com.depromeet.qr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.exception.NotFoundException;
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
			throw new NotFoundException();
		Member member = Member.builder().role("USER").seminarRoom(seminarRoom).build();
		if (memberRepository.findOneBySeminarRoom(seminarRoom) == null)
			member.setRole("ADMIN");
		return memberRepository.save(member);
	}

	public Member getMember(Long mid) {
		Member member = memberRepository.findOneByMid(mid);
		if (member == null)
			throw new NotFoundException("존재하지 않는 멤버입니다");
		return member;
	}

	public List<Member> getMembersBySeminarRoom(Long seminarId) {
		SeminarRoom seminarRoom = seminarRoomRepository.findOneBySeminarId(seminarId);
		if (seminarRoom == null)
			throw new NotFoundException();
		List<Member> members = memberRepository.findAllBySeminarRoom(seminarRoom);
		if (members == null)
			throw new NotFoundException();
		return members;
	}

	public boolean deleteMembersBySeminarRoom(Long seminarId) {
		List<Member> members = getMembersBySeminarRoom(seminarId);
		if (members == null)
			return false;
		memberRepository.deleteInBatch(members);
		return true;
	}
}