package com.pkms.sys.bp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.usermg.bp.model.BpModel;
import com.pkms.usermg.bp.service.BpServiceIf;

@Controller
public class BpSystemController {
	
	@Resource(name = "BpService")
	private BpServiceIf bpService;

	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;
	
	@RequestMapping(value = "/usermg/bp/Bp_System_ReadList.do")
	public String readList(BpModel bpModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(bpModel);

		/*
		 * 서비스로 부터 전체개수와 목록 정보 조회
		 */
		List<BpModel> BpModelList = bpService.readListSystem(bpModel);
		paginationInfo.setTotalRecordCount(bpModel.getTotalCount());

		/*
		 * UI 모델 세팅
		 * 
		 */
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("BpModel", bpModel);
		model.addAttribute("BpModelList", BpModelList);

		return "/sys/bp/Bp_System_ReadList";
	}
	
}
