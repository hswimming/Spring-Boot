package com.shinhan.firstzone.vo3;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 기본적으로 객체가 여러개 있을 경우 다 같아야 같다고 봄
@EqualsAndHashCode(of = {"rno"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_freereply")
@Entity
@ToString(exclude = {"board"}) // 계속 조회 할 필요 없이 필요할 때만 reply.getBoard(); 하여 조회 가능
@Setter
@Getter
public class FreeReplyEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto -> 시퀀스 테이블 생성
	@Id // PK
	private Long rno;
	
	private String reply; // 댓글 내용
	private String replyer; // 댓글 작성자
	
	@CreationTimestamp // insert 시 자동 생성
	private Timestamp regdate;
	
	@UpdateTimestamp // insert, update 시 자동 생성
	private Timestamp updatedate;
	
	// 하나의 게시글에 댓글이 여러개 있음 (자식)
	@ManyToOne(fetch = FetchType.EAGER)
	private FreeBoardEntity board; // DB 칼럼 => board_bno (many 즉, 자식쪽에 생성됨)
}