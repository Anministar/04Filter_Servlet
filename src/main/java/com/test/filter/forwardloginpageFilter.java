package com.test.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class forwardloginpageFilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		//Request 요청 전 처리
		System.out.println("[FF] ForwardLoginFilter Start!");
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//로그인이 된 상태인지 유무 확인
		HttpSession session = request.getSession(true);  //true(기본) : 세션객체 없으면 새로 생성
														  //false : 세션객체 없으면 null
		
		if(session.getAttribute("authdto") == null) { //no login
			
			if(request.getRequestURI().contains("/member/join.do")) {
				req.getRequestDispatcher("/member/join.do").forward(request, response);
				//종료
				return ;
			}
			
			String msg = "로그인이 필요한 사이트 입니다.";
			req.setAttribute("msg", msg);
			req.getRequestDispatcher("/auth/login.do").forward(request, response);
			//종료
			return ;
			
		}
		//로그인한 상태
		chain.doFilter(req, resp);
		
		//Response 전달 전 처리
		System.out.println("[FF] ForwardLoginFilter End!");
	}
	
}
