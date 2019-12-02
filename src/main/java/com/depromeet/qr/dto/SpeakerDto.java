package com.depromeet.qr.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpeakerDto {
	private String speakerName;
	private String speakerTopic;
	private String organization;
	private Long seminarId;
}
