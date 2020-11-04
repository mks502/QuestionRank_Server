package com.depromeet.qr.dto;

import java.util.List;

import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.Member;

import com.depromeet.qr.entity.SeminarRoom;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeminarAndCommentList {
	public SeminarRoom seminarRoom;
	public List<SpeakerAndCommentList> commentListBySpeaker;
}
