package com.shinhan.firstzone.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

// 인증 인가 연습
@Log4j2
@RequestMapping("/security")
@Controller
public class SecurityTestController {
	@GetMapping("/all")
	public void f1() {
		log.info("모든 사용자가 접근 가능한 요청이다.");
	}
	
	@GetMapping("/user")
	public void f2() {
		log.info("user 권한을 가진 member가 접근 가능한 요청이다.");
	}
	
	@GetMapping("/admin")
	public void f3() {
		log.info("admin 권한을 가진 member가 접근 가능한 요청이다.");
	}
	
	@GetMapping("/manager")
	public void f4() {
		log.info("manager 권한을 가진 member가 접근 가능한 요청이다.");
	}
}