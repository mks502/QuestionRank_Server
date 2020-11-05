package com.depromeet.qr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findOneByMemberId(Long memberId);
	Optional<Member> findOneByKakaoId(String kakaoId);
}