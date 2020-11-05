package com.depromeet.qr.dto;

import com.depromeet.qr.entity.Comment;
import com.depromeet.qr.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDto {
    private Long answerId;
    private String content;
    private Member member;
}
