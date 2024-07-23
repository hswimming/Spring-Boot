package com.shinhan.firstzone.vo2;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "t_member")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberEntity {
	@Id // Primary Key
	String mid;
	
	String mpassword;
	
	String mname;
	
	@Enumerated(EnumType.STRING) // 순서(orinal)가 기본값
	MemberRole mrole;
}