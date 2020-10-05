package com.depromeet.qr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.depromeet.qr.base.BaseEntity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long memberId;
	@Builder.Default
	private String role="USER";

	private String kakaoId;
	private String name;
	private String profileImgUrl;

	@ManyToOne
	@JoinColumn(name = "seminarId")
	private SeminarRoom seminarRoom;
}