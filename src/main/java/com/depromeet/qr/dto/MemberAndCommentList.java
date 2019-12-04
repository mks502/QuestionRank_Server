package com.depromeet.qr.dto;

import java.util.List;

import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.Member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberAndCommentList {
	public Member member;
	public List<SpeakerAndCommentList> commentListBySpeaker;
}
