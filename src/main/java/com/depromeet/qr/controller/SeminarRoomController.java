package com.depromeet.qr.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.qr.dto.SeminarAdminDto;
import com.depromeet.qr.entity.Member;
import com.depromeet.qr.entity.SeminarRoom;
import com.depromeet.qr.service.SeminarRoomService;

@RestController
public class SeminarRoomController {
	@Autowired
	SeminarRoomService seminarRoomService;
	
	@PostMapping("api/seminar")
	public Member createSeminarRoom(@RequestBody SeminarRoom seminarRoom) throws MalformedURLException, IOException {
		return seminarRoomService.createSeminar(seminarRoom);
	}
	@GetMapping("api/seminar/enter/{seminarid}/{mid}")
	public Member enterSeminarByMember(@PathVariable Long seminarid,@PathVariable Long mid){
		return seminarRoomService.enterSeminarByMember(seminarid, mid);
	}
	@GetMapping("api/seminar/enter/admin")
	public Member enterSeminarByAdmin(@ModelAttribute SeminarAdminDto seminarAdmin){
		return seminarRoomService.enterSeminarByAdmin(seminarAdmin.getSeminarId(), seminarAdmin.getPassword());
	}
	
}