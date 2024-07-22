package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.shinhan.firstzone.repository.BoardRepository;
import com.shinhan.firstzone.vo.BoardEntity;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest // Junit(단위 test)으로 test
public class BoardCRUDTest {
	@Autowired
	BoardRepository bRepo;
	
	@Test
	void f9() {
//		bRepo.jpqlTest1("요일",  "짝수").forEach(b -> log.info(b));
//		bRepo.jpqlTest2("요일",  "짝수").forEach(b -> log.info(b));
//		bRepo.jpqlTest3("요일",  "짝수").forEach(b -> log.info(b));
//		bRepo.jpqlTest4("요일",  "짝수").forEach(arr -> log.info(Arrays.toString(arr)));
		
		// 레파짓토리에서 % 사용하지 않으면 test에서 % 넣어줘야 함
//		bRepo.jpqlTest5("%요일%",  "%맑음%").forEach(b -> log.info(b));
		bRepo.jpqlTest5("요일",  "맑음").forEach(b -> log.info(b));
	}
	
	// @Test
	void f8() {
		// Pageable pageable = PageRequest.of(0, 5); // PageNumber(0부터 시작), size
		// Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").descending()); // sort 작성 가능
		// Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, new String[] {"bno"}); // 정렬 기준이 여러개일 경우
		// Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "bno"); // 정렬 기준이 하나일 경우
		
		// order by writer desc, bno desc limit ?,?
		Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, new String[] {"writer", "bno"});
		
		Page<BoardEntity> result = bRepo.findAll(pageable); // Page<BoardEntity> -> 페이지에 대한 정보가 담김
		List<BoardEntity> blist = result.getContent(); // getContent() -> 데이터가 담김
		
		// 페이지 정보 출력
		log.info("getNumber : " + result.getNumber());
		log.info("getSize : " + result.getSize());
		log.info("getTotalElements : " + result.getTotalElements());
		log.info("getTotalPages : " + result.getTotalPages());
		
		blist.forEach(board -> log.info(board));
	}
	
	// @Test
	void f7() {
		// paging 추가
		// where bno > ? order by bno desc
		Pageable pageable = PageRequest.of(0, 5); // PageNumber(0부터 시작), size
		// 0 : 20 ~ 16
		// 1 : 15 ~ 11
		// 2 : 10 ~ 6
		// 3 : 6 ~ 2
		// DB 데이터 별 결과 다름
		bRepo.findByBnoGreaterThanOrderByBnoDesc(10L, pageable).forEach(b -> {
			log.info(b);
		});
	}
	
	//@Test
	void f6() {
		// like 사용 시 직접 값을 넣어줘야 함
		// where content like '%aa%' and (bno >= ? and bno <= ?) order by bno desc
		bRepo.findByContentLikeAndBnoBetweenOrderByBnoDesc("%짝%", 1L, 15L).forEach(b -> {
			log.info(b);
		});
	}
	
	// @Test
	void f5() {
		// where bno >= ? and bno <= ?
		bRepo.findByBnoBetween(10L, 15L).forEach(b -> {
			log.info(b);
		});
	}
	
	// @Test
	void f4() {
		// where title '%A%'
		bRepo.findByTitleContaining("1").forEach(b -> {
			log.info(b);
		});
	}
	
	// @Test
	void f3() {
		// where title %'A'
		bRepo.findByTitleEndingWith("1").forEach(b -> {
			log.info(b);
		});
	}
	
	// @Test
	void f2() {
		// where title 'A%'
		bRepo.findByTitleStartingWith("월").forEach(b -> {
			log.info(b);
		});
	}
	
	// @Test
	void f1() {
		// where writer = 'A'
		bRepo.findByWriter("user1").forEach(b -> {
			log.info(b);
		});
	}
	
	// @Test
	void boardCount() {
		// 건수 확인
		Long cnt = bRepo.count();
		log.info(cnt + "건");
	}
	
	// @Test
	void delete() {
		// 선택 삭제
		bRepo.deleteById(2L);
		bRepo.findById(2L).ifPresentOrElse(b -> {
			log.info(b);
			
		}, () -> {
			log.info("Not Found");
		});
	}
	
	// @Test
	void update() {
		// 수정
		bRepo.findById(1L).ifPresent(board -> { // ifPresent : 있을 경우
			log.info("before : " + board);
			
			board.setTitle("springboot");
			board.setContent("========== 수정 ==========");
			board.setWriter("manager");
			
			BoardEntity updateBoard = bRepo.save(board);
			log.info("after : " + updateBoard);
		});
	}
	
	// @Test
	void detail() {
		// 선택 조회
		bRepo.findById(50L).ifPresentOrElse(board -> {
			log.info(board);
			
		}, () -> {
			log.info("해당 데이터가 존재하지 않습니다.");
		});
	}
	
	// @Test
	void select() {
		// 전체 조회
		bRepo.findAll().forEach(board -> {
			log.info(board); // 자동 toString
		});
	}
	
	// @Test
	void insert() {
		// 생성
		IntStream.rangeClosed(11, 20).forEach(i -> {
			BoardEntity entity = BoardEntity.builder()
										.title("토요일" + i)
										.content("맑음" + (i % 2 == 0 ? "짝수" : "홀수"))
										.writer("user" + (i % 5))
										.build();
										// regDate, updateDate -> 자동 insert
			
			bRepo.save(entity);
		});
	}
	
}