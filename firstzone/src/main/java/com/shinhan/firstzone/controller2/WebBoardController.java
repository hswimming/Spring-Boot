package com.shinhan.firstzone.controller2;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shinhan.firstzone.service2.WebBoardService;
import com.shinhan.firstzone.vo4.WebBoardDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/webboard")
@Controller
public class WebBoardController {
	final WebBoardService boardService;
	
	// 등록 페이지
	@GetMapping("/register")
	void registerForm() {
		
	}
	
	// 등록
	@PostMapping("/register")
	String register(WebBoardDTO dto) {
		log.info("입력 before : " + dto);
		
		Long bno = boardService.register(dto);
		log.info(bno + "번 게시글이 등록되었습니다.");
		
		return "redirect:list";
	}
	
	// 전체 조회
	@GetMapping("/list")
	String getList(Model model) {
		List<WebBoardDTO> blist = boardService.getList();
		model.addAttribute("blist", blist);

		return "webboard/boardlist";
	}
	
	// 상세 조회
	@GetMapping("/detail")
	void getDetail(Long bno, Model model) {
		model.addAttribute("board", boardService.selectById(bno));
	}
	
	// 수정
	@PostMapping("/modify")
	String modify(WebBoardDTO dto) {
		boardService.modify(dto);
		
		return "redirect:list";
	}
	
	// 삭제
	@PostMapping("/delete")
	String delete(Long bno) {
		boardService.delete(bno);
		
		return "redirect:list";
	}
}