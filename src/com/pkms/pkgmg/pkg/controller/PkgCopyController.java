package com.pkms.pkgmg.pkg.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.service.PkgCopyServiceIf;

/**
 * PKG 복사 관련 Controller<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Controller
public class PkgCopyController {

	@Resource(name = "PkgCopyService")
	private PkgCopyServiceIf pkgCopyService;

	/**
	 * PKG 복사
	 * @param pkgModel
	 * @param model
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgCopy_Create.do")
	public String create(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		String pkg_seq = pkgCopyService.create(pkgModel);
        status.setComplete();
		return ResultUtil.handleSuccessResultParam(model, pkg_seq, "");
	}

}
