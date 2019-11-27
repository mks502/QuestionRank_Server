package com.depromeet.qr.dto;

import com.depromeet.qr.entity.SeminarRoom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeminarRoomDto {
	private String seminarTitle;
	private String fullURL;
	private String seminarPassword;

	public SeminarRoom toEntity() {
		return SeminarRoom.builder().seminarTitle(seminarTitle).seminarPassword(seminarPassword).fullURL(fullURL)
				.build();
	}
}
