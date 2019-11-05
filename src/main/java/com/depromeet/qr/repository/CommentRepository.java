package com.depromeet.qr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.qr.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	public Comment findOneBycommentId(Long commentId);

	public List<Comment> findAllBySeminarRoom(Long seminarId);

}