package com.pkms.pkgmg.pkg.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.service.PkgDetailVariableServiceIf;

/**
 * PKG 보완적용내역 관련 Controller<br/>
 * - 가변필드 포함
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Controller
public class PkgDetailVariableController {

	@Resource(name = "PkgDetailVariableService")
	private PkgDetailVariableServiceIf pkgDetailVariableService;
	
	/**
	 * 보완적용내역 목록조회
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgDetailVariable_Ajax_ReadList.do")
	public String readList(PkgModel pkgModel, Model model) throws Exception {
		PkgModel pkgModelData = pkgDetailVariableService.read(pkgModel);
		model.addAttribute("PkgModel", pkgModelData);
		return "/pkgmg/pkg/PkgDetailVariable_Ajax_ReadList";
	}
	
	/**
	 * 보완적용내역 목록추가
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgDetailVariable_Ajax_WriteList.do")
	public String writeList(PkgModel pkgModel, Model model) throws Exception {
		PkgModel pkgModelData = pkgDetailVariableService.read(pkgModel);
		model.addAttribute("PkgModel", pkgModelData);
		return "/pkgmg/pkg/PkgDetailVariable_Ajax_WriteList";
	}
	
	/**
	 * 보완적용내역 수정
	 * @param pkgModel
	 * @param model
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgDetailVariable_Update.do")
	public String update(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		pkgDetailVariableService.update(pkgModel);
        status.setComplete();
        String tt = ResultUtil.handleSuccessResult();
        System.out.println("==========UPDATE===============");
        System.out.println(tt);
		return tt;
	}
	
	@RequestMapping(value = "/pkgmg/pkg/PkgDetailVariable_Insert.do")
	public String insert(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		pkgDetailVariableService.insert(pkgModel);
        status.setComplete();
		return ResultUtil.handleSuccessResult();
	}
	
	/**
	 * 보완적용내역 - 상용검증결과 OK NOK COK update
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgDetailVariable_OkNok_Update.do")
	public String okNokupdate(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		pkgDetailVariableService.okNokUpdate(pkgModel);

        status.setComplete();
        String tt = ResultUtil.handleSuccessResult();
        System.out.println("==========OK/NOK/COK/BYPASS===============");
        System.out.println(tt);
		return tt;
	}
	
	/**
	 * 보완적용내역 개별 조회
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgDetailVariable_Ajax_Read.do")
	public String read(PkgModel pkgModel, Model model) throws Exception {
		//NEW/PN/CR이나 상세 클릭하면 pkg_detail_seq 값이 세팅되어
		//보완적용내역 조회 시 pkg_detail_seq 값이 있으면 안되기 때문에
		pkgModel.setPkg_detail_seq("");
		PkgModel pkgModelData = pkgDetailVariableService.read(pkgModel);
		model.addAttribute("PkgModel", pkgModelData);
		return "/pkgmg/pkg/PkgDetailVariable_Ajax_Read";
	}

}
