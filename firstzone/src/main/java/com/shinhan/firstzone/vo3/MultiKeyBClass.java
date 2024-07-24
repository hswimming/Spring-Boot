package com.shinhan.firstzone.vo3;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "t_child3")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MultiKeyBClass {
	@EmbeddedId // 복합키를 가져옴
	MultiKeyB multib;
	
	String productName;
	int price;
}