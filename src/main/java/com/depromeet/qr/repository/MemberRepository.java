package com.depromeet.qr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.qr.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}