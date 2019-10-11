package com.depromeet.qr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;

public interface MemberRepository extends JpaRepository<Member, Long> {
	public Member findOneBySeminarRoom(SeminarRoom seminarRoom);
	public Member findOneByMid(Long mid);
	public List<Member> findAllBySeminarRoom(SeminarRoom seminarRoom);
}