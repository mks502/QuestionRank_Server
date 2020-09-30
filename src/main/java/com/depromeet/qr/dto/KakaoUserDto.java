package com.depromeet.qr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
//카카오에서 응답해주는 형식에 맞춰 사용자 정보를 가져옴
public class KakaoUserDto {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("properties")
    private Map<String, String> properties;

    @JsonProperty("kakao_account")
    private Map<String, Object> account;

    public String getUserName() {
        return this.properties.get("nickname");
    }

    public String getProfileImage() {
        return this.properties.get("profile_image");
    }
}