package com.depromeet.qr.service;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.depromeet.qr.dto.SpeakerDto;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.entity.Speaker;
import com.depromeet.qr.repository.SpeakerRepository;

@Service
@RequiredArgsConstructor
public class SpeakerService {

	private final SpeakerRepository speakerRepository;
	private final SeminarRoomService seminarRoomService;
	
	@Transactional
	public Speaker createSpeaker(SpeakerDto speakerDto) {
		SeminarRoom seminar = seminarRoomService.findSeminar(speakerDto.getSeminarId());
		Speaker speaker = Speaker.builder().seminarRoom(seminar).speakerName(speakerDto.getSpeakerName())
				.speakerTopic(speakerDto.getSpeakerTopic()).organization(speakerDto.getOrganization()).build();
		return speakerRepository.save(speaker);
	}

}
