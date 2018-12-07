package com.pkms.sys.group3.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.group3.service.Group3ServiceIf;

/**
 * PKG현황에서 검색조건으로 사용되는 select-box 만들기 위한 클래스
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Controller
public class Group3Controller {

	/**
	 * 중분류 서비스
	 */
	@Resource(name = "Group3Service")
	private Group3ServiceIf group3Service;
	
	/**
	 * PKG현황에서 검색조건으로 사용되는 중분류 select-box 목록
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/common/Group3_Select_Ajax_Read.do")
	public String readList(PkgModel pkgModel, Model model) throws Exception {
		SysModel sysModel = new SysModel();
		sysModel.setGroup2_seq(pkgModel.getGroup2Condition());
		List<SysModel> group3List = group3Service.readList(sysModel);

		model.addAttribute("SysModelList", group3List);
		
		return "/pkgmg/common/Group3_Select_Ajax_ReadList";
	}
}
