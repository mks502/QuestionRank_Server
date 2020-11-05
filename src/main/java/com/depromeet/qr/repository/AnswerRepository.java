package com.depromeet.qr.repository;

import com.depromeet.qr.entity.Answer;
import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByComment(Comment comment);
}