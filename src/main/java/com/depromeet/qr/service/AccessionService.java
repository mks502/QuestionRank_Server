package com.depromeet.qr.service;

import com.depromeet.qr.constant.AccessionRole;
import com.depromeet.qr.entity.Accession;
import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.exception.NotFoundException;
import com.depromeet.qr.repository.AccessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessionService {
	private final AccessionRepository accessionRepository;

	public void accessSeminarRoom(Member member, SeminarRoom seminarRoom, AccessionRole accessionRole){
		Accession accession = Accession.builder()
				.member(member).seminarRoom(seminarRoom).accessionRole(accessionRole)
				.build();
		accessionRepository.save(accession);
	}

	public Accession findAccessionByMemberAndSeminar(Member member,SeminarRoom seminarRoom){
		return accessionRepository.findOneByMemberAndSeminarRoom(member,seminarRoom).orElseThrow(()->new NotFoundException("NOT FOUND ACCESS"));
	}
}