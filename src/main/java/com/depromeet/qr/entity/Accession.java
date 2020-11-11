package com.depromeet.qr.entity;

import com.depromeet.qr.base.BaseEntity;
import com.depromeet.qr.constant.AccessionRole;
import lombok.*;

import javax.persistence.*;

@Table(name = "accession")
@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Accession extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accession_id")
	private long accessionId;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne
	@JoinColumn(name = "seminar_id", nullable = false)
	private SeminarRoom seminarRoom;

	@Column(name = "accession_role")
	@Enumerated(value = EnumType.STRING)
	private AccessionRole accessionRole;
}
