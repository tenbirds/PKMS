package com.pkms.sample.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.pkmscode.service.PkmsCodeServiceIf;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.CodeUtil;
import com.pkms.common.util.ResultUtil;
import com.pkms.sample.model.SampleModel;
import com.pkms.sample.service.SampleServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 비지니스 로직은 해당 클래서에서 구현되면 안되고 필요한 서비스를 호출하고 페이지 URL만 리턴 하도록 한다.<br>
 * (페이지 URL 지정도 서비스에서 판단하는 방식 고려?)
 * 
 * @author : skywarker
 * @Date : 2012. 3. 9.
 * 
 */
@Controller
public class SampleController {

	@Resource(name = "SampleService")
	private SampleServiceIf sampleService;

//	@Resource(name = "PropertyService")
//	private PropertyServiceIf propertyService;

	@Resource(name = "PkmsCodeService")
	private PkmsCodeServiceIf pkmsCodeService;

	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;

	@RequestMapping(value = "/sample/Sample_Create.do")
	public String create(SampleModel sampleModel, Model model) throws Exception {
		sampleService.create(sampleModel);
		return ResultUtil.handleSuccessResult();
	}

	@RequestMapping(value = "/sample/Sample_Read.do")
	public String read(SampleModel sampleModel, Model model) throws Exception {
		String retUrl = sampleModel.getRetUrl();
		if ("".equals(retUrl)) {
			throw new Exception("read에서는 반드시 retUrl이 정의 되어야 합니다.");
		}

		SampleModel sampleModelData = new SampleModel();

		if (StringUtils.hasLength(sampleModel.getId())) {
			sampleModelData = sampleService.read(sampleModel);
		}

		/*
		 * 검색 조건, 페이징 세팅
		 */
		sampleModelData.setSearchCondition(sampleModel.getSearchCondition());
		sampleModelData.setSearchKeyword(sampleModel.getSearchKeyword());
		sampleModelData.setPageIndex(sampleModel.getPageIndex());
		
		// 사용여부 코드 값 세팅
		sampleModelData.setUseYnItems(CodeUtil.convertCodeModel(pkmsCodeService.readList("USE")));

		model.addAttribute("SampleModel", sampleModelData);

		return retUrl;
	}

	@RequestMapping(value = "/sample/Sample_ReadList.do")
	public String readList(SampleModel sampleModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(sampleModel);

		/*
		 * 서비스로 부터 전체개수와 목록 정보 조회
		 */
		List<?> sampleModelList = sampleService.readList(sampleModel);
		paginationInfo.setTotalRecordCount(sampleModel.getTotalCount());

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("SampleModel", sampleModel);
		model.addAttribute("SampleModelList", sampleModelList);

		return "/sample/Sample_ReadList";
	}

	@RequestMapping(value = "/sample/Sample_Update.do")
	public String update(SampleModel sampleModel, Model model) throws Exception {
		sampleService.update(sampleModel);
		return ResultUtil.handleSuccessResult();
	}

	@RequestMapping(value = "/sample/Sample_Delete.do")
	public String delete(SampleModel sampleModel, Model model) throws Exception {
		sampleService.delete(sampleModel);
		return ResultUtil.handleSuccessResult();
	}

}
