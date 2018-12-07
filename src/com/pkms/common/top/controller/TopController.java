package com.pkms.common.top.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.menu.model.MenuModel;
import com.pkms.common.menu.service.MenuServiceIf;
import com.pkms.common.top.model.TopModel;
import com.pkms.common.top.service.TopServiceIf;
import com.wings.properties.service.PropertyServiceIf;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 5.
 * 
 */
@Controller
public class TopController {

	@Resource(name = "MenuService")
	private MenuServiceIf menuService;
	
	@Resource(name = "TopService")
	private TopServiceIf topService;

	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@RequestMapping("/common/top/Header_Menu1Depth_ReadList.do")
	public String menu1Depth_ReadList(TopModel topModel, HttpServletRequest httpServletRequest, Model model) throws Exception {

		ArrayList<MenuModel> topMenuList = menuService.readListTopMenu();
		model.addAttribute("topMenuList", topMenuList);

		model.addAttribute("topModel", topService.read(topModel));
		
		ArrayList<MenuModel> subMenuList = menuService.readListSubMenu();
		
		model.addAttribute("subMenuList", subMenuList);
		
		String requestURI = httpServletRequest.getRequestURI();
		System.out.println(requestURI);
		
		String [] uri = requestURI.split("/");
		
		if("board".equals(uri[1])){
			uri[1] = uri[1]+"/"+uri[2];
		}
		
		MenuModel parentModel = menuService.parentSeq(uri[1]);
		int ParentSeq=0;
		if(parentModel != null){
			ParentSeq = parentModel.getParent_seq();
		}
		model.addAttribute("ParentSeq", ParentSeq);
//		model.addAttribute("ASIS_PKMS_URL", propertyService.getString("ASIS.PKMS.URL"));

		return "/common/top/Header_Menu1Depth_ReadList";
	}

	@RequestMapping("/common/top/Header_Menu2Depth_ReadList.do")
	public String menu2Depth_ReadList(Model model) throws Exception {

		ArrayList<MenuModel> topMenuList = menuService.readListTopMenu();
		model.addAttribute("topMenuList", topMenuList);

		ArrayList<MenuModel> subMenuList = menuService.readListSubMenu();
		model.addAttribute("subMenuList", subMenuList);

		return "/common/top/Header_Menu2Depth_ReadList";
	}

	@RequestMapping("/common/top/Body_TitlePath_Read.do")
	public String titlePathRead(HttpServletRequest httpServletRequest, Model model) throws Exception {

		String requestURI = httpServletRequest.getRequestURI();
		model.addAttribute("requestURI", requestURI);

		ArrayList<MenuModel> allMenuList = menuService.readListAllMenu();
		model.addAttribute("allMenuList", allMenuList);

		return "/common/top/Body_TitlePath_Read";
	}
}
