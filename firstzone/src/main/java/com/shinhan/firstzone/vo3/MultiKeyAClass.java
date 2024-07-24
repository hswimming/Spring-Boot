package com.shinhan.firstzone.vo3;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "t_child1")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@IdClass(MultiKeyA.class) // key로 사용 된 ID class 작성
public class MultiKeyAClass {
	@Id
	Integer id1;
	
	@Id
	Integer id2;
	
	String productName;
	int price;
}