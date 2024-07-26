package com.shinhan.firstzone.vo4;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.shinhan.firstzone.vo2.MemberEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "t_webboard")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "replies")
@Setter
@Getter
public class WebBoardEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto -> 시퀀스 테이블 생성
	@Id // PK
	private Long bno; // 레파짓토리에서 키를 사용하므로 테이블에도 키가 꼭 있어야 한다.
	
	private String title;
	
	@ManyToOne // 하나의 멤버가 여러개의 board 작성 가능 (연관 관계 작성 필수)
	private MemberEntity writer; // writer_mid, 로그인 유저만 작성 가능
	private String content;
	
	@CreationTimestamp // insert 시 자동 생성
	private Timestamp regdate;
	
	@UpdateTimestamp // insert, update 시 자동 생성
	private Timestamp updatedate;
	
	@BatchSize(size = 100)
	@OneToMany(mappedBy = "board", // 부모인데 자식에게 매여있음
						cascade = CascadeType.ALL, // 모든 전의
						fetch = FetchType.EAGER) // EAGER : 즉시 로딩 / LAZY : 지연 로딩
	List<WebReplyEntity> replies;
}