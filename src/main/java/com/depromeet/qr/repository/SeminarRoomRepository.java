package com.depromeet.qr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.qr.entity.SeminarRoom;

public interface SeminarRoomRepository extends JpaRepository<SeminarRoom, Long> {
	public SeminarRoom findOneBySeminarId(Long seminarId);
	public List<SeminarRoom> findAllBySeminarId(Long seminarId);
	
}