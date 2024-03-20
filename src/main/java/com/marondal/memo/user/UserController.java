package com.marondal.memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@GetMapping("/join-view")
	public String inputJoin() {
		return "user/join";
	}
	
	@GetMapping("/login-view")
	public String inputLogin() {
		return "user/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		// 세션에 사용자 정보를 저장하면 로그인 
		// 세션에 저장된 사용자 정보를 제거한다. 
		HttpSession session = request.getSession();
		// userId, userName
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		
		// 로그인 페이지로 리다이렉트
		return "redirect:/user/login-view";
	}

}
