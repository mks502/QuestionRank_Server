package com.depromeet.qr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.qr.entity.SeminarRoom;

public interface SeminarRoomRepository extends JpaRepository<SeminarRoom, Long> {
	Optional<SeminarRoom> findOneBySeminarId(Long seminarId);
	List<SeminarRoom> findAllBySeminarId(Long seminarId);
	Optional<SeminarRoom> findOneBySeminarPassword(String seminarPassword);
}