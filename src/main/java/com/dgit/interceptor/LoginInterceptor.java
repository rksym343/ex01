package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dgit.domain.MemberVO;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	public static final String LOGIN = "login";
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("==========================LoginInterceptor preHandle=============================");
		return true; // return true시 해당 컨트롤러로 이동해도 된다는 표시
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("==========================LoginInterceptor POST HANDLER=============================");
		MemberVO loginVO = (MemberVO) modelAndView.getModel().get("loginVO");
		System.out.println("=========MEMBERVO=======       : " + loginVO);
		
		if(loginVO == null){
			response.sendRedirect("login"); // 원래 회원가입화면으로 유도해야 함..
			
		}else{
			// 로그인시 session영역에 login한 사람 정보를 넣음...
			
			HttpSession session = request.getSession();
			session.setAttribute(LOGIN, loginVO.getUserid());
			
			
			// 로그인 이전에 이동될 uri가 있다면 dest에 저장해둠
			// 저장된 dest의 값을 받아 그곳으로 이동되도록..
			String path = (String) session.getAttribute("dest");
			System.out.println("=======path : " + path);	
			if(path != null){
				response.sendRedirect(path);
			}
			
		}
		
		
	}

	
	
	
}
