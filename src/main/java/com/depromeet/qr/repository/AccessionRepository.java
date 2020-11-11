package com.depromeet.qr.repository;

import com.depromeet.qr.constant.AccessionRole;
import com.depromeet.qr.entity.Accession;
import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccessionRepository extends JpaRepository<Accession, Long> {
    Optional<Accession> findOneByMemberAndSeminarRoom(Member member,SeminarRoom seminarRoom);
    List<Accession> findAllBySeminarRoomAndAccessionRole(SeminarRoom seminarRoom, AccessionRole accessionRole);
    List<Accession> findAllByMember(Member member);
}