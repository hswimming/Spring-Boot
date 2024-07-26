package com.shinhan.firstzone.paging;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class PageResultDTO<DTO, EN> { // 어떤 entity가 들어올지 모르므로 Generic type
	// data
	private List<DTO> dtoList;
	// 총 페이지 번호
	private int totalPage;
	// 현재 페이지 번호
	private int page;
	// 목록 사이즈
	private int size;
	// 시작 페이지 번호, 끝 페이지 번호
	private int start, end;
	// 이전, 다음
	private boolean prev, next;
	// 페이지 번호 목록
	private List<Integer> pageList;
	
	// Page<Entity>객체들을 DTO객체로 변환해서 자료구조에 넣기 
	public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) { // entity가 들어있는 Page, 들어온 것을 바꾸는건 그때마다 다름 -> Function
		dtoList = result.stream().map(fn).collect(Collectors.toList()); // map 안에 function 주고 다시 DTO list 만들기
		totalPage = result.getTotalPages(); // 전체 페이지
		
		makePageList(result.getPageable());
	}
	
	private void makePageList(Pageable pageable) {
		this.page = pageable.getPageNumber() + 1; // getPageNumber -> 0부터 시작 (usert한테 보여줄 때는 1부터 시작으로 보여줘야 함)
		this.size = pageable.getPageSize();
		
		int tempEnd = (int)(Math.ceil(page/10.0))*10; // 1 ~ 10 -> start : 1, end : 10
		start = tempEnd - 9; // 한 화면에 10개의 페이지 번호를 출력하기로 가정함
		end = totalPage > tempEnd?tempEnd:totalPage;
		end = totalPage < 10 ? totalPage : end;  //10개보다 작다면 page 번호 까지만
		prev = start > 1;  // 전체 페이지가 있는가?
		next = totalPage > tempEnd;
		
		System.out.printf("tempEnd : %d, totalPage : %d start : %d end : %d \n", tempEnd, totalPage, start, end);
		pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList()); // Int -> Integer, boxing 한 것을 다시 list로 바꾸기
	}
}