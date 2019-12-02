package com.depromeet.qr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.entity.Speaker;
import com.depromeet.qr.entity.Speaker;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	public Comment findOneBycommentId(Long commentId);

	public List<Comment> findAllBySeminarRoom(SeminarRoom seminar);
	public List<Comment> findAllBySpeaker(Speaker speaker);
	public List<Comment> findTop3BySeminarRoomOrderByLikeCountDesc(SeminarRoom seminarRoom);
	public List<Comment> findTop3BySpeakerOrderByLikeCountDesc(Speaker speaker);

}