package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shinhan.firstzone.vo2.PDSBoardEntity;

public interface PDSBoardRepository extends JpaRepository<PDSBoardEntity, Long> {
	// 1. 기본적인 CRUD 제공 : findAll(), findById()
	
	// 2. 규칙에 맞는 함수 정의
	List<PDSBoardEntity> findByWriter(String writer);
	
	// 3. @Query
	@Query("select b.pname, b.writer, f.pdsfilename "
				+ " from #{#entityName} b left outer join b.files f " // 연관 관계가 있으면 on 생략 가능
				+ " where b.pid = ?1 order by b.pid ")
	public List<Object[]> getFilesInfo2(Long pid); // select 절에 칼럼이 3개, Object 배열로 받음
	
	// JPA 문법에 맞는 쿼리 쓰는것을 권장
	@Query("select b.pname, count(f ) "
				+ " from #{#entityName} b left outer join b.files f where b.pid > 0 "
				+ " group by b.pname order by b.pname ")
	public List<Object[]> getFilesCount2();

	// nativeQuery -> 테이블명 직접 입력
	@Query(value = "select b.pname, count(f.pdsno) from t_pdsboard b "
				+ " left outer join t_pdsfile f on (b.pid = f.pdsno) group by b.pname order by 1 ", nativeQuery = true)
	public List<Object[]> getFilesCount3();
	
	// @Query는 기본적으로 select만 지원 / DML(insert, update, delete)를 하려면 @Modifying 추가
	@Modifying
	@Query("UPDATE PDSFileEntity f set f.pdsfilename = ?2 WHERE f.fno = ?1 ") 
	int updatePDSFile(Long fno, String newFileName); 
}