package com.pkms.verify_tem_mg.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.verify_tem_mg.model.VerifyTemModel;
import com.pkms.verify_tem_mg.service.VerifyTemMgServiceIf;
import com.pkms.verify_tem_mg.service.VerifyTemServiceIf;
import com.pkms.verify_tem_mg.dao.VerifyTemDao;

@Controller
public class VerifyTemMgController {
	@Resource(name = "VerifyTemMgService")
	private VerifyTemMgServiceIf verifyTemMgService;
	
	@Resource(name = "VerifyTemService")
	private VerifyTemServiceIf verifyTemService;
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;
	
	@Resource(name = "VerifyTemDao")
	private VerifyTemDao verifyTemDao;
	
	
	@RequestMapping(value = "/verify_tem_mg/verifyTempVol_ReadList.do")
	public String verifyTempVol_ReadList(VerifyTemModel verifyTemModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(verifyTemModel);
		verifyTemModel.setVerify_type("vol");
		List<VerifyTemModel> readList_verifyTem = verifyTemMgService.readList_verifyTem(verifyTemModel);
		paginationInfo.setTotalRecordCount(verifyTemModel.getTotalCount());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("verifyTemModel", verifyTemModel);
		model.addAttribute("readList_verifyTem", readList_verifyTem);
		
		return "/verifyTem_mg/verifyTemp4type_ReadList";
		
	}
	
	@RequestMapping(value = "/verify_tem_mg/verifyTempSec_ReadList.do")
	public String verifyTempSec_ReadList(VerifyTemModel verifyTemModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(verifyTemModel);
		verifyTemModel.setVerify_type("sec");
		List<VerifyTemModel> readList_verifyTem = verifyTemMgService.readList_verifyTem(verifyTemModel);
		paginationInfo.setTotalRecordCount(verifyTemModel.getTotalCount());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("verifyTemModel", verifyTemModel);
		model.addAttribute("readList_verifyTem", readList_verifyTem);
		
		return "/verifyTem_mg/verifyTemp4type_ReadList";
		
	}
	
	@RequestMapping(value = "/verify_tem_mg/verifyTempCha_ReadList.do")
	public String verifyTempCha_ReadList(VerifyTemModel verifyTemModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(verifyTemModel);
		verifyTemModel.setVerify_type("cha");
		List<VerifyTemModel> readList_verifyTem = verifyTemMgService.readList_verifyTem(verifyTemModel);
		paginationInfo.setTotalRecordCount(verifyTemModel.getTotalCount());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("verifyTemModel", verifyTemModel);
		model.addAttribute("readList_verifyTem", readList_verifyTem);
		
		return "/verifyTem_mg/verifyTemp4type_ReadList";
		
	}
	
	@RequestMapping(value = "/verify_tem_mg/verifyTempNon_ReadList.do")
	public String verifyTempNon_ReadList(VerifyTemModel verifyTemModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(verifyTemModel);
		verifyTemModel.setVerify_type("non");
		List<VerifyTemModel> readList_verifyTem = verifyTemMgService.readList_verifyTem(verifyTemModel);
		paginationInfo.setTotalRecordCount(verifyTemModel.getTotalCount());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("verifyTemModel", verifyTemModel);
		model.addAttribute("readList_verifyTem", readList_verifyTem);
		
		return "/verifyTem_mg/verifyTemp4type_ReadList";
		
	}
	
	@RequestMapping(value = "/verify_tem_mg/verifyTempMg_Read.do")
	public String verifyTempMg_Read(VerifyTemModel verifyTemModel, Model model) throws Exception {
		String used_tem = "";
		if(StringUtils.hasLength(verifyTemModel.getVerify_seq())){ //read
			List<VerifyTemModel> readList_veriTem = verifyTemMgService.read_verifyTem(verifyTemModel);
			
			//해당 탬플릿을 사용중인 PKG가 있는지확인 
			List<String> check4PKGusing_temp = verifyTemMgService.check4PKGusing_temp(verifyTemModel);
			if(check4PKGusing_temp.size()!=0){
				used_tem = "사용중";
			}
			
			//시스템이름갖고오기 
			verifyTemModel.setSystem_name(verifyTemMgService.read_sysName_verifyTem(readList_veriTem.get(0).getSystem_seq()));
			model.addAttribute("readList_veriTem", readList_veriTem);
			model.addAttribute("listSize", readList_veriTem.size());
			model.addAttribute("used_tem", used_tem);
		}else{ //create
		}
		
		verifyTemModel.setSearchCondition(verifyTemModel.getVerify_type());
		verifyTemModel.setPaging(false);
		List<VerifyTemModel> readList_quest = verifyTemDao.readList_quest(verifyTemModel);
		model.addAttribute("readList_quest", readList_quest);
		model.addAttribute("verifyTemModel", verifyTemModel);
		
		return "/verifyTem_mg/verifyTemp4type_Read";
		
	}
	
	
	@RequestMapping(value = "/verify_tem_mg/verifyTempMenuList_ReadList.do")
	public String verifyTempMenuList_ReadList(VerifyTemModel verifyTemModel, Model model) throws Exception {
		String used_tem = "";
		if(StringUtils.hasLength(verifyTemModel.getVerify_seq())){ //read
			List<VerifyTemModel> readList_veriTem = verifyTemMgService.read_verifyTem(verifyTemModel);
			
			//해당 탬플릿을 사용중인 PKG가 있는지확인 
			List<String> check4PKGusing_temp = verifyTemMgService.check4PKGusing_temp(verifyTemModel);
			if(check4PKGusing_temp.size()!=0){
				used_tem = "사용중";
			}
			
			//시스템이름갖고오기 
			verifyTemModel.setSystem_name(verifyTemMgService.read_sysName_verifyTem(readList_veriTem.get(0).getSystem_seq()));
			model.addAttribute("readList_veriTem", readList_veriTem);
			model.addAttribute("listSize", readList_veriTem.size());
			model.addAttribute("used_tem", used_tem);
		}else{ //create
		}
		
		verifyTemModel.setSearchCondition(verifyTemModel.getVerify_type());
		verifyTemModel.setPaging(false);
		List<VerifyTemModel> readList_quest = verifyTemDao.readList_quest(verifyTemModel);
		model.addAttribute("readList_quest", readList_quest);
		model.addAttribute("verifyTemModel", verifyTemModel);
		
		
		
		
		
		
		
		return "/verifyTem_mg/verifyTempMenuList_ReadList";
		
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/verify_tem_mg/verifyTempMg_Create.do")
	public String verifyTempMg_Create(VerifyTemModel verifyTemModel, Model model, HttpServletRequest request ) throws Exception {
		verifyTemModel = verifyTemMgService.create_verifyManage(verifyTemModel);
		String [] arrayQuest_seq = verifyTemModel.getQuest_seq_space().split(",");
		
		for(int i=0; i<arrayQuest_seq.length; i++){
			verifyTemModel.setQuest_seq(arrayQuest_seq[i]);
			verifyTemMgService.create_verifyTem(verifyTemModel);
		}
		
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/verify_tem_mg/verifyTempMg_Update.do")
	public String verifyTempMg_Update(VerifyTemModel verifyTemModel, Model model, HttpServletRequest request ) throws Exception {
		/*System.out.println("#$$#$#$#$#$#$#$#$##$");
		System.out.println(verifyTemModel.getVerify_seq());
		System.out.println(verifyTemModel.getVerify_ver());
		System.out.println(verifyTemModel.getQuest_seq_space());
		System.out.println(verifyTemModel.getVerify_title_ori());
		System.out.println("#$$#$#$#$#$#$#$#$##$");*/
		String [] arrayQuest_seq = verifyTemModel.getQuest_seq_space().split(",");
		
		if(verifyTemModel.getVerify_title_ori().equals("Y")){//제목 안바뀌고 버전만 up
			verifyTemModel.setVerify_ver(verifyTemMgService.getNewVer(verifyTemModel));
		}else{//제목바뀌고 새로 등록
			verifyTemModel.setVerify_ver(1);
		}
		verifyTemModel = verifyTemMgService.create_verifyManage(verifyTemModel);
		
		for(int i=0; i<arrayQuest_seq.length; i++){
			verifyTemModel.setQuest_seq(arrayQuest_seq[i]);
			verifyTemMgService.create_verifyTem(verifyTemModel);
		}
		
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/verify_tem_mg/verifyTempMg_Delete.do")
	public String verifyTempMg_Delete(VerifyTemModel verifyTemModel, Model model, HttpServletRequest request ) throws Exception {
		//검증완료된 페키지가 있을경우 삭제 불가능
		
		//검증완료되지 않은 페키지가 있을경우 삭제 가능
		verifyTemMgService.delete_verifyMG(verifyTemModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/verify_tem_mg/read_Ajax_verifyQue_List.do")
	public String read_Ajax_verifyQue_List(VerifyTemModel verifyTemModel, Model model) throws Exception {
		verifyTemModel.setSearchCondition(verifyTemModel.getVerify_type());
		List<VerifyTemModel> readList_quest = verifyTemService.readList_quest(verifyTemModel);
		model.addAttribute("readList_quest", readList_quest);
		model.addAttribute("verifyTemModel", verifyTemModel);
		
		return "/verifyTem_mg/verifyTemp4type_Ajax_queList";
		
	}
	
	

}
