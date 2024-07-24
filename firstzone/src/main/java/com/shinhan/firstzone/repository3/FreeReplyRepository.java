package com.shinhan.firstzone.repository3;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shinhan.firstzone.vo3.FreeBoardEntity;
import com.shinhan.firstzone.vo3.FreeReplyEntity;

public interface FreeReplyRepository extends JpaRepository<FreeReplyEntity, Long> {
	// 조회 (Board 번호를 알고 댓글들의 정보 조회)
	List<FreeReplyEntity> findByBoard(FreeBoardEntity board);
	
	@Query("select b from #{#entityName} b join b.board ")
	List<FreeReplyEntity> findAllWithReplyUsingJoin();
	
	@Query("select b from #{#entityName} b join fetch b.board ")
	List<FreeReplyEntity> findAllWithReplyUsingJoinFetch();
}