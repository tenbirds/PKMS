package com.pkms.board.report.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.board.report.model.SolutionReportModel;
import com.pkms.board.report.model.SolutionReportUserModel;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.system.model.SystemUserModel.SYSTEM_USER_CHARGE_GUBUN;
import com.pkms.board.report.service.SolutionReportServiceIf;
import com.pkms.sys.system.service.SystemServiceIf;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.common.session.service.SessionServiceIf;
import com.wings.util.DateUtil;
@Controller
public class SolutionReportController {

	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;
	
	@Resource(name = "SystemService")
	private SystemServiceIf systemService;
	
	@Resource(name = "SolutionReportService")
	private SolutionReportServiceIf solutionReportService;
	
	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;
	
	@RequestMapping(value = "/board/report/Solution_Report_ReadList.do")
	public String readList(SolutionReportModel SRModel, Model model) throws Exception{
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(SRModel);
		
		//검색기간 설정
		if(!StringUtils.hasLength(SRModel.getSearch_date_start()) || !StringUtils.hasLength(SRModel.getSearch_date_end())){
			SRModel.setSearch_date_start(DateUtil.formatDateByMonth(DateUtil.format(), -1));
			SRModel.setSearch_date_end(DateUtil.dateFormat());
		}
		
		List<?> solutionReportList = null;
		
		solutionReportList = solutionReportService.readList(SRModel);
		paginationInfo.setTotalRecordCount(SRModel.getTotalCount());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("SolutionReportModel", SRModel);
		model.addAttribute("solutionReportList", solutionReportList);
		return "/board/report/SolutionReport_ReadList";
	}
	
	@RequestMapping(value = "/board/report/Solution_Report_Read.do")
	public String read(SolutionReportModel SRModel, Model model) throws Exception{
		String retUrl = SRModel.getRetUrl();
		if ("".equals(retUrl)) {
			throw new Exception("read에서는 반드시 retUrl이 정의 되어야 합니다.");
		}
		
		SolutionReportModel solutionReportModel = new SolutionReportModel();
		solutionReportModel = SRModel;
		//수정 or view
		if (StringUtils.hasLength(SRModel.getSolution_report_seq())){
			solutionReportModel = solutionReportService.read(SRModel);
			solutionReportModel.setSession_user_group_id(SRModel.getSession_user_group_id());
			solutionReportModel.setSession_user_id(SRModel.getSession_user_id());
			
			SolutionReportUserModel SRUserModel = new SolutionReportUserModel();
			SRUserModel.setSolution_report_seq(SRModel.getSolution_report_seq());
			
			//등록되어 있는 승인자 불러오기
			solutionReportModel.setSolutionReportUserList(solutionReportService.srUserList(SRUserModel));
			
			//등록되어 있는 공지대상부서 불러오기
			solutionReportModel.setSolutionReportSosokList(solutionReportService.readSosokList(SRUserModel));
			
			if(retUrl.indexOf("View") > -1){
				//승인자 목록 수
				int cnt = 0;
				for(SolutionReportUserModel userCntMdl : solutionReportModel.getSolutionReportUserList()){
					if(userCntMdl.getUse_yn().equals("Y")){
						cnt++;
					}
				}
				solutionReportModel.setUser_cnt(cnt);
				//공지대상 부서 수
				int s_cnt=0;
				for(SolutionReportUserModel sosokCntMdl : solutionReportModel.getSolutionReportSosokList()){
					if(sosokCntMdl.getUse_yn().equals("Y")){
						s_cnt++;
					}
				}
				solutionReportModel.setSosok_cnt(s_cnt);
				
				//접속자가 해당 시스템 검증 권한인지 조회
				SRModel.setSystem_seq(solutionReportModel.getSystem_seq());
				solutionReportModel.setVu_yn(solutionReportService.readVuYn(SRModel));
			}
			
		//생성
		}else{
			
		}
		
		if(retUrl.indexOf("View") > -1) {
			solutionReportModel.setContent(solutionReportModel.getContent().replace("\n", "<br>").replace(" ", "&nbsp;"));
			List<?> commentList = null;
			commentList = solutionReportService.commentList(SRModel);
			model.addAttribute("CommentList", commentList);
		} else if(retUrl.indexOf("Read") > -1) {
			
		}
		
		
		model.addAttribute("SolutionReportModel", solutionReportModel);
		return retUrl;
	}
	
	@RequestMapping(value="/board/report/SolutionReportUser_Ajax_Read.do")
	public String solutionUserRead(SolutionReportModel SRModel, Model model) throws Exception{	
		SysModel sysModel = new SysModel();
		sysModel.setSystem_seq(SRModel.getSystem_seq());
		sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
		sysModel = systemService.readUsersAppliedToSystem(sysModel);

		SRModel.setSystemUserModelList(sysModel.getSystemUserList());
		
		SolutionReportUserModel SRUserModel = new SolutionReportUserModel();
		
		SRUserModel.setSystem_seq(SRModel.getSystem_seq());
		SRModel.setSolutionReportSosokList(solutionReportService.srSosokList(SRUserModel));
		
		model.addAttribute("SolutionReportModel", SRModel);
		return "/board/report/SolutionReportUser_Ajax_Read";
	}
	
	@RequestMapping(value="/board/report/Solution_Report_Ajax_Create.do")
	public String create(SolutionReportModel SRModel, Model model) throws Exception {
		solutionReportService.create(SRModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value="/board/qna/Solution_Report_Ajax_Update.do")
	public String update(SolutionReportModel SRModel, Model model) throws Exception {
		solutionReportService.update(SRModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value="/board/qna/Solution_Report_Ajax_Delete.do")
	public String delete(SolutionReportModel SRModel, Model model) throws Exception {
		solutionReportService.delete(SRModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value="/board/qna/Solution_Report_Ajax_Complete.do")
	public String complete(SolutionReportModel SRModel, Model model) throws Exception {
		solutionReportService.complete(SRModel);
		return ResultUtil.handleSuccessResult();
	}
	
	/*
	 * Comment create, delete
	 */
	@RequestMapping(value="/board/report/Solution_Report_Comment_Create.do")
	public String commentCreate(SolutionReportModel SRModel) throws Exception{
		solutionReportService.commentCreate(SRModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value="/board/report/Solution_Report_Comment_Delete.do")
	public String commentDelete(SolutionReportModel SRModel) throws Exception{
		solutionReportService.commentDelete(SRModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value="/board/report/Solution_Report_User_Yes.do")
	public String userYes(SolutionReportModel SRModel) throws Exception{
		solutionReportService.userYes(SRModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value="/board/report/Solution_Report_Confirm_Yes.do")
	public String sosokYes(SolutionReportModel SRModel) throws Exception{
		solutionReportService.sosokYes(SRModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/report/Solution_Report_Popup_MailList.do")
	public String roadMap(SolutionReportModel SRModel, Model model) throws Exception {
		SolutionReportUserModel SRUModel = new SolutionReportUserModel();
		SRUModel.setSystem_seq(SRModel.getSystem_seq());
		
		//댓글 저장 시 보낼 메일리스트
		SRModel.setSolutionReportMailList(solutionReportService.readMailList(SRUModel));
		model.addAttribute("SolutionReportModel", SRModel);
		return "/board/report/SolutionReport_Mail_Popup_Read";
	}

}
