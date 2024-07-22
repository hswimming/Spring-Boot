package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.firstzone.vo.BoardEntity;


// CRUD 작업 할 테이블의 이름, 키(PK) 속성
// Repository 설계
// JPA -> interface 기반으로 구현 class 생성
public interface BoardRepository extends CrudRepository<BoardEntity, Long>, // 기본 CRUD
															  PagingAndSortingRepository<BoardEntity, Long> {  // 기본 CRUD +paging, sort
	// CrudRepository 상속 받아서 find, select 등 사용 가능
	
	// 1. 기본 CRUD 제공 : findAll(), findById(), save(), count(), deleteById()
	// 2. 규칙에 맞는 함수를 정의
	// https://docs.spring.io/spring-data/jpa/docs/2.5.1/reference/html/#jpa.query-methods
	
	List<BoardEntity> findByWriter(String writer); // where writer = 'A'
	List<BoardEntity> findByTitleStartingWith(String writer); // where title 'A%'
	List<BoardEntity> findByTitleEndingWith(String writer); // where title %'A'
	List<BoardEntity> findByTitleContaining(String writer); // where title '%A%'
	List<BoardEntity> findByBnoBetween(Long bno1, Long bno2); // where bno >= ? and bno <= ?
	
	// 이런 문장도 가능
	// where content like '%aa%' and (bno >= ? and bno <= ?) order by bno desc
	List<BoardEntity> findByContentLikeAndBnoBetweenOrderByBnoDesc(String content, Long bno1, Long bno2);
	
	// paging 추가
	// where bno > ? order by bno desc
	List<BoardEntity> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable pageable);
	
	// 3. JPQL(JPA Query Language) 이용 : 복잡한 SQL문 생성 시 이용
	// 주의 : nativeQuery와 비슷하지만 같지는 않다, ex) select * from (* 사용 불가능)
	@Query("select b from BoardEntity b "
			+ " where b.title like '%?1%' and b.content like '%?2%' order by bno desc")
	List<BoardEntity> jpqlTest1(String title, String content); // 파라미터 순서대로 작성
	
	@Query("select b from BoardEntity b "
			+ " where b.title like %:tt% and b.content like %:cc% order by bno desc")
	List<BoardEntity> jpqlTest2(@Param("tt") String title, @Param("cc") String content); // 파라미터 순서가 바뀔 수 있으므로 이름으로 작성
	
	// entity 이름을 직접 작성하지 않고 가변적으로 받음
	@Query("select b from #{#entityName} b "
			+ " where b.title like %:tt% and b.content like %:cc% order by bno desc")
	List<BoardEntity> jpqlTest3(@Param("tt") String title, @Param("cc") String content);
	
	@Query("select b.title, b.content, b.writer from #{#entityName} b "
			+ " where b.title like %:tt% and b.content like %:cc% order by bno desc")
	List<Object[]> jpqlTest4(@Param("tt") String title, @Param("cc") String content);
	// select 뒤에 어떤 값을 받을지 모름 -> List 타입을 Object로 받기
	
	// || 연산자 사용 주의 : oracle DB 외에 지원 하지 않는 경우 있음
	@Query(value = "select * from t_board b "
			+ " where b.title like %?1% and b.content like %?2% order by b.bno desc", nativeQuery = true)
	List<BoardEntity> jpqlTest5(String title, String content); // 파라미터 순서대로 작성
	
	// % 사용하지 않으면 test에서 % 넣어줘야 함
	/*
	@Query(value = "select * from t_board b "
			+ " where b.title like ?1 and b.content like ?2 order by b.bno desc", nativeQuery = true)
	List<BoardEntity> jpqlTest5(String title, String content); // 파라미터 순서대로 작성
	*/
}