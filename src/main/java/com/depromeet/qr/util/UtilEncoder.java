package com.depromeet.qr.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class UtilEncoder {
	public String encoding(String target) throws UnsupportedEncodingException {
		String url = Base64.getEncoder().encodeToString(target.getBytes());
		String result = URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
		return result;
	}
	
	public String decoding(String target) throws UnsupportedEncodingException {
		String url = URLDecoder.decode(target,StandardCharsets.UTF_8.toString());
		byte[] decodedBytes = Base64.getDecoder().decode(target);
		String result = new String(decodedBytes);
		return result;
	}
}
