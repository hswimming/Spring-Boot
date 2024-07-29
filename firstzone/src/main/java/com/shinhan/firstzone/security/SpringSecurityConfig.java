package com.shinhan.firstzone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // web에서 security를 사용 가능
@Configuration // 설정 파일을 의미 (App 시작 시 해석됨)
public class SpringSecurityConfig {
	// 상수로 접근 권한에 따른 요청 주소들을 저장
	private static final String[] WHITE_LIST = {"/security/all", "/auth/**", "/v3/**", "swagger-ui/**"}; // 로그인 하지 않아도 들어갈 수 있음
	private static final String[] USER_LIST = {"/security/user", "/webboard/**", "/replies/**"}; // webboard, replies로 시작하는 경로 전부
	private static final String[] ADMIN_LIST = {"/security/admin"};
	private static final String[] MANAGER_LIST = {"/security/manager"};
	
	@Bean
	PasswordEncoder passEncoder() {
		// BCryptPasswordEncoder 생성해서 PasswordEncoder 리턴
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
		// http.csrf(c -> c.disable()); // csrf 토큰을 사용하지 않음, default : 토큰 사용
		
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers(WHITE_LIST).permitAll(); // 로그인 불필요
			auth.requestMatchers(USER_LIST).hasRole("USER");
			auth.requestMatchers(ADMIN_LIST).hasRole("ADMIN");
			auth.requestMatchers(MANAGER_LIST).hasRole("MANAGER");
			
			auth.anyRequest().authenticated();
		});
		
		// SecurityAuthController에서 작성한 로그인 페이지 보여주기
		http.formLogin(login -> login.loginPage("/auth/login")
												.usernameParameter("mid") // default 이름 : username, 변경하려면 반드시 설정 필요 (html 파일에서 mid로 변경함)
												.defaultSuccessUrl("/auth/loginSuccess") // 로그인 성공 후 연결할 경로
												.permitAll());
		
		http.logout(out -> out.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
										.logoutSuccessUrl("/auth/login")
										.invalidateHttpSession(true));
		
		http.exceptionHandling(handling -> handling.accessDeniedPage("/auth/accessDenined"));
		
		return http.build();
	}
}