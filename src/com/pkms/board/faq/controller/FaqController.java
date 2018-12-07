package com.pkms.board.faq.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.board.faq.model.FaqModel;
import com.pkms.board.faq.service.FaqServiceIf;
import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.wings.properties.service.PropertyServiceIf;
import com.pkms.common.util.ResultUtil;

@Controller
public class FaqController {
	@Resource(name = "FaqService")
	private FaqServiceIf faqService;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;
	
	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;
	
	@RequestMapping(value = "/board/faq/Faq_ReadList.do")
	public String readList(FaqModel faqModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(faqModel);

		/*
		 * 서비스로 부터 전체개수와 목록 정보 조회
		 */
		List<?> faqModelList = faqService.readList(faqModel);
		paginationInfo.setTotalRecordCount(faqModel.getTotalCount());

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("FaqModel", faqModel);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("faqModelList", faqModelList);
			
		return "/board/faq/Faq_ReadList";
	}
	
	@RequestMapping(value = "/board/faq/Faq_Read.do")
	public String read(FaqModel faqModel, Model model) throws Exception {

		String retUrl = faqModel.getRetUrl();
		if ("".equals(retUrl)) {
			throw new Exception("read에서는 반드시 retUrl이 정의 되어야 합니다.");
		}
		
		FaqModel faqModelData = new FaqModel();
	
		if (StringUtils.hasLength(faqModel.getFaq_seq())) {
			faqModelData = faqService.read(faqModel);
		}
		
		if(retUrl.indexOf("View") > -1) {
			//Faq key 데이터
			model.addAttribute("FaqModel", faqModel);
			
			if(faqModelData.getContent() != null){
				faqModelData.setContent(faqModelData.getContent().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}
			
			//Faq 조회 데이터
			model.addAttribute("FaqModelData", faqModelData);
			
		} else if(retUrl.indexOf("Read") > -1) {
			//Faq key + 조회 데이터
			if(null!=faqModelData){
				//View Read
				model.addAttribute("FaqModel", faqModelData);
			}else{
				//신규
				model.addAttribute("FaqModel", faqModel);
			}
		}
		return retUrl;
	}
	
	
	@RequestMapping(value = "/board/faq/Faq_Ajax_Create.do")
	public String create(FaqModel faqModel, Model model) throws Exception {
		faqService.create(faqModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/faq/Faq_Ajax_Update.do")
	public String update(FaqModel faqModel, Model model) throws Exception {
		faqService.update(faqModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/faq/Faq_Ajax_Delete.do")
	public String delete(FaqModel faqModel, Model model) throws Exception {
		faqService.delete(faqModel);
		return ResultUtil.handleSuccessResult();
	}
}
