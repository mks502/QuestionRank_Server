package com.depromeet.qr.service;

import java.util.List;

import javax.transaction.Transactional;

import com.depromeet.qr.adapter.KakaoAdapter;
import com.depromeet.qr.dto.KakaoUserDto;
import com.depromeet.qr.exception.ApiFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.exception.BadRequestException;
import com.depromeet.qr.exception.NotFoundException;
import com.depromeet.qr.repository.MemberRepository;
import com.depromeet.qr.repository.SeminarRoomRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final SeminarRoomRepository seminarRoomRepository;
	private final KakaoAdapter kakaoAdapter;
	private final SeminarRoomService seminarRoomService;


	@Transactional
	public Member getOrCreateMember(String kakaoToken) {
		final KakaoUserDto kakaoUserDto = kakaoAdapter.getUserInfo(kakaoToken);
		if (kakaoUserDto == null) {
			throw new ApiFailedException("Failed to get user info from kakao api", HttpStatus.SERVICE_UNAVAILABLE);
		}
		final String kakaoId = kakaoUserDto.getId().toString();
		final String profileImgUrl = kakaoUserDto.getProfileImage();
		final String name = kakaoUserDto.getUserName();
		return memberRepository.findOneByKakaoId(kakaoId)
				.orElseGet(() -> {    //해당 카카오id가 우리 서버에 존재 하지않으면 회원 등록
					System.out.println("새로운 유저 등록!");
					final Member member = new Member();
					member.setKakaoId(kakaoId);
					member.setProfileImgUrl(profileImgUrl);
					member.setName(name);
					return memberRepository.save(member);
				});
	}

	@Transactional
	public Member createMember(Long seminarId) {
		SeminarRoom seminarRoom = seminarRoomService.findSeminar(seminarId);
		Member member = Member.builder().role("USER").seminarRoom(seminarRoom).build();
		if (memberRepository.findOneBySeminarRoom(seminarRoom) == null)
			member.setRole("ADMIN");
		return memberRepository.save(member);
	}

	@Transactional
	public Member getMember(Long memberId) {
		Member member = memberRepository.findOneByMemberId(memberId);
		if (member == null)
			throw new NotFoundException("존재하지 않는 멤버입니다");
		return member;
	}

	@Transactional
	public List<Member> getMembersBySeminarRoom(Long seminarId) {
		SeminarRoom seminarRoom = seminarRoomService.findSeminar(seminarId);
		List<Member> members = memberRepository.findAllBySeminarRoom(seminarRoom);
		if (members == null)
			throw new NotFoundException();
		return members;
	}

	@Transactional
	public void deleteMembersBySeminarRoom(Long seminarId) {
		List<Member> members = getMembersBySeminarRoom(seminarId);
		memberRepository.deleteInBatch(members);
	}
	
	@Transactional
	public boolean checkRoleAdmin(Long memberId) {
		Member member = getMember(memberId);
		if(member.getRole()=="ADMIN")
			new BadRequestException("ADMIN이 아닙니다");
		return true;
	}
}