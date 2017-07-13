package com.dgit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommonExceptionAdvice {

	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
	
	/*@ExceptionHandler(Exception.class)
	public String common(Exception e) {
		logger.info(e.toString());
		return "error_common";
	}*/
	
	/*@ExceptionHandler(Exception.class)
	private ModelAndView errorModelAndView(Exception e) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/error_common");
		model.addObject("exception", e);
		return model;
	}*/
}
