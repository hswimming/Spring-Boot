package com.shinhan.firstzone.vo4;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WebBoardDTO { // 테이블이 아닌 기본 클래스
	// DTO : Data Transfer Object
	// VO : Value Object
	
	private Long bno;
	private String title;
	private String mid;
	private String mname;
	private String content;
	private Timestamp regdate;
	private Timestamp updatedate;
	private int replyCount;
}