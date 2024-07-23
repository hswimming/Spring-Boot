package com.shinhan.firstzone.vo2;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EntityListeners(value = AuditingEntityListener.class) // 다른 곳에서 사용 가능 하지만 클래스 자체적으로 new 하지 않음
@MappedSuperclass // table 생성이 되지 않음 (다른 엔티티의 부모)
public abstract class BaseEntity { // JPA가 관리하지 않음 (Spring 소유, JPA 소유가 아님)
	// JPA가 관리 (자동으로 날짜가 들어감)
	// @CreationTimestamp
	// @UpdateTimestamp

	@CreatedDate
	@Column(name = "regdate", updatable = false) // false : 수정되지 않음
	LocalDateTime regDate;

	@LastModifiedDate
	@Column(name = "moddate")
	LocalDateTime modDate;
}