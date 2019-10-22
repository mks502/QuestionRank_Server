package com.depromeet.qr.service;

import static com.rosaloves.bitlyj.Bitly.as;
import static com.rosaloves.bitlyj.Bitly.shorten;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.depromeet.qr.dto.SeminarAndMemberDto;
import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.repository.MemberRepository;
import com.depromeet.qr.repository.SeminarRoomRepository;
import com.rosaloves.bitlyj.Url;

@Service
@PropertySource("classpath:/com/depromeet/qr/config/config.properties")
public class SeminarRoomService {
	@Autowired
	SeminarRoomRepository seminarRoomRepository;
	@Autowired
	MemberRepository memberRepository;

	@Value("${qr.addr}")
	private String QR_ADDR;

	public SeminarRoom createSeminar(SeminarRoom seminarRoom) throws MalformedURLException, IOException {
		SeminarRoom seminar = seminarRoomRepository.save(seminarRoom);
		Long seminarId = seminar.getSeminarId();
		SeminarRoom newSeminar = seminarRoomRepository.findOneBySeminarId(seminarId);
		String full = "/seminar/";
		String seminarid = Long.toString(newSeminar.getSeminarId());
		String longURL = QR_ADDR + "/mini_QR/";

		full = full.concat(seminarid);
		newSeminar.setFullURL(full);
		longURL = longURL.concat(full);
		newSeminar.setShortURL(createShortUrl(longURL));
		seminarRoomRepository.save(newSeminar);

		return newSeminar;
	}

	// bit.ly api를 사용하여 fullUrl을 shortUrl로 변환
	public String createShortUrl(String fullUrl) throws MalformedURLException, IOException {

		Url url = as("o_1duq7b20be", "R_01bd87bf046e49cd93816dd681925740").call(shorten(fullUrl));
		String shortUrl = url.getShortUrl();

		return shortUrl;
	}

	public SeminarRoom findSeminar(Long seminarId) {
		SeminarRoom seminarRoom = seminarRoomRepository.findOneBySeminarId(seminarId);
		return seminarRoom;
	}

	public SeminarAndMemberDto enterSeminarByMember(Long seminarId, Long mid) {
		SeminarRoom seminar = findSeminar(seminarId);
		if (seminar == null)
			return null;
		Member member = memberRepository.findOneBySeminarRoomAndMid(seminar,mid);
		if (member == null) {
			member = Member.builder().role("USER").seminarRoom(seminar).build();
			member = memberRepository.save(member);
		}
		return SeminarAndMemberDto.builder().member(member).seminarRoom(seminar).build();
	}

	public SeminarAndMemberDto enterSeminarByAdmin(Long seminarId, String password) {
		SeminarRoom seminar = findSeminar(seminarId);
		if (seminar == null)
			return null;
		if (!seminar.getSeminarPassword().equals(password))
			return null;
		Member member = memberRepository.findOneBySeminarRoomAndRole(seminar, "ADMIN");
		SeminarAndMemberDto seminarAndMember = SeminarAndMemberDto.builder().seminarRoom(seminar).member(member).build();
		return seminarAndMember;
	}
}
