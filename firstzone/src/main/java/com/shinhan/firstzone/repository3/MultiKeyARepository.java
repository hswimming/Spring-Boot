package com.shinhan.firstzone.repository3;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.firstzone.vo3.MultiKeyA;
import com.shinhan.firstzone.vo3.MultiKeyAClass;

public interface MultiKeyARepository extends CrudRepository<MultiKeyAClass, MultiKeyA> {
	// 1개의 아이디로만 조회
	List<MultiKeyAClass> findById1(Long aa);
}