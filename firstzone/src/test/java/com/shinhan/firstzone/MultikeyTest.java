package com.shinhan.firstzone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository3.MultiKeyARepository;
import com.shinhan.firstzone.repository3.MultiKeyBRepository;
import com.shinhan.firstzone.vo3.MultiKeyAClass;
import com.shinhan.firstzone.vo3.MultiKeyB;
import com.shinhan.firstzone.vo3.MultiKeyBClass;

@SpringBootTest
public class MultikeyTest {
	@Autowired
	MultiKeyARepository aRepo;
	
	@Autowired
	MultiKeyBRepository bRepo;
	
	// @Test
	void selectB() {
		MultiKeyB id = MultiKeyB.builder().id1(1).id2(2).build();
		
		bRepo.findByMultib(id).forEach(data -> System.out.println(data));
	}
	
	// @Test
	void insertB() {
		MultiKeyB id = MultiKeyB.builder().id1(2).id2(3).build();
		
		MultiKeyBClass b = MultiKeyBClass.builder()
									.multib(id)
									.productName("커피")
									.price(100)
									.build();
		
		bRepo.save(b);
	}
	
	// @Test
	void selectA() {
		aRepo.findById1(10L).forEach(aa -> System.out.println(aa));
	}
	
	// @Test
	void insertA() {
		MultiKeyAClass a = MultiKeyAClass.builder()
									.id1(10)
									.id2(30)
									.productName("키보드 A")
									.price(100)
									.build();
		
		aRepo.save(a);
	}
}