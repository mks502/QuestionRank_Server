package com.depromeet.qr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.depromeet.qr.base.BaseEntity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class LikeEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long lid;
	
	@OneToOne
	@JoinColumn(name = "commentId")
	private Comment comment;
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

}
