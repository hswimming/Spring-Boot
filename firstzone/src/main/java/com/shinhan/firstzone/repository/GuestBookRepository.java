package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shinhan.firstzone.vo2.GuestBookEntity;

public interface GuestBookRepository extends CrudRepository<GuestBookEntity, Long>,
																	 PagingAndSortingRepository<GuestBookEntity, Long>,
																	 QuerydslPredicateExecutor<GuestBookEntity> { // 동적 SQL 사용
	
	// 1. 기본 CRUD 함수 제공, PagingAndSort 가능
	
	// 2. 규칙에 맞는 함수 추가 가능
	List<GuestBookEntity> findByRegDateIsNull();
	
	// 3. @Query : JPQL (* 사용 불가능)
	/*
	@Query("select b from GuestBookEntity b where regDate is null") // entity 이름
	List<GuestBookEntity> findByRegDateIsNull2();
	*/
	
	@Query("select b from #{#entityName} b where regDate is null") // entity 이름 가변적으로 읽기
	List<GuestBookEntity> findByRegDateIsNull2();
	
	// 4. @Query, nativeQuery 가능
	@Query(value = "select b from t_questbook b where regDate is null", nativeQuery = true)
	List<GuestBookEntity> findByRegDateIsNull3();
	
	@Query("select b from #{#entityName} b where b.regDate is null and b.gno > ?1")
	List<GuestBookEntity> findByRegDateIsNull4(Long gno);
}