package com.shinhan.firstzone.vo3;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 기본적으로 객체가 여러개 있을 경우 다 같아야 같다고 봄
@EqualsAndHashCode(of = {"bno"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_freeboard")
@Entity
@ToString(exclude = {"replies"}) // FreeBoardEntity, FreeReplyEntity 모두 @ToString 있을 경우 StackOverflowError 발생하므로 제외한다.
@Setter
@Getter
public class FreeBoardEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto -> 시퀀스 테이블 생성
	@Id // PK
	private Long bno;
	
	private String title;
	private String writer;
	private String content;
	
	@CreationTimestamp // insert 시 자동 생성
	private Timestamp regdate;
	
	@UpdateTimestamp // insert, update 시 자동 생성
	private Timestamp updatedate;
	
	// 하나의 게시글에 댓글이 여러개 있음 (부모)
	// N + 1 해결 방법 중 하나, Join 사용 시 대상 entity에 갯수만큼 상대 entity의 select문이 된다. in을 사용하여 BatchSize만큼 한꺼번에 select 가능
	@BatchSize(size = 100) // @OneToMany에서만 사용 가능
	@OneToMany(mappedBy = "board", // 부모인데 자식에게 매여있음
						cascade = CascadeType.ALL, // 모든 전의
						fetch = FetchType.EAGER) // EAGER : 즉시 로딩 / LAZY : 지연 로딩
	List<FreeReplyEntity> replies;
}