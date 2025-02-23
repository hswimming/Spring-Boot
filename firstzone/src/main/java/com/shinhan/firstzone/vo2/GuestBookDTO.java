package com.shinhan.firstzone.vo2;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestBookDTO {
	// table join 시 join column 추가	
	Long gno;
	String title;
	String content;
	String writer;
	LocalDateTime regDate, modDate;
}