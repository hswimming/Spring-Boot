package com.shinhan.firstzone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.shinhan.firstzone.repository.GuestBookRepository;
import com.shinhan.firstzone.vo2.GuestBookDTO;
import com.shinhan.firstzone.vo2.GuestBookEntity;
import com.shinhan.firstzone.vo2.QGuestBookEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 필수 field를 constructor를 통해 injection
@Service
public class GuestBookServiceImpl implements GuestBookService { // 컨트롤러에서 사용
	final GuestBookRepository gRepo; // @RequiredArgsConstructor 어노테이션과 final 사용 시 @Autowired 어노테이션 생략 가능
	
	@Override
	public void create(GuestBookDTO dto) {
		GuestBookEntity entity = dtoToEntity(dto);
		
		gRepo.save(entity);
	}

	@Override
	public List<GuestBookDTO> readAll() {
		List<GuestBookEntity> entityList = (List<GuestBookEntity>) gRepo.findAll(); // 리터럴 타입이 상위, 형변환 필요
		
		// entity를 dto로 변환 -> dto로 return -> 다시 list로 만듦
		// List<GuestBookDTO> dtoList = entityList.stream().map(entity -> entityToDTO(entity)).toList();
		
		// entity를 dto로 변환 -> dto로 return -> 한 건 한 건을 모아서 다시 전체 list로 만듦
		List<GuestBookDTO> dtoList = entityList.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
		
		return dtoList;
	}

	@Override
	public GuestBookDTO readById(Long gno) {
		GuestBookEntity entity = gRepo.findById(gno).orElse(null); // 없을 수도 있음, 없으면 null
		
		return entityToDTO(entity);
	}

	@Override
	public void update(GuestBookDTO dto) {
		gRepo.findById(dto.getGno()).ifPresent(book -> {
			book.setContent(dto.getContent());
			book.setTitle(dto.getTitle());
			book.setWriter(dto.getWriter());
			
			gRepo.save(book);
		});
	}

	@Override
	public void delete(Long gno) {
		gRepo.deleteById(gno);
	}
	
	// 동적으로 SQL 생성
	@Override
	public List<GuestBookDTO> getSearch(String type, String keyword) { // 타입과 키워드를 가변적으로 받음
		// t -> title / c -> content / w -> writer / tc -> title, content / tcw -> title, content, writer
		
		BooleanBuilder builder = new BooleanBuilder();
		QGuestBookEntity entity = QGuestBookEntity.guestBookEntity; // from 절에 entity가 들어감
		
		BooleanExpression expression = entity.gno.gt(0L); // gno > 0
		builder.and(expression); // select b from GuestBookEntity b where b.gno > 0
		
		BooleanBuilder builder2 = new BooleanBuilder();
		
		if (type.contains("t")) { // 들어온 문자에 t가 있는지 확인 후 문장 만들기
			builder2.or(entity.title.contains(keyword));
		}
		
		if (type.contains("c")) { // 들어온 문자에 c가 있는지 확인 후 문장 만들기
			builder2.or(entity.content.contains(keyword));
		}
		
		if (type.contains("w")) { // 들어온 문자에 w가 있는지 확인 후 문장 만들기
			builder2.or(entity.writer.contains(keyword));
		}
		
		builder.and(builder2); // 만든 문장(builder2) builder에 붙이기 -> and (title like ? or content like ? or writer like ?)
		
		List<GuestBookEntity> entityList = (List<GuestBookEntity>) gRepo.findAll(builder); // 리터럴 타입이 상위, 형변환 필요

		// entity를 dto로 변환 -> dto로 return -> 한 건 한 건을 모아서 다시 전체 list로 만듦
		List<GuestBookDTO> dtoList = entityList.stream().map(e -> entityToDTO(e)).collect(Collectors.toList());
		
		return dtoList;
	}
}