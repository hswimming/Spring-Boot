package com.shinhan.firstzone.controller2;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.security.jwt.ResponseDTO;
//import com.shinhan.firstzone.security.jwt.ResponseDTO;
import com.shinhan.firstzone.service2.WebBoardService;
import com.shinhan.firstzone.vo4.WebBoardDTO;

@CrossOrigin // 다른 포트 번호 허용, 직접 설정도 가능
@RestController
@RequestMapping("/api/board")
public class WebBoardRestController {

	@Autowired
	WebBoardService boardService;

	// Jackson이 json으로 변경하여 return
	@GetMapping("/list")
	public List<WebBoardDTO> list() {
		return boardService.getList();
	}

	@GetMapping("/detail/{bno}")
	WebBoardDTO detail(@PathVariable("bno") Long bno) {
		return boardService.selectById(bno);
	}

	@PostMapping("/register")
	ResponseDTO register(@RequestBody WebBoardDTO dto, Principal principal) { // 응답 DTO 따로 생성
		// Jackson이 Json으로 변경할 수 있도록 객체로만
		dto.setMid(principal.getName());
		Long bno = boardService.register(dto);
		
		return ResponseDTO.builder().message("insertSuccess").status(HttpStatus.OK).build();
	}

	// @PostMapping("/modify")
	@PutMapping("/modify")
	public ResponseDTO modify(@RequestBody WebBoardDTO dto) {
		boardService.modify(dto);
		return ResponseDTO.builder().message("modifySuccess").status(HttpStatus.OK).build();
	}

	// @PostMapping("/delete")
	@DeleteMapping("/delete/{bno}")
	public ResponseDTO delete(@PathVariable("bno") Long bno) {
		boardService.delete(bno);
		return ResponseDTO.builder().message("deleteSuccess").status(HttpStatus.OK).build();
	}
}