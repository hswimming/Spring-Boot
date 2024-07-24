package com.shinhan.firstzone.vo3;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MultiKeyA implements Serializable {
	// Serializable => 줄 세워서 늘어뜨림, 나중에 다시 복구하려면 Serializable 번호가 필요
	private static final long serialVersionUID = 1L;
	
	Integer id1;
	Integer id2;
}