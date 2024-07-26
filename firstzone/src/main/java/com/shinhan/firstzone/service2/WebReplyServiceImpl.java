package com.shinhan.firstzone.service2;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.firstzone.repository4.WebReplyRepository;
import com.shinhan.firstzone.vo4.WebBoardEntity;
import com.shinhan.firstzone.vo4.WebReplyDTO;
import com.shinhan.firstzone.vo4.WebReplyEntity;

@Service
public class WebReplyServiceImpl implements WebReplyService {
	@Autowired
	WebReplyRepository replyRepo;
	
	// 등록
	@Override
	public Long register(WebReplyDTO dto) {
		WebReplyEntity newReply = replyRepo.save(dtoToEntity(dto));
		
		return newReply.getRno();
	}

	// 특정 board의 댓글들 조회
	@Override
	public List<WebReplyDTO> getList(Long bno) {
		WebBoardEntity board = WebBoardEntity.builder().bno(bno).build();
		List<WebReplyEntity> entityList = replyRepo.findByBoard(board);
		
		// map 안에 함수
		// return entityList.stream().map(en -> entityToDTo(en)).collect(Collectors.toList());
		
		// 위와 같은 코드, 풀어서 작성
		Function<WebReplyEntity, WebReplyDTO> fn = en -> entityToDTo(en);
		return entityList.stream().map(fn).collect(Collectors.toList());
	}

	// 수정
	@Override
	public void modify(WebReplyDTO dto) {
		replyRepo.findById(dto.getRno()).ifPresent(reply -> {
			reply.setReply(dto.getReply());
			reply.setReplyer(dto.getReplyer());
			
			replyRepo.save(reply);
		});
	}

	// 삭제
	@Override
	public void delete(Long rno) {
		replyRepo.deleteById(rno);
	}
}