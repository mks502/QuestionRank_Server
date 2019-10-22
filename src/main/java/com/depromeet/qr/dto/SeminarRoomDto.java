package com.depromeet.qr.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeminarRoomDto {
	private String seminarTitle;
	private String fullURL;
	private String shortURL;
	private String seminarPassword;
}
