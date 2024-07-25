package com.shinhan.firstzone.vo3;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_usercellphone3")
public class UserCellPhoneVO3 {
	// @OneToOne, 양방향일때
	// JAP를 사용하려면 @MapsId, @Id 둘 다 사용해야 함
	
	@Id
	String userAA;
	
	// 식별자로 참조하기
	@MapsId // 참조하는 쪽
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE) // 추가해야 같이 삭제 가능
	@JoinColumn(name = "user_id") // PK
	UserVO3 user3;
	
	String phoneNumber;
	String model;
}