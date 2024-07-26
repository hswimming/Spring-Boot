package com.shinhan.firstzone.controller2;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.service2.WebReplyService;
import com.shinhan.firstzone.vo4.WebReplyDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final인 field를 @Autowired
@RequestMapping("/replies")
@RestController
public class WebReplyRestController {
	// @Controller 요청 -> 서비스 수행 -> Data, Page return (Thymleaf template Page 사용중)
	// @RestController 요청 -> 서비스 수행 -> data return (ReactJS에서 사용 예정)

	final WebReplyService replyService;
	
	// 특정 board의 댓글들 조회
	@GetMapping("/list/{bno}")
	List<WebReplyDTO> list(@PathVariable("bno") Long bno) {
		return replyService.getList(bno);
	}
	
	// 등록
	@PostMapping("/register")
	Long insert(@RequestBody WebReplyDTO dto) { // 요청 문서의 body에 들어옴
		return replyService.register(dto); // 입력된 댓글 번호가 return
	}
	
	// 수정
	@PutMapping(value = "/modify", consumes = "application/json;charset=UTF-8",
			produces = "text/plain;charset=UTF-8")
	String update(@RequestBody WebReplyDTO dto) { // 요청 문서의 body에 들어옴
		replyService.modify(dto);
		
		return "success";
	}
	
	// 삭제
	@DeleteMapping("/delete/{rno}")
	String delete(@PathVariable Long rno) {
		replyService.delete(rno);
		
		return "success";
	}
}