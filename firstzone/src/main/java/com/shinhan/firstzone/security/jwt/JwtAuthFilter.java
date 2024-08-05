package com.shinhan.firstzone.security.jwt;

 
import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shinhan.firstzone.security.MemberService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// OncePerRequestFilter는 Http Request의 한번 요청에 한번만 실행하는 Filter 
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
	private final MemberService memberService;
    private final JwtUtil jwtUtil;
	 
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      
    	String authorizationHeader = request.getHeader("Authorization");

        // JWT가 헤더에 있는 경우
    	// Bearer 인증 방식은 OAuth 2.0 프레임워크에서 사용하는 토큰 인증 방식. "Bearer"는 소유자라는 뜻인데,
    	// "이 토큰의 소유자에게 권한을 부여해줘" 라는 의미로 이름을 붙임. Bearer이라는 단어 + 토큰 Header에 들어옴
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { // 주의!! 한 칸 띄어써야 함
            String token = authorizationHeader.substring(7); // "Bearer " => 7자이므로 자르고 사용
            
            // JWT 유효성 검증
            if (jwtUtil.validateToken(token)) {
                String userId = jwtUtil.getUserId(token); // token을 주면 user 정보만 추출

                // 유저와 토큰 일치 시 userDetails 생성
                UserDetails userDetails = memberService.loadUserByUsername(userId);

                if (userDetails != null) {
                    // UserDetsils, Password, Role -> 접근권한 인증 Token 생성
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // userDetails : security 된 user

                    // 현재 Request의 Security Context에 접근권한 설정
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        
        filterChain.doFilter(request, response);
    }
}