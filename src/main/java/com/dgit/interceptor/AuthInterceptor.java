package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	// 1. sbaord/resigter 접근
	// 2. 로그인 안된 경우. login화면 등장
	// 3. 로그인후 다시 register로 이동되도록
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("===================AUTH Interceptor PreHandle ================ ");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute(LoginInterceptor.LOGIN);
		System.out.println("session userId : " + userId);
		
		if(userId == null){
			saveDest(request);
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		return true;
	}
	
	// 로그인 이전 페이지 주소 저장함... 로그인 후 다시 이 페이지로 이동하기 위해서~!
	private void saveDest(HttpServletRequest req){
		System.out.println("===================saveDest================ ");
		String uri = req.getRequestURI();  // 주소창 주소들
		String query = req.getQueryString(); // ?key=value 같은 매개변수들~!
		
		if(query == null || query.equals("null")){
			query="";
		}else{
			query = "?"+query;
		}
		
		if(req.getMethod().equalsIgnoreCase("get")){
			System.out.println("=======uri :   " + uri); 	//    /ex01/sboard/register
			System.out.println("=======query : " + query);	
			System.out.println(uri+query);
			req.getSession().setAttribute("dest", uri+query);
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("=======AUTH Interceptor postHandle ================ ");
	
	}
}
