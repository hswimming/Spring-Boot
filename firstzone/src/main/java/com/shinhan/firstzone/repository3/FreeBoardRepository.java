package com.shinhan.firstzone.repository3;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.shinhan.firstzone.vo.BoardEntity;
import com.shinhan.firstzone.vo3.FreeBoardEntity;

public interface FreeBoardRepository extends JpaRepository<FreeBoardEntity, Long>,
																	QuerydslPredicateExecutor<FreeBoardEntity> { // 동적 SQL 생성
	// BoardTitle, Reply 건수
	@Query("select b.title, count(reply ) "
				+ " from #{#entityName} b left outer join b.replies reply " // 게시글에 댓글이 없는 경우도 있어서 left outer join
				+ " group by b.title order by b.title ")
	public List<Object[]> getBoardReplyCount();
	
	// 조건 조회 (bno >= 10 and bno <= 20, paging 추가, sort 추가)
	// List<FreeBoardEntity> findByBnoBetween(Long bno1, Long bno2, Pageable pageable);
	Page<FreeBoardEntity> findByBnoBetween(Long bno1, Long bno2, Pageable pageable); // 페이지 정보도 조회
	
	// join, joinfetch 차이
	// join : N + 1 문제
	// joinfetch : N + 1 문제 해결
	
	@Query("select b from #{#entityName} b join b.replies ")
	List<FreeBoardEntity> findAllWithReplyUsingJoin();
	
	@Query("select b from #{#entityName} b join fetch b.replies ")
	List<FreeBoardEntity> findAllWithReplyUsingJoinFetch();
}