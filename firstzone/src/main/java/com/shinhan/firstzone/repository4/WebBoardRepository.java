package com.shinhan.firstzone.repository4;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo4.WebBoardEntity;

// 레파짓토리에서 키를 사용하므로 테이블에도 키가 꼭 있어야 한다.
// interface 설계
public interface WebBoardRepository extends JpaRepository<WebBoardEntity, Long>,
																	QuerydslPredicateExecutor<WebBoardEntity> {

	// 1. JpaRepository, QuerydslPredicateExecutor 상속 : 기본 제공 함수들 사용 가능
	// 2. 규칙이 있는 함수 정의 : findBy ~
	Page<WebBoardEntity> findByWriter(MemberEntity member, Pageable paging); // entity가 들어있는 Page, Page가 return
	// Page<WebBoardEntity> -> List<WebBoardDTO>
	
	// 3. JPQL : select -> @Query / DML -> @Modifying, @Query
	// 4. Querydsl : 동적 SQL 생성 가능
}