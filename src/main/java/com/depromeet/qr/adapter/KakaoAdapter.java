package com.depromeet.qr.adapter;

import com.depromeet.qr.dto.KakaoUserDto;
import com.depromeet.qr.exception.ApiFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class KakaoAdapter {
	// 카카오 서버에 토큰을 통해 사용자 정보를 가져오기 위해 RestTemplate 사용
	private final RestTemplate restTemplate;
    //// 토큰을 이용해 사용자 정보 가져오기
    public KakaoUserDto getUserInfo(String kakaoToken) {
        final URI requestUrl = UriComponentsBuilder.fromHttpUrl("https://kapi.kakao.com/v2/user/me")
                .build(true)
                .toUri();
        
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + kakaoToken);

        final HttpEntity httpEntity = new HttpEntity(httpHeaders);
        final ResponseEntity<KakaoUserDto> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, httpEntity, KakaoUserDto.class);
        System.out.println("check re"+responseEntity.getStatusCode());
        if (! responseEntity.getStatusCode().equals( HttpStatus.OK) ) {
            // 토큰을 이용해 카카오로부터 정보를 못 받았을 경우
            throw new ApiFailedException("Failed to get User Info from kakao", HttpStatus.SERVICE_UNAVAILABLE);
        }
        return responseEntity.getBody();
    }

}
