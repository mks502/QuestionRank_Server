package com.depromeet.qr.dto;

import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeminarAndMemberDto {
	private SeminarRoom seminarRoom;
	private Member member;
}