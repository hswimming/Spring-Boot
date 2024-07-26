package com.shinhan.firstzone.service2;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shinhan.firstzone.paging.PageRequestDTO;
import com.shinhan.firstzone.paging.PageResultDTO;
import com.shinhan.firstzone.repository4.WebBoardRepository;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo4.WebBoardDTO;
import com.shinhan.firstzone.vo4.WebBoardEntity;

@Service
public class WebBoardServiceImpl implements WebBoardService {
	@Autowired
	WebBoardRepository boardRepo;
	
	@Override
	public Long register(WebBoardDTO dto) {
		// dto -> entity 바꿔서 저장
		WebBoardEntity newEntity = boardRepo.save(dtoToEntity(dto));
		
		return newEntity.getBno(); // 입력 된 entity 번호
	}

	@Override
	public List<WebBoardDTO> getList() {
		// 들어온 entity -> dto 변경
		List<WebBoardDTO> blist = boardRepo.findAll().stream().map(en -> entityToDTO(en))
												.collect(Collectors.toList()); // 한개 한개 바꾼 dto 다시 모으기
		
		return blist;
	}
	
	// 인터페이스에서 정의하면 public
	public PageResultDTO<WebBoardDTO, WebBoardEntity> getList(PageRequestDTO pageRequestDTO) { // 페이징 처리 되면서 조회
		// querydsl 제공 메서드 -> findAll(Predicate, Pageable)
		Page<WebBoardEntity> result = boardRepo.findAll(makePredicate(pageRequestDTO.getType(), // Type, Keyword 주면 Predicate 생성
																									 pageRequestDTO.getKeyword()),
																									 // PageRequestDTO에 있는 getPageable 메서드를 사용하기 위해 sort 생성
																									 pageRequestDTO.getPageable(Sort.by("bno").descending()));
		
		Function<WebBoardEntity, WebBoardDTO> fn = en -> entityToDTO(en);
		PageResultDTO<WebBoardDTO, WebBoardEntity> result2 = new PageResultDTO<>(result, fn);
		
		return result2;
	}

	@Override
	public WebBoardDTO selectById(Long bno) {
		/*
		Optional<WebBoardEntity> en = boardRepo.findById(bno);
		
		if (en.isPresent()) {
			return entityToDTO(en.get()); // Optional (있을수도, 없을수도) -> 있으면 get();
			
		} else {
			return null;
		}
		*/
		
		WebBoardEntity en = boardRepo.findById(bno).orElse(null);
		
		if (en == null) return null;
		return entityToDTO(en);
	}

	@Override
	public void modify(WebBoardDTO dto) {
		boardRepo.findById(dto.getBno()).ifPresent(en -> {
			en.setTitle(dto.getTitle());
			en.setContent(dto.getContent());
			
			MemberEntity member = MemberEntity.builder().mid(dto.getMid()).build();
			en.setWriter(member);
			
			boardRepo.save(en);
		});
	}

	@Override
	public void delete(Long bno) {
		boardRepo.deleteById(bno);
	}
}