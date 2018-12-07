package com.pkms.pkgmg.pkg.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.service.PkgServiceIf;
import com.pkms.pkgmg.pkg.service.PkgTab2ServiceIf;

/**
 * 첨부파일(두번째탭) 관련 Controller
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Controller
public class PkgTab2Controller {

	@Resource(name = "PkgTab2Service")
	private PkgTab2ServiceIf pkgTab2Service;
	
	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;
	/**
	 * 첨부파일 조회
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgTab2_Ajax_Read.do")
	public String read(PkgModel pkgModel, Model model) throws Exception {
		PkgModel pkgModelData = pkgTab2Service.read(pkgModel);

		model.addAttribute("PkgModel", pkgModelData);

		return "/pkgmg/pkg/PkgTab2_Ajax_Read";
	}

	/**
	 * 첨부파일 수정
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgTab2_Update.do")
	public String update(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		pkgTab2Service.update(pkgModel);
		pkgService.urgency_update(pkgModel);
		
        status.setComplete();
		return ResultUtil.handleSuccessResult();
	}
	
	/**
	 * roadMap create
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgRoadMap_Update.do")
	public String roadMapUpdate(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		
		PkgModel pkgroadmap = pkgTab2Service.roadMapUpdate(pkgModel);
		
		String prm_seq04 = pkgroadmap.getPkg_road_map_seq_04();
		String prm_seq07 = pkgroadmap.getPkg_road_map_seq_07();
		String prm_seq08 = pkgroadmap.getPkg_road_map_seq_08();
		
		String rm_seq04 = pkgroadmap.getRoad_map_seq_04();
		String rm_seq07 = pkgroadmap.getRoad_map_seq_07();
		String rm_seq08 = pkgroadmap.getRoad_map_seq_08();
		
        status.setComplete();
		return ResultUtil.handleSuccessResultParam(model, prm_seq04, prm_seq07, prm_seq08, rm_seq04, rm_seq07, rm_seq08);
	}
	
}
