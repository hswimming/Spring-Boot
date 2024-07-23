package com.shinhan.firstzone.vo2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "t_profile")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProfileEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Long fno;
	
	String fname;
	
	boolean currentYn;
	
	// 칼럼 이름은 filed이름_키field -> member_mid 칼럼이 생성된다.
	// 하나의 member는 여러개의 Profile을 갖는다. -> 여러개의 Profile은 하나의 Member의 소유이다. (n : 1 관계)
	@ManyToOne
	MemberEntity member;
}