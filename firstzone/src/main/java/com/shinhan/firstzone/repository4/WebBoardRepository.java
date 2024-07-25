package com.shinhan.firstzone.repository4;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.shinhan.firstzone.vo4.WebBoardEntity;

// 레파짓토리에서 키를 사용하므로 테이블에도 키가 꼭 있어야 한다.
public interface WebBoardRepository extends JpaRepository<WebBoardEntity, Long>,
																	QuerydslPredicateExecutor<WebBoardEntity> {

}