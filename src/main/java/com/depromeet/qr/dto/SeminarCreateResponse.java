package com.depromeet.qr.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeminarCreateResponse {

    private Long seminarId;
    private String seminarTitle;
    private String fullURL;
    private String shortURL;
    private String seminarPassword;
    private List<SpeakerResponse> speakerList;
}
