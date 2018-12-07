package com.pkms.pkgmg.pkg.controller;

import javax.annotation.Resource;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.service.PkgTab1ServiceIf;

/**
 * 기본정보(첫번째탭) 관련 Controller
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Controller
public class PkgTab1Controller {

	@Resource(name = "PkgTab1Service")
	private PkgTab1ServiceIf pkgTab1Service;

	/**
	 * 기본정보 등록
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgTab1_Create.do")
	public String create(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		if(pkgModel.getContent().length() > 550){
			throw new Exception("error.biz.주요보완내역의 글자수가 제한된 글자수를 초과하였습니다. 550자이하로 줄여 주세요.");
		}
		
		pkgModel.setDev_yn(pkgModel.getDev_yn_bak());
		
		pkgTab1Service.create(pkgModel);
		status.setComplete();
		return ResultUtil.handleSuccessResultParam(model, pkgModel.getPkg_seq(), "");
	}

	/**
	 * 기본정보 수정
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgTab1_Update.do")
	public String update(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		if(pkgModel.getContent().length() > 550){
			throw new Exception("error.biz.주요보완내역의 글자수가 제한된 글자수를 초과하였습니다. 550자이하로 줄여 주세요.");
		}
		
		pkgModel.setDev_yn(pkgModel.getDev_yn_bak());
		
		if("1".equals(pkgModel.getStatus())){
			if("D".equals(pkgModel.getDev_yn()) && "0".equals(pkgModel.getStatus_dev())){
				pkgModel.setStatus_dev("1");
			}
		}
		
		pkgTab1Service.update(pkgModel);
        status.setComplete();
		return ResultUtil.handleSuccessResultParam(model, pkgModel.getSystem_seq(), "");
	}
}
