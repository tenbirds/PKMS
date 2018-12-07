package com.pkms.common.util;

import org.springframework.ui.Model;

public class ResultUtil {

	public static String handleSuccessResult() {

		return "/common/Message";
	}
	
	public static String handleSuccessResultParam(Model model, String param1, String param2) {
		model.addAttribute("param1", param1);
		model.addAttribute("param2", param2);

		return "/common/AjaxParam";
	}
	
	public static String handleSuccessResultParam(Model model, String param1, String param2 ,String param3, String param4, String param5) {
		model.addAttribute("param1", param1);
		model.addAttribute("param2", param2);
		model.addAttribute("param3", param3);
		model.addAttribute("param4", param4);
		model.addAttribute("param5", param5);

		return "/common/AjaxParam";
	}
	
	public static String handleSuccessResultParam(Model model, String param1, String param2 ,String param3, String param4, String param5, String param6) {
		model.addAttribute("param1", param1);
		model.addAttribute("param2", param2);
		model.addAttribute("param3", param3);
		model.addAttribute("param4", param4);
		model.addAttribute("param5", param5);
		model.addAttribute("param6", param6);

		return "/common/AjaxParam";
	}
}
