package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.repository.ProfileRepository;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo2.MemberRole;
import com.shinhan.firstzone.vo2.ProfileEntity;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class ManyToOneTest {
	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ProfileRepository pRepo;
	
	@Test
	void getProfileCount() {
		// 모든 member의 mid, profile의 count
		pRepo.getProfileCount().forEach(arr -> {
			log.info(Arrays.toString(arr));
			log.info("mid : " + arr[0] + ", profile count : " + arr[1]);
			log.info("========================================");
		});
	}
	
	// @Test
	void getProfile() {
		// table join으로 여러번 select 하지 않고 한 번에 조회
		pRepo.getProfile("user5").forEach(arr -> {
			log.info(Arrays.toString(arr));
			log.info("이름 : " + arr[0] + ", 파일 이름 : " + arr[1]);
			log.info("========================================");
		});
	}
	
	// @Test
	void selectByCurrent() {
		// currentYn=true
		pRepo.findByCurrentYnTrue().forEach(p -> log.info(p));
	}
	
	// @Test
	void selectByMember() {
		// 특정 Member(user5)의 Profile 정보 조회
		// from t_profile where member_mid = 'user5' and currentYn  = 1
		MemberEntity member = MemberEntity.builder().mid("user5").build();
		
		pRepo.findByMemberAndCurrentYnTrue(member).forEach(p -> log.info(p));
	}
	
	// @Test
	void selectByFno() {
		// PK인 fno=1의 Profile 정보 조회
		pRepo.findById(1L).ifPresentOrElse(profile -> {
			System.out.println("파일 이름 : " + profile.getFname());
			System.out.println(profile.isCurrentYn() ? "현재 profile" : "과거의 profile"); // boolean type 이므로 true인 경우, false인 경우
			System.out.println("이름 : " + profile.getMember().getMname());
			System.out.println("권한 : " + profile.getMember().getMrole().name());
			
		}, () -> {
			System.out.println("존재하지 않는 profile 입니다.");
		});
	}
	
	// @Test
	void selectProfile() {
		// 전체 조회
		pRepo.findAll().forEach(p -> {
			log.info(p);
		});
	}
	
	// @Test
	void selectByMid() {
		// 선택 조회
		mRepo.findById("user5").ifPresentOrElse(m -> {
			log.info(m);
			
		}, () -> {
			log.info("존재하지 않는 member 입니다.");
		});
	}
	
	// @Test
	void profileInsert() {
		MemberEntity member = MemberEntity.builder().mid("user5").build();
		
		IntStream.rangeClosed(1, 7).forEach(i -> {
			ProfileEntity profile = ProfileEntity.builder()
										  .fname("face-" + 1)
										  .currentYn(i == 5 ? true : false)
										  .member(member) // admin1의 profile
										  .build();
			
			pRepo.save(profile);
		});
	}
	
	// @Test
	void memberInsert() {
		// Member insert
		// 규칙 없으면 loop 돌지 않아도 됨
		IntStream.rangeClosed(1, 2).forEach(i -> {
			/*
			MemberEntity member = MemberEntity.builder()
												.mid(i == 1 ? "king" : "user" + i)
												.mname("황수영" + i)
												.mpassword("1234")
												.mrole(i == 1 ? MemberRole.MANAGER : MemberRole.USER)
												.build();
			*/
			
			MemberEntity member = MemberEntity.builder()
					.mid("admin" + i)
					.mname("보리" + i)
					.mpassword("1234")
					.mrole(MemberRole.ADMIN)
					.build();
			
			mRepo.save(member);
		});
	}
}