package com.shinhan.firstzone.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.service.GuestBookService;
import com.shinhan.firstzone.vo2.GuestBookDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/guest")
@RestController
public class GuestBookRestController {
	final GuestBookService gService;
	
	// 동적 SQL 생성
	@GetMapping("/search")
	List<GuestBookDTO> search(String type, String keyword) { // @RequestParam("type") 생략
		return gService.getSearch(type, keyword);
	}
	
	// 전체 조회
	@GetMapping("/list")
	List<GuestBookDTO> list() {
		return gService.readAll();
	}
	
	// 선택 조회
	@GetMapping("/get/{gno}")
	GuestBookDTO list(@PathVariable("gno") Long gno) {
		return gService.readById(gno);
	}
	
	// 입력
	@PostMapping("/insert")
	String insert(@RequestBody GuestBookDTO dto) {
		gService.create(dto);
		
		return "입력 작업";
	}
	
	// 수정
	@PutMapping("/update")
	String update(@RequestBody GuestBookDTO dto) {
		gService.update(dto);
		
		return "수정 작업";
	}
	
	// 삭제
	@DeleteMapping("/delete/{gno}")
	String update(@PathVariable("gno") Long gno) {
		gService.delete(gno);
		
		return "삭제 작업";
	}
}