package com.depromeet.qr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeakerResponse {
    private String speakerName;
    private String speakerTopic;
    private String organization;
}
