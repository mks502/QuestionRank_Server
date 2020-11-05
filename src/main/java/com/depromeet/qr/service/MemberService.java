package com.depromeet.qr.service;

import com.depromeet.qr.adapter.KakaoAdapter;
import com.depromeet.qr.dto.KakaoUserDto;
import com.depromeet.qr.entity.Member;
import com.depromeet.qr.exception.ApiFailedException;
import com.depromeet.qr.exception.NotFoundException;
import com.depromeet.qr.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
	private final MemberRepository memberRepository;
	private final KakaoAdapter kakaoAdapter;


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
					log.info("새로운 유저 등록!");
					final Member member = new Member();
					member.setKakaoId(kakaoId);
					member.setProfileImgUrl(profileImgUrl);
					member.setName(name);
					return memberRepository.save(member);
				});
	}

	@Transactional
	public Member getMember(Long memberId) {
		Member member = memberRepository.findOneByMemberId(memberId);
		if (member == null)
			throw new NotFoundException("존재하지 않는 멤버입니다");
		return member;
	}
}