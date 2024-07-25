package com.shinhan.firstzone.controller2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.repository3.FreeBoardRepository;
import com.shinhan.firstzone.vo2.MemberEntity;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // @Autowired
@RequestMapping("/coffee")
// @RestController // @Controller + @ResponseBody
@Controller // 요청하면 Page forward or 다른 요청으로 redirect
public class CoffeeController {
	// @RestController -> 응답 문서의 body에 전달
	// @Controller -> Page 반환
	
	final FreeBoardRepository boardRepo;
	
	@GetMapping("/layout1")
	String f6() {
		return "layout/layout1";
	}
	
	@GetMapping("/exlayout1")
	String f5() {
		return "layout/exLayout1";
	}
	
	@GetMapping("/detail")
	void f4(Long bno, Model model) {
		model.addAttribute("board", boardRepo.findById(bno).get());
	}
	
	@GetMapping("/list")
	void f3(@RequestParam("keyword") String keyword, Model model, HttpSession session) {
		System.out.println("keyword : " + keyword);
		
		model.addAttribute("blist", boardRepo.findAll());
		
		MemberEntity member = MemberEntity.builder()
											.mid("zz")
											.mname("수수수수")
											.build();
		
		session.setAttribute("loginUser", member);
	}
	
	@GetMapping("/page2")
	public String f2(Model model) {
		model.addAttribute("menu", "카페라떼");
		
		MemberEntity member = MemberEntity.builder()
											.mid("zz")
											.mname("수수수수")
											.build();
		
		model.addAttribute("member", member);
		
		return "coffee/page1";
	}
	
	@GetMapping("/page1")
	public void f1(Model model) {
		// 리턴 타입 void -> 페이지 forward (default)
		// return "coffee/page1"; // 요청 주소와 페이지 경로가 똑같으면 생략 가능
		
		model.addAttribute("menu", "아메리카노");
		
		MemberEntity member = MemberEntity.builder()
											.mid("abc")
											.mname("수")
											.build();
		
		model.addAttribute("member", member);
	}

	// @RestController의 경우 넘겨줄 데이터를 작성하지 않으면 페이지에 아무것도 보이지 않음
	/*
	@GetMapping("/page1")
	public String f() {
		return "Hello";
	}
	*/
}