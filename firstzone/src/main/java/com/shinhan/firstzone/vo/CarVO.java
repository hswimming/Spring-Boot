package com.shinhan.firstzone.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data // @Getter + @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarVO {
	String model;
	int price;
}