package com.shinhan.firstzone.service;

import java.util.List;

import com.shinhan.firstzone.vo2.GuestBookDTO;
import com.shinhan.firstzone.vo2.GuestBookEntity;

public interface GuestBookService {
	// CRUD
	
	// 1. Create
	void create(GuestBookDTO dto);
	
	// 2. Read
	List<GuestBookDTO> readAll(); // 전체 조회
	GuestBookDTO readById(Long gno); // 선택 조회
	
	List<GuestBookDTO> getSearch(String type, String keyword); // 동적 SQL
	
	// 3. Update
	void update(GuestBookDTO dto);
	
	// 4. Delete
	void delete(Long gno);
	
	// Entity -> DTO
	default GuestBookDTO entityToDTO(GuestBookEntity entity) { // 인터페이스에서 함수 생성 시 default 사용
		GuestBookDTO dto = GuestBookDTO.builder()
				  					  .gno(entity.getGno())
				  					  .title(entity.getTitle())
				  					  .content(entity.getContent())
				  					  .writer(entity.getWriter())
				  					  .regDate(entity.getRegDate())
				  					  .modDate(entity.getModDate())
				  					  .build();
		
		return dto;
	}
	
	// DTO -> Entity
	default GuestBookEntity dtoToEntity(GuestBookDTO dto) { // 인터페이스에서 함수 생성 시 default 사용
		GuestBookEntity entity = GuestBookEntity.builder()
											.title(dto.getTitle())
											.content(dto.getContent())
											.writer(dto.getWriter())
											.build();
		
		return entity;
	}
}