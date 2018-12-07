package com.pkms.main.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController {
	
	@RequestMapping(value = "/errorPage.do")
	public String read(HttpServletRequest request, Model model) throws Exception{
		return "/common/Exception";
	}
	
	@RequestMapping(value = "/errorPage_4_exception.do")
	public String read_4_Exception(HttpServletRequest request, Model model) throws Exception{
		return "/common/Exception";
	}

}
