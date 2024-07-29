package com.shinhan.firstzone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shinhan.firstzone.vo2.MemberEntity;

@RequestMapping("/auth")
@Controller
public class SecurityAuthController {
	
	@Autowired
	MemberService mService;
	
	@GetMapping("/login")
	public void f1() {
		
	}
	
	// Spring이 post 수행
	// UserDetailsService를 구현한 class 수행
	/*
	@PostMapping("/login")
	public void f1() {
		
	}
	*/
	
	@GetMapping("/loginSuccess")
	public void f2() {
		
	}
	
	@GetMapping("/logout")
	public void f3() {
		
	}
	
	@GetMapping("/accessDenined")
	public void f4() {
		
	}
	
	@GetMapping("/signup")
	public String f5() {
		return "auth/joinForm";
	}
	
	// 회원가입
	@PostMapping("/joinProc")
	public String f6(MemberEntity member) {
		MemberEntity newMember = mService.joinUser(member);
		
		// return newMember.getMid() + " -- OK";
		return "redirect:login";
	}
}