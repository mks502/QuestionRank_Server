package com.depromeet.qr.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeminarCreateRequest {
	public SeminarRoomDto seminarRoomDto;
	public List<SpeakerDto> speakerList;
	public Long memberId;
}
