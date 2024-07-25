package com.shinhan.firstzone.service2;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo4.QWebBoardEntity;
import com.shinhan.firstzone.vo4.WebBoardDTO;
import com.shinhan.firstzone.vo4.WebBoardEntity;

public interface WebBoardService {
	// CRUD 작업 제공
	
	// 1. 입력
	Long register(WebBoardDTO dto); // 자동으로 증가되는 bno 알기 위해 return
	
	// 2. 조회
	List<WebBoardDTO> getList();
	WebBoardDTO selectById(Long bno); // 상세 조회
	
	// 3. 수정
	void modify(WebBoardDTO dto);
	
	// 4. 삭제
	void delete(Long bno);
	
	// 인터페이스는 함수 정의만, 구현 X -> default method
	
	// DTO -> Entity (DB 반영하기 위함)
	default WebBoardEntity dtoToEntity(WebBoardDTO dto) {
		MemberEntity member = MemberEntity.builder().mid(dto.getMid()).build();
		
		WebBoardEntity entity = WebBoardEntity.builder()
											.bno(dto.getBno())
											.title(dto.getTitle())
											.writer(member)
											.content(dto.getContent())
											.build();
		
		return entity;
	}
	
	// Entity -> DTO (Data 전송을 위함 : controller, service, view 작업)
	default WebBoardDTO entityToDTO(WebBoardEntity entity) {
		WebBoardDTO dto = WebBoardDTO.builder()
										.bno(entity.getBno())
										.title(entity.getTitle())
										.mid(entity.getWriter().getMid())
										.mname(entity.getWriter().getMname())
										.content(entity.getContent())
										.regdate(entity.getRegdate())
										.updatedate(entity.getUpdatedate())
										.replyCount(entity.getReplies().size())
										.build();
		
		return dto;
	}
	
	// 동적 SQL 생성
	// t : title / c : content / w : writer / tc : title, content / tcw : title, content, writer
	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		
		QWebBoardEntity board = QWebBoardEntity.webBoardEntity;
		builder.and(board.bno.gt(0));
		
		BooleanBuilder builder2 = new BooleanBuilder();
		
		//검색조건처리
		if (type == null) return builder;
		
		if (type.contains("t")) builder2.or(board.title.like("%" + keyword + "%"));
		if (type.contains("c")) builder2.or(board.content.like("%" + keyword + "%"));
		if (type.contains("w")) builder2.or(board.writer.mname.like("%" + keyword + "%")); // memberEntity 안에 들어가서 이름 가져오기
		builder.and(builder2); // 원래 있던 조건 + or 조건
		
		return builder;
	}
}