package com.shinhan.firstzone.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.repository.BoardRepository;
import com.shinhan.firstzone.vo.BoardEntity;
import com.shinhan.firstzone.vo.CarVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController // @Controller + @ResponseBody
@RequestMapping("/sample")
public class SampleRestController {

	@Autowired
	BoardRepository bRepo;
	
	// 삭제
	@DeleteMapping("/delete/{bno}")
	public String delete(@PathVariable("bno") Long bno) {
		bRepo.deleteById(bno);
		
		return "삭제 성공";
	}
	
	// 수정
	@PutMapping("/update")
	public String update(@RequestBody BoardEntity board) { // JSON 형태로 옴
		bRepo.findById(board.getBno()).ifPresent(b -> {
			log.info("before : " + b); // 기존 데이터가 b에 담겨있음, 확인 후 update
			
			board.setRegDate(b.getRegDate()); // 기존 생성일 반영 (넣어주지 않으면 null)
			
			BoardEntity updateBoard = bRepo.save(board);
			log.info("after : " + updateBoard);
		});
		
		return "수정 성공";
	}
	
	// 입력
	@PostMapping("/insert")
	public Long insert(@RequestBody BoardEntity board) { // JSON 형태로 옴
		BoardEntity newBoard = bRepo.save(board);
		
		return newBoard.getBno(); // 입력되면서 생기는 새로운 번호 조회
	}
	
	// 선택 조회
	@GetMapping("/detail/{bno}")
	public BoardEntity detail(@PathVariable("bno") Long bno) {
		return bRepo.findById(bno).orElse(null);
	}
	
	// 전체 조회
	@GetMapping("/list")
	public List<BoardEntity> list() {
		return (List<BoardEntity>) bRepo.findAll();
	}
	
	@GetMapping("/test1")
	public String f1() {
		return "Hello! 반갑습니다~!";
	}
	
	@GetMapping("/test2")
	public CarVO f2() {
		CarVO car = CarVO.builder()
					.model("ABC모델")
					.price(2000)
					.build();
		
		return car; // jackson이 jason으로 자동 변환 (값이 넘어감)
	}
	
	@GetMapping("/test3")
	public List<CarVO> f3() {
		List<CarVO> carList = new ArrayList<>();
		
		IntStream.rangeClosed(1, 5).forEach(i -> {
			CarVO car = CarVO.builder()
					.model("ABC모델" + i)
					.price(2000 * i)
					.build();
			
			carList.add(car);
		});
		
		return carList; // jackson이 jason 배열로 자동 변환 (값이 넘어감)
	}
}