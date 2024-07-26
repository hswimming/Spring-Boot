package com.shinhan.firstzone.repository4;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.firstzone.vo4.WebBoardEntity;
import com.shinhan.firstzone.vo4.WebReplyEntity;

//레파짓토리에서 키를 사용하므로 테이블에도 키가 꼭 있어야 한다.
public interface WebReplyRepository extends JpaRepository<WebReplyEntity, Long> {
	// 특정 board의 댓글들 조회
	List<WebReplyEntity> findByBoard(WebBoardEntity board);
}