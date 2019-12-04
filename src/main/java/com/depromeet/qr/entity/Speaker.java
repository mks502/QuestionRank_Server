package com.depromeet.qr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Speaker {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long speakerId;
	private String speakerName;
	private String speakerTopic;
	private String organization;
	
	@ManyToOne
	@JoinColumn(name = "seminarId")
	private SeminarRoom seminarRoom;
}
