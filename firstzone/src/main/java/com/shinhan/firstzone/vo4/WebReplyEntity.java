package com.shinhan.firstzone.vo4;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.shinhan.firstzone.vo3.FreeBoardEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "t_webreply")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "board")
@Setter
@Getter
public class WebReplyEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto -> 시퀀스 테이블 생성
	@Id // PK
	private Long rno; // 레파짓토리에서 키를 사용하므로 테이블에도 키가 꼭 있어야 한다.
	
	private String reply; // 댓글 내용
	private String replyer; // 댓글 작성자
	
	@CreationTimestamp // insert 시 자동 생성
	private Timestamp regdate;
	
	@UpdateTimestamp // insert, update 시 자동 생성
	private Timestamp updatedate;
	
	// 하나의 게시글에 댓글이 여러개 있음 (자식)
	@ManyToOne(fetch = FetchType.EAGER) // EAGER : 즉시 로딩 / LAZY : 지연 로딩
	private WebBoardEntity board; // DB 칼럼 => board_bno(참조 키) (many 즉, 자식쪽에 생성됨)
}