package com.depromeet.qr.dto;

import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.Member;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberResponseDto {
	private String type;
	private List<Member> memberList;
}
