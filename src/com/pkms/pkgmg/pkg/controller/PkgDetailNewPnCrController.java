package com.pkms.pkgmg.pkg.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.service.PkgDetailNewPnCrServiceIf;

/**
 * PKG NewPnCr 관련 Controller<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Controller
public class PkgDetailNewPnCrController {

	@Resource(name = "PkgDetailNewPnCrService")
	private PkgDetailNewPnCrServiceIf pkgDetailNewPnCrService;

	/**
	 * NewPnCr 목록 조회
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgDetailNewPnCr_Ajax_ReadList.do")
	public String readList(PkgModel pkgModel, Model model) throws Exception {
		pkgModel = pkgDetailNewPnCrService.readList(pkgModel);

		model.addAttribute("PkgModel", pkgModel);
		return "/pkgmg/pkg/PkgDetailNewPnCr_Ajax_ReadList";
	}

	/**
	 * NewPnCr 매핑
	 * @param pkgModel
	 * @param model
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgDetailNewpncr_Update.do")
	public String update(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		pkgDetailNewPnCrService.update(pkgModel);
        status.setComplete();
		return ResultUtil.handleSuccessResultParam(model, pkgModel.getPkg_detail_seq(), "");
	}
	

}
