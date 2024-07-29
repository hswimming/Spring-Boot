package com.shinhan.firstzone.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.shinhan.firstzone.vo2.MemberEntity;

import lombok.Setter;

@Setter
public class SecurityUser extends User{
	private static final long serialVersionUID = 1L;
	private static final String ROLE_PREFIX="ROLE_"; 
	
    // 기본제공 생성자
	public SecurityUser(String name, String password, Collection<? extends GrantedAuthority> authorities) {
		super(name, password, authorities);
		System.out.println("***** 생성자에서 출력 member : " + name);
	}
	
	// 개발자가 추가한 코드
	public SecurityUser(MemberEntity member) {	
		super(member.getMid(), member.getMpassword(), makeRole(member)  );	 
		System.out.println("***** MemberEntity  member : " + member);
	}
	
	// Role을 여러개 가질수 있도록 되어있음
	private static List<GrantedAuthority> makeRole(MemberEntity member) {
		// System.out.println("******************************ROLE" + ROLE_PREFIX + member.getMrole());
		
		List<GrantedAuthority> roleList = new ArrayList<>();
		roleList.add(new SimpleGrantedAuthority(ROLE_PREFIX + member.getMrole()));
		
		return roleList;
	}
	
	// User class에서 username필드가 있지만 google 인증시 사용되는 필드는 name 이를 맞추기위해 함수 추가함
	public String getName() {
		return super.getUsername();
	}
}