package com.pkms.pkgmg.pkg.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.dao.PkgStatusDAO;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgStatusModel;
import com.pkms.pkgmg.pkg.service.PkgServiceIf;
import com.pkms.pkgmg.pkg.service.PkgStatusServiceIf;
import com.pkms.pkgmg.pkg.service.PkgStatusVerifyServiceIf;

/**
 * PKG 상태별 관련 Controller<br/>
 * 
 * @author : 009
 * @Date : 2012. 5. 08.
 * 
 */
@Controller
public class PkgStatusController {

	@Resource(name = "PkgStatusService")
	private PkgStatusServiceIf pkgStatusService;
	
	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;

	@Resource(name = "PkgStatusDAO")
	private PkgStatusDAO pkgStatusDAO;
	
	@Resource(name = "PkgStatusVerifyService")
	private PkgStatusVerifyServiceIf pkgStatusVerifyService;
	
	/**
	 * 상태별 등록
	 * @param pkgModel
	 * @param model
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgStatus_Create.do")
	public String create(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		if("D".equals(pkgModel.getDev_yn_bak())){
			pkgModel.setDev_yn("D");
		}
		pkgStatusService.create(pkgModel);
		pkgService.urgency_update(pkgModel);
		
        status.setComplete();
		return ResultUtil.handleSuccessResultParam(model, pkgModel.getStatus(), pkgModel.getMax_status());
	}
	
	/**
	 * 상태별 조회
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgStatus_Ajax_Read.do")
	public String read(PkgModel pkgModel, Model model) throws Exception {
		PkgModel pkgModelData = new PkgModel();
		
		if("D".equals(pkgModel.getDev_yn_bak())){
			pkgModel.setDev_yn("D");
		}
		
		String selected_status = pkgModel.getSelected_status();
		if ("".equals(selected_status)) {
			throw new Exception("error.biz.Status가 선택되지 않았습니다.");
		}

		// 선택된 flow num 에 따른 페이지 분기
		String retUrl = "/pkgmg/pkg/Pkg_Status_" + selected_status + "_Ajax_Read";

		// 선택된 Status Read
		pkgModelData = pkgStatusService.read(pkgModel);

		List<PkgStatusModel> list = pkgModelData.getPkgStatusModelList();
		if(list != null) {
			int i = 0;
			for(PkgStatusModel pkgStatusModel : list) {
				model.addAttribute("PkgStatusModel_" + i, pkgStatusModel);
				i++;
			}
		}
		if("2".equals(pkgModel.getSelected_status()) || "26".equals(pkgModel.getSelected_status()) || "3".equals(pkgModel.getSelected_status()) || "7".equals(pkgModel.getSelected_status())){
			PkgModel pkgModelProgressData = new PkgModel();
			// progress read
			pkgModelProgressData = pkgService.popupProgressRead(pkgModel);
			model.addAttribute("PkgModelProgress", pkgModelProgressData);
			if("3".equals(pkgModel.getSelected_status())){
				pkgModel.setSelected_status("2");
				
				PkgStatusModel pkgStatusModel = new PkgStatusModel();
				
				pkgStatusModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgStatusModel.setStatus(pkgModel.getSelected_status());
				
				PkgStatusModel pkgStatusModelData = pkgStatusDAO.read(pkgStatusModel);
				
				model.addAttribute("PkgModelData", pkgStatusModelData);
				pkgModel.setSelected_status("3");

				PkgModel pkgVerifyInfo = new PkgModel();
				pkgVerifyInfo.setPkg_seq(pkgModel.getPkg_seq());
				
				// 패키지의 검증정보 가져오기(검증버전,검증content)
				pkgVerifyInfo.setVerify_type("vol");
				PkgModel vol = pkgStatusVerifyService.readPKGVerify_info(pkgVerifyInfo); 
		
				pkgVerifyInfo.setVerify_type("cha");
				PkgModel cha = pkgStatusVerifyService.readPKGVerify_info(pkgVerifyInfo);
				
				pkgVerifyInfo.setVerify_type("non");
				PkgModel non = pkgStatusVerifyService.readPKGVerify_info(pkgVerifyInfo);
				
				model.addAttribute("vol", vol);
				model.addAttribute("cha", cha);
				model.addAttribute("non", non);
			}
		}else if("23".equals(pkgModel.getSelected_status()) || "24".equals(pkgModel.getSelected_status())){
			PkgModel pkgModelProgressData = new PkgModel();
			
			// progress read
			pkgModelProgressData = pkgService.popupProgressRead_Dev(pkgModel);

			model.addAttribute("PkgModelProgress", pkgModelProgressData);
		}
		
		pkgModelData.setContent(pkgModelData.getContent().replaceAll("\n","<br/>"));
		model.addAttribute("PkgModel", pkgModelData);

		return retUrl;
	}

}
