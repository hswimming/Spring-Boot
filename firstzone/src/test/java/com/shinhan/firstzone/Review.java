package com.shinhan.firstzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.repository.PDSBoardRepository;
import com.shinhan.firstzone.repository.PDSFileRepository;
import com.shinhan.firstzone.repository.ProfileRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class Review {
	// ManyToOne Test
	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ProfileRepository pRepo;
	
	// OneToMany Test
	@Autowired
	PDSBoardRepository boardRepo;
	
	@Autowired
	PDSFileRepository fileRepo;
	
	@Transactional // Lazy이더라도 같은 transaction으로 묶음 fetch가 즉시로딩으로 수행
	@Test
	void f2() {
		// OneToMany Test
		boardRepo.findAll().forEach(board -> {
			System.out.println(board); // 즉시 로딩 (Eager : 옴), 지연 로딩 (Lazy : 안옴) default
		});
	}
	
	// @Test
	void f1() {
		// ManyToOne Test - select
		pRepo.findAll().forEach(profile -> {
			System.out.println(profile); // 즉시 로딩 (Eager : 옴) default, 지연 로딩 (Lazy : 안옴)
		});
	}
}