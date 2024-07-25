package com.shinhan.firstzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository3.PhoneVORepository;
import com.shinhan.firstzone.repository3.PhoneVORepository2;
import com.shinhan.firstzone.repository3.UserVO3Repository;
import com.shinhan.firstzone.repository3.UserVORepository;
import com.shinhan.firstzone.vo3.UserCellPhoneVO;
import com.shinhan.firstzone.vo3.UserCellPhoneVO2;
import com.shinhan.firstzone.vo3.UserCellPhoneVO3;
import com.shinhan.firstzone.vo3.UserVO;
import com.shinhan.firstzone.vo3.UserVO2;
import com.shinhan.firstzone.vo3.UserVO3;

@SpringBootTest
public class OneToOneTest {
	@Autowired
	UserVORepository uRepo;
	
	@Autowired
	PhoneVORepository pRepo;
	
	@Autowired
	PhoneVORepository2 pRepo2;
	
	@Autowired
	UserVO3Repository user3Repo; 
	
	@Test
	void f3() {
		UserCellPhoneVO3 phone = UserCellPhoneVO3.builder()
												.phoneNumber("010-3333-4444")
												.model("갤럭시")
												.build();
		
		UserVO3 user = UserVO3.builder()
								.userid("황")
								.username("수영")
								.phone(phone)
								.build();
		
		phone.setUser3(user);
		user3Repo.save(user); // user를 통해 양쪽 다 저장
	}
	
	// @Test
	void f2() {
		UserVO2 user = UserVO2.builder()
								.userid("coffee")
								.username("스타벅스")
								.build();
		
		UserCellPhoneVO2 phone = UserCellPhoneVO2.builder()
												.phoneNumber("010-1111-2222")
												.model("사과")
												.user(user)
												.build();
		
		pRepo2.save(phone);
	}
	
	// @Test
	void f1() {
		UserCellPhoneVO phone = UserCellPhoneVO.builder()
											 .phoneNumber("010-1234-5678")
											 .model("아이폰")
											 .build();
		
		UserCellPhoneVO newphone = pRepo.save(phone);
		
		// 새로운 phone 먼저 저장 후 user에서 이용
		UserVO uservo = UserVO.builder()
								.userid("swimming")
								.username("sy")
								.phone(newphone)
								.build();
		
		uRepo.save(uservo);
	}
}