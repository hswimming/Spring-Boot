package com.shinhan.firstzone;

import static org.mockito.ArgumentMatchers.booleanThat;

import java.util.ArrayList;
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

import com.querydsl.core.BooleanBuilder;
import com.shinhan.firstzone.repository3.FreeBoardRepository;
import com.shinhan.firstzone.repository3.FreeReplyRepository;
import com.shinhan.firstzone.vo3.FreeBoardEntity;
import com.shinhan.firstzone.vo3.FreeReplyEntity;
import com.shinhan.firstzone.vo3.QFreeBoardEntity;

import jakarta.persistence.FetchType;
import jakarta.transaction.Transactional;

@SpringBootTest
public class BidirectionTest {
	@Autowired
	FreeBoardRepository boardRepo;
	
	@Autowired
	FreeReplyRepository replyRepo;
	
	@Test
	void queryDslTest() {
		// 동적 SQL 사용
		String type = "tcw";
		String keyword = "맑음";
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QFreeBoardEntity board = QFreeBoardEntity.freeBoardEntity;
		builder.and(board.bno.gt(0L));
		
		BooleanBuilder builder2 = new BooleanBuilder();
		
		if(type.contains("t")) {
			builder2.or(board.title.like("%" + keyword + "%"));
		}
		
		if(type.contains("c")) {
			builder2.or(board.content.like("%" + keyword + "%"));
		}
		
		if(type.contains("w")) {
			builder2.or(board.writer.like("%" + keyword + "%"));
		}
		
		builder.and(builder2);
		System.out.println(builder);
		
		Pageable page = PageRequest.of(0, 10);
		
		Page<FreeBoardEntity> result = boardRepo.findAll(builder, page);
		List<FreeBoardEntity> list = result.getContent();
		
		list.forEach(b -> { System.out.println(b); });
		
		System.out.println("getSize : " + result.getSize());
		System.out.println("getTotalPages : " + result.getTotalPages());
		System.out.println("getTotalElements : " + result.getTotalElements());
		System.out.println("nextPageable : " + result.nextPageable());
	}
	
	// @Test
	void join2() {
		// join, joinfetch 차이
		// N : 1 test (Reply select) => 현재 Reply가 참조하는 Board가 5개라면 Reply select문 1개, Board select문 5개가 생성
		replyRepo.findAllWithReplyUsingJoin().forEach(b -> System.out.println(b)); // N + 1 문제 (테이블 join 했는데도 계속 조회 => 성능 하락)
		// replyRepo.findAllWithReplyUsingJoinFetch().forEach(b -> System.out.println(b)); // N + 1 문제 해결
	}
	
	// @Test
	void join1() {
		// join, joinfetch 차이
		boardRepo.findAllWithReplyUsingJoin().forEach(b -> System.out.println(b));
		// boardRepo.findAllWithReplyUsingJoinFetch().forEach(b -> System.out.println(b));
	}
	
	// @Transactional
	// @Test
	void selectBoard2() {
		// 조건 조회 (bno >= 1 and bno <= 10, paging 추가, sort 추가)
		// 2번째 페이지 5건
		Pageable pageable = PageRequest.of(1, 5, Sort.Direction.DESC, "bno"); 
		
		// where bno between 1 and 10 order by bno desc
		// 페이지 정보도 조회하는 경우
		Page<FreeBoardEntity> result = boardRepo.findByBnoBetween(1L, 10L, pageable);
		System.out.println("getNumber : " + result.getNumber());
		System.out.println("getSize : " + result.getSize());
		System.out.println("getTotalElements : " + result.getTotalElements());
		System.out.println("getTotalPages : " + result.getTotalPages());
		System.out.println("getSort : " + result.getSort());
		System.out.println("getPageable : " + result.getPageable());
		System.out.println("getPageable.next : " + result.getPageable().next()); // 다음 페이지 정보
		
		result.getContent().forEach(board -> {
			System.out.println(board);
			System.out.println(board.getReplies().size() + "건");
		});
		
		/*
		boardRepo.findByBnoBetween(1L, 10L, pageable).forEach(board -> {
			System.out.println(board);
			System.out.println(board.getReplies().size() + "건");
		});
		*/
	}
	
	// @Test
	void selectBoardReply() {
		// BoardTitle, Reply 건수
		boardRepo.getBoardReplyCount().forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
	
	// @Transactional
	// @Test
	void selectBoard() {
		boardRepo.findAll().forEach(board -> {
			System.out.println(board);
			System.out.println("댓글 건수 : " + board.getReplies().size());
		});
	}
	
	// @Transactional // FetchType.LAZY인데, 특정 업무에서 조회하고 싶을 경우 어노테이션 추가
	// @Test
	void selectByReply() {
		// 조회 (Board 번호를 알고 댓글들의 정보 조회)
		FreeBoardEntity board = FreeBoardEntity.builder().bno(5L).build();
		
		replyRepo.findByBoard(board).forEach(reply -> {
			System.out.println(reply);
			System.out.println(reply.getBoard());
		});
	}
	
	// @Test
	void updateReplyBno2() {
		// 댓글의 게시글 번호가 null인 경우 수정 (방법2)
		FreeBoardEntity board = FreeBoardEntity.builder().bno(5L).build(); // 게시글 번호 가져오기
		
		List<FreeReplyEntity> replies = replyRepo.findAllById(Arrays.asList(20L, 21L, 22L, 23L, 24L)); // 20~24번 rno의 bno를 5로 변경
		
		replies.forEach(reply -> {
			reply.setBoard(board);
			replyRepo.save(reply);
		});
	}
	
	// @Test
	void updateReplyBno() {
		// 댓글의 게시글 번호가 null인 경우 수정 (방법1)
		FreeBoardEntity board = FreeBoardEntity.builder().bno(5L).build(); // 게시글 번호 가져오기
		
		Long[] arr = {20L, 21L, 22L, 23L, 24L};
		List<Long> idList = Arrays.asList(arr);
		// idList.add(20L); idList.add(21L); idList.add(22L); idList.add(23L); idList.add(24L);
		
		replyRepo.findAllById(idList).forEach(reply -> {
			reply.setBoard(board);
			replyRepo.save(reply);
		});
	}
	
	// @Test
	void updateReply() {
		// 댓글 수정
		FreeBoardEntity board = FreeBoardEntity.builder().bno(4L).build(); // 게시글 번호 가져오기
		
		replyRepo.findById(1L).ifPresent(reply -> {
			reply.setBoard(board);
			
			replyRepo.save(reply);
		});
	}
	
	// @Test
	void insertReply() {
		// 댓글 insert
		// FreeBoardEntity board = boardRepo.findById(4L).get();
		FreeBoardEntity board = FreeBoardEntity.builder().bno(4L).build(); // 게시글 번호 가져오기
		
		FreeReplyEntity reply = FreeReplyEntity.builder()
										 .reply("점심 메뉴")
										 .replyer("user1")
										 .board(board)
										 .build();
		
		replyRepo.save(reply);
	}
	
	// @Test
	void insertBoard2() {
		// 특정 board 댓글 insert
		boardRepo.findById(3L).ifPresent(board -> {
			List<FreeReplyEntity> replyList = board.getReplies();
			
			// 댓글 생성
			IntStream.rangeClosed(1, 5).forEach(i -> {
				FreeReplyEntity reply = FreeReplyEntity.builder()
												 .reply("맛집..." + i)
												 .replyer("user" + i % 2)
												 .board(board) // 자식이 부모를 참조
												 .build();
				
				replyList.add(reply);
			});
			
			boardRepo.save(board);
		});
	}
	
	// @Test
	void insertBoard() {
		// 30건 insert
		IntStream.rangeClosed(1, 30).forEach(i -> {
			FreeBoardEntity entity = FreeBoardEntity.builder()
												.title("수요일" + i)
												.content("오늘의 날씨는 " + (i % 2 == 0 ? "맑음" : "흐림"))
												.writer("user" + (i % 5))
												.build();
			
			boardRepo.save(entity);
		});
	}
}