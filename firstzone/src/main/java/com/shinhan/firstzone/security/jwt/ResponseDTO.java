package com.shinhan.firstzone.security.jwt;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseDTO {
	// 무조건 JSON으로 받음
	String message;
	HttpStatus status;
}