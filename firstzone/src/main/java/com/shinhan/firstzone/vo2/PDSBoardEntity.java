package com.shinhan.firstzone.vo2;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//하나의 Board에 첨부파일이 여러개 있다.
@Table(name = "t_pdsboard")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString // (exclude = {"files"}) // FetchType.LAZY 사용 할 경우 Board 전체 조회 시 오류가 날 수 있으므로 작성
public class PDSBoardEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Long pid;
	String pname;
	String writer;
	
	// PDSFileEntity 테이블에 pdsno 생성 / PDSBoard키, PDSFileEntity키를 칼럼으로 갖는 중간 테이블 만들어짐
	// JoinColumn 생략 시 중간 테이블이 생성됨
	@JoinColumn(name = "pdsno")
	@OneToMany(cascade = CascadeType.ALL, // cascade : 전의 / 모든 변경에 대해 PDSFileEntity 테이블에도 영향을 미친다. (나와 연관 관계가 있는)
									   fetch = FetchType.EAGER) // FetchType.EAGER -> 조회 시 PDSFileEntity 함께 조회
	List<PDSFileEntity> files;
}