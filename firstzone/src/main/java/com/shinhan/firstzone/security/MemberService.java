package com.shinhan.firstzone.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.vo2.MemberEntity;

import jakarta.servlet.http.HttpSession;

@Service
public class MemberService implements UserDetailsService {
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private MemberRepository mrepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// 회원가입
	// @Transactional
	public MemberEntity joinUser(MemberEntity member) {
		// 비밀번호 암호화... 암호화 되지 않으면 로그인 되지 않는다.
		String pwd = passwordEncoder.encode(member.getMpassword());
		member.setMpassword(pwd);
		
		return mrepo.save(member);
	}
	
	// !!!! 반드시 구현해야한다 (로그인) !!!!
	@Override 
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername mid : " + userName);
		
	    Optional<MemberEntity> optionMember =  mrepo.findById(userName);
	    
	    // login member를 security member로 바꿔야 함
	    // Application Member
	    MemberEntity loginUser = optionMember.orElse(null);

	    // Spring Secirity User
		UserDetails member = optionMember.filter(m -> m != null)
															.map(m -> new SecurityUser(m)).get();
		
		// 로그인 정보를 세션에 저장
		httpSession.setAttribute("user", loginUser);
		
		return member;
	}
}