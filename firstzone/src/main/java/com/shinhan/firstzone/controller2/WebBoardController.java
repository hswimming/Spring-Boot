package com.shinhan.firstzone.controller2;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shinhan.firstzone.paging.PageRequestDTO;
import com.shinhan.firstzone.security.MemberService;
import com.shinhan.firstzone.service2.WebBoardService;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo4.WebBoardDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/webboard")
@Controller
public class WebBoardController {
	// @Controller 요청 -> 서비스 수행 -> Data, Page return (Thymleaf template Page 사용중)
	// @RestController 요청 -> 서비스 수행 -> data return (ReactJS에서 사용 예정)
	
	final WebBoardService boardService;
	final MemberService memService;
	
	// 등록 페이지
	@GetMapping("/register")
	void registerForm() {
		// forward : templates/webboard/register.html
	}
	
	// 등록
	@PostMapping("/register")
	String register(WebBoardDTO dto, RedirectAttributes attr, Principal principal, Authentication authentication, HttpSession session) {
		System.out.println("방법 1. principal : " + principal);
		
		Object principal2 = authentication.getPrincipal();
		System.out.println("방법 2. authentication : " + principal2);
		
		Object principal3 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("방법 3. authentication : " + principal3);
		
		UserDetails userDetails = (UserDetails) principal3;
		System.out.println("userDetails : " + userDetails);
		
		// 로그인 한 memeber id가 board의 mid로 입력된다.
		String mid = principal.getName();
		UserDetails userDetails2 = memService.loadUserByUsername(mid);
		System.out.println("방법 4. loadUserByUsername : " + userDetails2);
		
		MemberEntity loginUser = (MemberEntity) session.getAttribute("user");
		System.out.println("방법 5. session에서 읽기 : " + loginUser);
		
		dto.setMid(mid); // 로그인 유저의 아이디 등록
		
		log.info("입력 before : " + dto);
		Long bno = boardService.register(dto);
		
		log.info(bno + "번 게시글이 등록되었습니다.");
		attr.addFlashAttribute("msg", "등록 완료");
		
		return "redirect:list";
	}
	
	// 전체 조회
	@GetMapping("/list")
	String getList(Model model, PageRequestDTO pageRequestDTO) {
		// List<WebBoardDTO> blist = boardService.getList();
		// model.addAttribute("blist", blist);
		
		// 페이징 처리
		// 조회 시 page, size가 0일 경우 넘어오지 않을 수 있음
		if (pageRequestDTO.getSize() == 0) {
			pageRequestDTO.setPage(1);
			pageRequestDTO.setSize(10);
			
			// 원하는 조건으로 조회
			// pageRequestDTO.setType("tcw");
			// pageRequestDTO.setKeyword("");
		}
		
		model.addAttribute("result", boardService.getList(pageRequestDTO));
		model.addAttribute("pageRequestDTO", pageRequestDTO);

		return "webboard/boardlist";
	}
	
	// 상세 조회
	@GetMapping("/detail")
	void getDetail(Long bno, Model model) {
		model.addAttribute("board", boardService.selectById(bno));
	}
	
	// 수정
	@PostMapping("/modify")
	String modify(WebBoardDTO dto, RedirectAttributes attr) {
		boardService.modify(dto);
		attr.addFlashAttribute("msg", "수정 완료");
		
		return "redirect:list";
	}
	
	// 삭제
	@PostMapping("/delete")
	String delete(Long bno, RedirectAttributes attr) {
		boardService.delete(bno);
		attr.addFlashAttribute("msg", "삭제 완료");
		
		return "redirect:list";
	}
}