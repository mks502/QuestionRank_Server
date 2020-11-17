package com.depromeet.qr.service;

import static com.rosaloves.bitlyj.Bitly.as;
import static com.rosaloves.bitlyj.Bitly.shorten;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.depromeet.qr.constant.AccessionRole;
import com.depromeet.qr.dto.MemberResponseDto;
import com.depromeet.qr.entity.Accession;
import com.depromeet.qr.repository.AccessionRepository;
import com.depromeet.qr.util.UtilEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
	private final AccessionRepository accessionRepository;

	private final UtilEncoder utilEncoder;

	@Value("${environments.url}")
	private String ADDR;

	@Transactional
	public SeminarRoom createSeminar(SeminarRoomDto seminarRoomDto) throws MalformedURLException, IOException {
		String password = utilEncoder.encoding(LocalDateTime.now().toString()+seminarRoomDto.getSeminarTitle());
		seminarRoomDto.setSeminarPassword(password);
		SeminarRoom seminar = seminarRoomRepository.save(seminarRoomDto.toEntity());
		Long seminarId = seminar.getSeminarId();
		SeminarRoom newSeminar = findSeminar(seminarId);
		newSeminar.setFullURL(ADDR+"/"+seminarId);
		newSeminar.setShortURL(createShortUrl(newSeminar.getFullURL()));

		return seminarRoomRepository.save(newSeminar);
	}

	// bit.ly api를 사용하여 fullUrl을 shortUrl로 변환
	@Transactional
	public String createShortUrl(String fullUrl) throws MalformedURLException, IOException {

		Url url = as("o_1duq7b20be", "R_01bd87bf046e49cd93816dd681925740").call(shorten(fullUrl));
		String shortUrl = url.getShortUrl();

		return shortUrl;
	}

	public SeminarRoom findSeminar(Long seminarId) {
		SeminarRoom seminarRoom = seminarRoomRepository.findOneBySeminarId(seminarId).orElseThrow(()-> new NotFoundException("Not Found SeminarRoom"));
		return seminarRoom;
	}

	public SeminarRoom findBySeminarByPassword(String password){
		return seminarRoomRepository.findOneBySeminarPassword(password).orElseThrow(()-> new NotFoundException("WRONG PASSWORD"));
	}

	public MemberResponseDto findByMemberListBySeminarAndRole(Long seminarId, String accessionRole){
		SeminarRoom seminarRoom = findSeminar(seminarId);
		List<Accession> accessionList;
		List<Member> memberList = new ArrayList<Member>();
		try {
			AccessionRole role =AccessionRole.valueOf(accessionRole.toUpperCase());
			accessionList = accessionRepository.findAllBySeminarRoomAndAccessionRole(seminarRoom,role);
		}
		catch (Exception e){
			throw new BadRequestException("WRONG REQUEST");
		}
		for (Accession accession : accessionList){
			memberList.add(accession.getMember());
		}
		return MemberResponseDto.builder()
				.memberList(memberList).type(accessionRole.toUpperCase()).build();
	}

	public List<SeminarRoom> getSeminarRoomListByMember(Long memberId){
		Member member = memberRepository.findOneByMemberId(memberId);
		List<Accession> accessionList = accessionRepository.findAllByMember(member);
		List<SeminarRoom> seminarRoomList = new ArrayList<>();

		for(Accession accession : accessionList){
			seminarRoomList.add(accession.getSeminarRoom());
		}
		return seminarRoomList;
	}
}
