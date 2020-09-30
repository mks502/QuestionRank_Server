package com.depromeet.qr.service;

import static com.rosaloves.bitlyj.Bitly.as;
import static com.rosaloves.bitlyj.Bitly.shorten;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import com.depromeet.qr.util.UtilEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.depromeet.qr.dto.SeminarRoomDto;
import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.exception.BadRequestException;
import com.depromeet.qr.exception.NotFoundException;
import com.depromeet.qr.repository.MemberRepository;
import com.depromeet.qr.repository.SeminarRoomRepository;
import com.rosaloves.bitlyj.Url;

@Service
@RequiredArgsConstructor
public class SeminarRoomService {

	private final SeminarRoomRepository seminarRoomRepository;
	private final MemberRepository memberRepository;
	private final MemberService memberService;
	private final UtilEncoder utilEncoder;
	
	@Value("${environments.url}")
	private String ADDR;

	@Transactional
	public Member createSeminar(SeminarRoomDto seminarRoomDto) throws MalformedURLException, IOException {
		String password = utilEncoder.encoding(LocalDateTime.now().toString()+seminarRoomDto.getSeminarTitle());
		seminarRoomDto.setSeminarPassword(password);
		SeminarRoom seminar = seminarRoomRepository.save(seminarRoomDto.toEntity());
		Long seminarId = seminar.getSeminarId();
		SeminarRoom newSeminar = seminarRoomRepository.findOneBySeminarId(seminarId);
		newSeminar.setFullURL(ADDR+"/"+seminarId);
		System.out.println(newSeminar.getFullURL());
		newSeminar.setShortURL(createShortUrl(newSeminar.getFullURL()));
		Member member = Member.builder().role("ADMIN").seminarRoom(seminarRoomRepository.save(newSeminar)).build();
		return memberRepository.save(member);
	}

	// bit.ly api를 사용하여 fullUrl을 shortUrl로 변환
	@Transactional
	public String createShortUrl(String fullUrl) throws MalformedURLException, IOException {

		Url url = as("o_1duq7b20be", "R_01bd87bf046e49cd93816dd681925740").call(shorten(fullUrl));
		String shortUrl = url.getShortUrl();

		return shortUrl;
	}

	public SeminarRoom findSeminar(Long seminarId) {
		SeminarRoom seminarRoom = seminarRoomRepository.findOneBySeminarId(seminarId);
		if (seminarRoom == null)
			throw new NotFoundException("Not Found SeminarRoom");
		return seminarRoom;
	}

	@Transactional
	public Member enterSeminarByMember(Long seminarId, Long mid) {
		SeminarRoom seminar = findSeminar(seminarId);

		Member member = memberRepository.findOneBySeminarRoomAndMid(seminar, mid);
		if (member == null) {
			member = Member.builder().role("USER").seminarRoom(seminar).build();
			member = memberRepository.save(member);
		}
		return member;
	}

	@Transactional
	public Member enterSeminarByAdmin(Long seminarId, String password) {
		SeminarRoom seminar = findSeminar(seminarId);
		if (!seminar.getSeminarPassword().equals(password))
			throw new BadRequestException("wrong password");
		Member member = memberRepository.findOneBySeminarRoomAndRole(seminar, "ADMIN");
		return member;
	}
	
//	@Transactional
//	public Member updateSeminarRoom(Long seminarId, Long memberId, SeminarRoomDto seminar) {
//		memberService.checkRoleAdmin(memberId);
//		SeminarRoom seminarRoom = findSeminar(seminarId);
//	}
}
