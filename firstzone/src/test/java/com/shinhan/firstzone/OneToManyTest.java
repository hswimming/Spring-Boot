package com.shinhan.firstzone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository.PDSBoardRepository;
import com.shinhan.firstzone.repository.PDSFileRepository;
import com.shinhan.firstzone.vo2.PDSBoardEntity;
import com.shinhan.firstzone.vo2.PDSFileEntity;

@SpringBootTest
public class OneToManyTest {
	@Autowired
	PDSFileRepository fileRepo;
	
	@Autowired
	PDSBoardRepository boardRepo;
	
	@Test
	void selectBoard() {
		Long pid = 1L;
		
		PDSBoardEntity board = boardRepo.findById(pid).orElse(null);
		
		if (board == null) return;
		
		System.out.println("board : " + board); // 레파짓토리에서 FetchType.LAZY인 경우 오류나기 때문에 exclude 작성해야 함
		System.out.println("Pname : " + board.getPname());
		System.out.println("Writer" + board.getWriter());
		// System.out.println("리스트 개수 : " + board.getFiles().size()); // exclude = {"files"} 생략 됐기 때문에 출력하면 오류
		// System.out.println("리스트 전체 : " + board.getFiles());
	}
	
	// @Test
	void selectFile() {
		// 조회
		Long fno = 10L;
		
		PDSFileEntity file = fileRepo.findById(fno).orElse(null);
		System.out.println(file);
	}
	
	// @Test
	void insert2() {
		// 입력
		// board 10건
		IntStream.rangeClosed(1, 10).forEach(j -> {
			// 첨부파일 먼저 생성
			List<PDSFileEntity> files = new ArrayList<>();
			
			// 첨부파일 5건 * 10
			IntStream.rangeClosed(1, 5).forEach(i -> {
				PDSFileEntity file = PDSFileEntity.builder()
											.pdsfilename("ptyhon-" + j + "-" + i + ".ppt")
											.build();
			
				files.add(file);
			});
		
			PDSBoardEntity board = PDSBoardEntity.builder()
											  .pname("파이썬 교육")
											  .writer("보리")
											  .files(files)
											  .build();
		
			boardRepo.save(board);
		});
	}
	
	// @Test
	void insert() {
		// 입력
		// 첨부파일 먼저 생성
		List<PDSFileEntity> files = new ArrayList<>();
		
		IntStream.rangeClosed(1, 5).forEach(i -> {
			PDSFileEntity file = PDSFileEntity.builder()
										.pdsfilename("스프링book-" + i + ".ppt")
										.build();
			
			files.add(file);
		});
		
		PDSBoardEntity board = PDSBoardEntity.builder()
										  .pname("springboot-JAP")
										  .writer("수영")
										  .files(files)
										  .build();
		
		boardRepo.save(board);
		
		// 첨부파일 insert 후 다시 update -> 처음에는 fno를 모르기 때문에 insert 후 넣어줌
	}
}