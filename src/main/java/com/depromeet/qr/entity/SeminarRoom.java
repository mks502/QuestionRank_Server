package com.depromeet.qr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.depromeet.qr.base.BaseEntity;
import com.depromeet.qr.dto.SeminarCreateResponse;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SeminarRoom extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long seminarId;

	@Column(nullable = false)
	private String seminarTitle;

	private String fullURL;
	private String shortURL;
	private String seminarPassword;

	public SeminarCreateResponse toResponse(){
		return SeminarCreateResponse.builder()
				.seminarId(this.seminarId).seminarPassword(this.seminarPassword).seminarTitle(this.seminarTitle).fullURL(this.fullURL)
				.shortURL(this.shortURL).build();
	}
}
