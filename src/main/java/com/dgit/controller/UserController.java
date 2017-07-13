package com.dgit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.MemberVO;
import com.dgit.interceptor.LoginInterceptor;
import com.dgit.service.MemberService;

@Controller
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	private MemberService service;
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void getLogin(){
		// return 안하면 value값에 해당되는 [login].jsp로 이동 --> /user/login 로 가므로... user폴더 만들고 그 아래 login.jsp 만들어야 한다 
		System.out.println("===========UserController getLogin===========");
	}
	
	
	@RequestMapping(value="/loginPost", method=RequestMethod.POST)
	public void postLogin(MemberVO member, Model model) throws Exception{
		MemberVO loginVO = service.login(member.getUserid(), member.getUserpw());
		System.out.println("===========UserController loginPost===========");
		if(loginVO == null){
			// 회원가입을 한 적이 없으면 memberVO가 없음
			// insterceptor에서 memberVO가 없으면 login화면으로 다시 가도록 처리
			return;
		}else{
			
		}
		model.addAttribute("loginVO", loginVO);		
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout(HttpSession session){
		System.out.println("===========UserController logout===========");
		session.removeAttribute(LoginInterceptor.LOGIN);
		session.invalidate();
	}
}
