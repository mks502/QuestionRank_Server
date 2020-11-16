package com.depromeet.qr.dto;

import com.depromeet.qr.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long commentId;
    private String content;
    private Integer likeCount = 0;
    private Member member;
    private Long speakerId;

    @Builder.Default
    private boolean isLiked = false;
}
