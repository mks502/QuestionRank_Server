package com.depromeet.qr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.entity.Speaker;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
	List<Speaker> findAllBySeminarRoom(SeminarRoom seminar);
	Optional<Speaker> findOneBySpeakerId(Long speakerId);
}
