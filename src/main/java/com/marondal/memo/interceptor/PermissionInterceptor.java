package com.marondal.memo.interceptor;

import java.io.IOException;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PermissionInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(
			HttpServletRequest request
			, HttpServletResponse response
			, Object handler) throws IOException {
		
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		
		// /post/list-view
		String uri = request.getRequestURI();
		
		// 로그인 안된 상태에서 메모 게시글과 관련된 페이지 접근을 막고
		// 로그인 페이지로 이동
		if(userId == null) {
			// /post 로 시작하는 주소의 요청 
			if(uri.startsWith("/post")) {
				// 로그인 페이지로 리다이렉트
				response.sendRedirect("/user/login-view");
				return false;
			}
			
		} else { // 로그인이 되어 있는 경우
			// /user 로 시작하는 주소 요청인경우 리스트 페이지로 이동
			if(uri.startsWith("/user")) {
				response.sendRedirect("/post/list-view");
				return false;
				
			}
			
		}

		return true;
		
		
		
	}

}
