package com.shinhan.firstzone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinhan.firstzone.security.jwt.AuthServiceImpl;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo2.MemberRole;

@CrossOrigin
@RequestMapping("/auth")
@Controller
public class SecurityAuthController {
	
	@Autowired
	MemberService mService;
	
	@Autowired
	AuthServiceImpl authService;
	
//	@GetMapping("/login")
//	public void f1() {
//		
//	}
	
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
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<TokenDTO> getMemberProfile(@RequestBody MemberEntity request) {
		System.out.println(request);
		
		String token = authService.login(request);
		TokenDTO dto = TokenDTO.builder().login(request.getMid()).token(token).build();
		
		return new ResponseEntity(dto, HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	@ResponseBody
	public ResponseEntity<MemberEntity> f7(@RequestBody MemberEntity member) {
		member.setMrole(MemberRole.USER);
		MemberEntity newMember = mService.joinUser(member);
		
		return new ResponseEntity(newMember, HttpStatus.OK);
	}
}