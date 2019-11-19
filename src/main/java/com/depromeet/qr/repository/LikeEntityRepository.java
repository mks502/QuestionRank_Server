package com.depromeet.qr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.LikeEntity;
import com.depromeet.qr.entity.Member;

public interface LikeEntityRepository extends JpaRepository<LikeEntity, Long> {
	public LikeEntity findOneByCommentAndMember(Comment comment, Member member);
	
}
