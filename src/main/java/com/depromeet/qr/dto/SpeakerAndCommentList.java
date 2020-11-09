package com.depromeet.qr.dto;

import java.util.List;

import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.Speaker;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpeakerAndCommentList {
	private SpeakerResponse speaker;
	private List<CommentDto> commentList;
	private List<CommentDto> commentRankingList;
}
