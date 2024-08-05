package com.shinhan.firstzone.security.jwt;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.vo2.MemberEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// @Transactional 
public class AuthServiceImpl {

	private final JwtUtil jwtUtil;
	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;

	// @Transactional
	public String login(MemberEntity dto) {
		String mid = dto.getMid();
		String password = dto.getMpassword();
		MemberEntity member = memberRepository.findById(mid).orElse(null);
		
		if (member == null) {
			throw new UsernameNotFoundException("아이디가 존재하지 않습니다.");
		}

		// 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
		if (!encoder.matches(password, member.getMpassword())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}

		String accessToken = jwtUtil.createAccessToken(member);
		return accessToken;
	}
}