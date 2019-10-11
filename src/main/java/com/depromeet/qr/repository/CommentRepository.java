package com.depromeet.qr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.qr.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}