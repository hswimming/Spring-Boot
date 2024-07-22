package com.shinhan.firstzone.vo;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // JAP가 관리하는 대상 -> DB의 table과 Mapping 할 때 사용
@Table(name = "t_board") // 테이블 이름 변경 (BoardEntity -> t_board)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id // 필수 (식별자 / PK)
	private Long bno;
	
	@Column(length = 50, nullable = false) // not null
	private String title;
	
	@Column(length = 2000)
	private String content;
	
	private String writer;
	
	@CreationTimestamp // insert 시 입력
	@Column(name = "regdate") // DB 컬럼명이 reg_date로 생성됨, 언더바 없이 사용하려고 컬럼명 변경
	private Timestamp regDate;
	
	@UpdateTimestamp // insert 시 입력, update 시 변경
	@Column(name = "updatedate") // DB 컬럼명이 update_date로 생성됨, 언더바 없이 사용하려고 컬럼명 변경
	private Timestamp updateDate;
}