package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo2.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> { // Generic -> 기본형 X
	// 1. 기본적인 CRUD 제공 : findAll(), findById()
	
	// 2. 규칙에 맞는 함수 정의
	List<ProfileEntity> findByMemberAndCurrentYnTrue(MemberEntity member);
	List<ProfileEntity> findByCurrentYnTrue();
	
	// 3. @Query (특정 member의 profile 가져오기)
	@Query("select m.mname, p.fname from ProfileEntity p "
			+ " join MemberEntity m on (m = p.member) where m.mid = ?1") // 오른쪽(member) 기준 join
	List<Object[]> getProfile(String mid);
	
	// 모든 member의 mid, profile의 count
	@Query("select m.mid, count(p) from MemberEntity m "
			+ " left outer join ProfileEntity p on (m = p.member) group by m.mid")
	List<Object[]> getProfileCount();
}