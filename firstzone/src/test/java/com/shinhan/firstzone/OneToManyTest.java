package com.shinhan.firstzone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.shinhan.firstzone.repository.PDSBoardRepository;
import com.shinhan.firstzone.repository.PDSFileRepository;
import com.shinhan.firstzone.vo2.PDSBoardEntity;
import com.shinhan.firstzone.vo2.PDSFileEntity;

import jakarta.persistence.CascadeType;
import jakarta.transaction.Transactional;

@Commit
@SpringBootTest
public class OneToManyTest {
	@Autowired
	PDSFileRepository fileRepo;
	
	@Autowired
	PDSBoardRepository boardRepo;

	// PDSBoardEntity에서 cascade = CascadeType.ALL 설정이므로 부모가 삭제되면 자식도 영향을 받아 삭제된다.
	@Test
	void deleteBoard() {
		// 삭제
		boardRepo.deleteById(10L);
	}
	
	// @Test
	void deleteFile() {
		// 삭제
		fileRepo.deleteById(59L);
	}
	
	// @Transactional // 실행 후 test 환경은 rollback 처리된다. @Commit 어노테이션을 추가해야 DB에 반영된다.
	// @Test
	void updateFile() {
		// boardRepo에서 PDSFile 수정하기, 직접 SQL 작성
		boardRepo.updatePDSFile(60L, "파일 이름 수정");
	}
	
	// @Test
	void update() {
		// PDSBoard의 이름 변경 : 본래 이름 뒤에 pid 붙이기
		boardRepo.findAll().forEach(board -> {
			String name = board.getPname() + "-" + board.getPid();
			board.setPname(name);
			
			boardRepo.save(board);
		});
	}
	
	// @Test
	void selectBoard5() {
		boardRepo.getFilesCount2().forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
	
	// @Test
	void selectBoard4() {
		Long pid = 4L;
		List<Object[]> blist = boardRepo.getFilesInfo2(pid);
		
		blist.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
	
	// @Test
	void insertBoard() {
		// 입력, 수정 (board에 file 추가)
		PDSBoardEntity board = boardRepo.findById(2L).orElse(null); // 2번의 파일 먼저 가져오기
		
		if (board == null) return;
		
		List<PDSFileEntity> flist = board.getFiles(); // 파일들 생성
		
		PDSFileEntity file1 = PDSFileEntity.builder().pdsfilename("파일 추가1.jpg").build();
		PDSFileEntity file2 = PDSFileEntity.builder().pdsfilename("파일 추가2.jpg").build();
		
		flist.add(file1); // 생성한 파일 추가
		flist.add(file2); // 생성한 파일 추가
		board.setWriter("작성자 수정");
		
		boardRepo.save(board);
	}
	
	// @Transactional // PDSBoardEntity에서 Lazy일 경우에도 files 조회 가능
	// @Test
	void selectBoard3() {
		// 조회
		String w = "수영";
		PDSBoardEntity board = boardRepo.findById(2L).orElse(null);
		
		if (board == null) return;
		
		System.out.println(board); // PDSBoardEntity에서 (exclude = {"files"}) 작성하지 않으면 오류
		System.out.println(board.getPname());
		System.out.println(board.getFiles().size() + "건");
	}
	
	// @Transactional // PDSBoardEntity에서 Lazy일 경우에도 files 조회 가능
	// @Test
	void selectBoard2() {
		// 조회
		String w = "수영";
		List<PDSBoardEntity> blist = boardRepo.findByWriter(w);
		
		for (PDSBoardEntity board : blist) {
			System.out.println(board); // PDSBoardEntity에서 (exclude = {"files"}) 작성하지 않으면 오류
			System.out.println(board.getPname());
			System.out.println(board.getFiles().size() + "건");
		}
	}
	
	// @Test
	void selectFile2() {
		// PDSFileEntity의 pdsfilename으로 조회
		String pdsfilename = "ptyhon-8-1.ppt";
		PDSFileEntity file = fileRepo.findByPdsfilename(pdsfilename);
		
		System.out.println(file);
	}
	
	// @Test
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