package com.shinhan.firstzone.service2;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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