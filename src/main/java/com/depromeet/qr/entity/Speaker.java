package com.depromeet.qr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.depromeet.qr.base.BaseEntity;
import com.depromeet.qr.dto.SpeakerResponse;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Speaker extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long speakerId;
	private String speakerName;
	private String speakerTopic;
	private String organization;
	
	@ManyToOne
	@JoinColumn(name = "seminarId")
	private SeminarRoom seminarRoom;

	public SpeakerResponse toResponseDto(){
		return SpeakerResponse.builder()
				.speakerName(this.speakerName).speakerTopic(this.speakerTopic).organization(this.organization).build();
	}
}
