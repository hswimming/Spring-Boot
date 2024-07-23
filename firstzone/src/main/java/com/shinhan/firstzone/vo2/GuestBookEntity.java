package com.shinhan.firstzone.vo2;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "t_guestbook")
@Entity // JPA가 관리하는 객체 (DB의 객체와 자바의 객체를 Mapping / DB의 객체 = table)
@Builder // 사용하려면 @AllArgsConstructor 있어야 함
@AllArgsConstructor
@NoArgsConstructor // @AllArgsConstructor 추가하면 사라짐, 다시 추가
@Setter
@Getter
// @Data 사용 시 toString 재정의 불가능
// @Data // getter + setter + toString + equalsandhashcode + Noargmentconstructor
public class GuestBookEntity extends BaseEntity {
	// @GeneratedValue(strategy = GenerationType.AUTO) // 자동 증가
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id // PK
	Long gno;
	
	@Column(length = 100, nullable = false) // not null
	String title;
	
	@Column(length = 2000)
	String content;
	
	@Column(length = 50)
	String writer;

	@Override
	public String toString() {
		return "GuestBookEntity [gno=" + gno + ", title=" + title + ", content=" + content + ", writer=" + writer
				+ ", 등록일(regDate)=" + regDate + ", 수정일(modDate)=" + modDate + "]";
	}
}