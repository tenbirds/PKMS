package com.pkms.main.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.pkms.board.notice.model.NoticeModel;
import com.pkms.board.notice.service.NoticeServiceIf;
import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.main.model.MainModel;
import com.pkms.newpncrmg.model.NewpncrModel;
import com.pkms.newpncrmg.service.NewpncrServiceIf;
import com.pkms.pkgmg.diary.model.DiaryModel;
import com.pkms.pkgmg.diary.service.DiaryServiceIf;
import com.pkms.pkgmg.mobile.service.MobileServiceIf;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.service.PkgServiceIf;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.UserAgentUtil;
import com.wings.util.DateUtil;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
/**
 * Main 페이지 controller 이다.
 * 
 * @author : 009
 * @Date : 2012. 4. 5.
 * 
 */
@Controller
public class MainController {
	
	static Logger logger = Logger.getLogger(MainController.class);

	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;

	@Resource(name = "DiaryService")
	private DiaryServiceIf diaryService;
	
	@Resource(name = "NewpncrService")
	private NewpncrServiceIf newpncrServie;

	@Resource(name = "NoticeService")
	private NoticeServiceIf noticeService;

	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;

	@Resource(name = "MobileService")
	private MobileServiceIf mobileService;

	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;
	
	@RequestMapping(value = "/Main.do")
	public String readList(HttpServletRequest request, Model model, PkgModel mdl) throws Exception {
		String serverName = propertyService.getString("ServerName");
		
		if("MOBILE".equals(serverName)) {
			main4Mobile(model, mdl);
			return "/pkgmg/mobile/Mobile_ReadList";
		}
		
		if (UserAgentUtil.isMobile(request)) {
			main4Mobile(model, mdl);
			return "/pkgmg/mobile/Mobile_ReadList";
		}
		
//		if(request.getHeader("REFERER").indexOf("http://tnetproxy.sktelecom.com:8081") > -1){
//		if(request.getHeader("SM_USER") != null){
//			HttpSession session = request.getSession();
//		    
//			return "redirect:http://pkms.sktelecom.com/Tnet.do?id=" + session.getId();
//		}

		// PKG 현황 목록 조회
		PkgModel pkgModel = new PkgModel();
		pagingService.getPaginationInfo(pkgModel);
		pkgModel.setStatusCondition("MAIN");//메인에 필요한 상태만
		List<PkgModel> pkgModelList = (List<PkgModel>) pkgService.readList(pkgModel);

		// 신규/PN/CR 목록 조회
		NewpncrModel newpncrModel = new NewpncrModel();
		pagingService.getPaginationInfo(newpncrModel);
		newpncrModel.setMain(true);
		List<?> newpncrModelList = newpncrServie.readList(newpncrModel);

		// 공지사항 목록 조회
		NoticeModel noticeModel = new NoticeModel();
		pagingService.getPaginationInfo(noticeModel);
		List<?> noticeModelList = noticeService.readList(noticeModel);

		List<?> noticePopupModelList = noticeService.readList4Main(noticeModel);
		
		
		// Diary 조회
		/*DiaryModel diaryModel = new DiaryModel();
		diaryModel = diaryService.setSearchCondition(diaryModel);
		diaryService.readList(diaryModel);*/
		
		MainModel mainModel = new MainModel();
		mainModel.setSession_user_id(pkgModel.getSession_user_id());
		mainModel.setSession_user_name(pkgModel.getSession_user_name());
		mainModel.setSession_user_type(pkgModel.getSession_user_type());
//		mainModel.setDiaryScript(diaryModel.getDiaryScript());
//		mainModel.setUserDiaryCondition(diaryModel.getUserDiaryCondition());
//		mainModel.setDiaryItem(diaryModel.getDiaryItem());
		
		model.addAttribute("MainModel", mainModel);
		model.addAttribute("PkgModelList", pkgModelList);
		model.addAttribute("NewpncrModelList", newpncrModelList);
		model.addAttribute("NoticeModelList", noticeModelList);
		model.addAttribute("NoticePopupModelList", noticePopupModelList);

		return "/main/Main";
	}

	@RequestMapping(value = "/M.do")
	public String read(HttpServletRequest request, Model model, PkgModel mdl) throws Exception {
			
		if (UserAgentUtil.isMobile(request)) {
			main4Mobile(model, mdl);
			return "/pkgmg/mobile/Mobile_ReadList";
		}
		return "redirect:/Main.do";
	}

	private void main4Mobile(Model model, PkgModel mdl) throws Exception {
		PkgModel pkgModel = new PkgModel();
		//사용자 편의를 위한 현재까지의 날짜 설정
		if(!StringUtils.hasLength(mdl.getEndD()) || !StringUtils.hasLength(mdl.getStartD())){
			mdl.setEndD(DateUtil.dateFormat());
			mdl.setStartD(DateUtil.formatDateByMonth(DateUtil.format(), -3));
		}
		
		List<PkgModel> pkgModelList = mobileService.readList(mdl);
		model.addAttribute("PkgModel", pkgModel);
		model.addAttribute("mdl", mdl);
		model.addAttribute("PkgModelList", pkgModelList);
	}
	
	@RequestMapping(value = "/Tnet.do")
	public String tnet(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
	    String id = request.getParameter("id");
		
		Cookie cookie = new Cookie("JSESSIONID", id);
		cookie.setMaxAge(60 * 60);
		response.addCookie(cookie);
		
		return "redirect:/Main.do";
	}
	
	@RequestMapping(value = "/Work_Popup_List.do")
	public String workPopup(HttpServletRequest request, PkgModel pkgModel, Model model) throws Exception {
		List<String> authList = sessionService.readAuth();
		
		//Spring Security 권한 체크 
		for(String auth : authList){
			// 관리자 권한인 경우
			if("ROLE_ADMIN".equals(auth)) {
				pkgModel.setGranted("ADMIN");
				List<PkgModel> workLimitList = (List<PkgModel>) pkgService.workLimitList(pkgModel);
				model.addAttribute("workLimitList", workLimitList);
			}
		}
		
		String url_gubun = "";
		
		url_gubun = request.getParameter("url_gubun");
		
		List<PkgModel> workCntList = (List<PkgModel>) pkgService.workCntList(pkgModel);
		List<PkgModel> workPkgList = (List<PkgModel>) pkgService.workPkgList(pkgModel);
		
		model.addAttribute("workCntList", workCntList);
		model.addAttribute("workPkgList", workPkgList);
		model.addAttribute("granted", pkgModel.getGranted());
		model.addAttribute("url_gubun", url_gubun);
		
		return "/common/Work_Popup";
	}
	
	@RequestMapping(value = "/Manual_Popup_Notice.do")
	public String manualPopup(HttpServletRequest request, PkgModel pkgModel, Model model) throws Exception {
		/*List<PkgModel> helloList = (List<PkgModel>) pkgService.helloList(pkgModel);
		BufferedWriter out = new BufferedWriter(new FileWriter("C:/Users/Jun/Desktop/result.txt"));
		for(PkgModel aa : helloList){
			String bb = "";
			if(aa.getOrd().equals("0")){
				if(aa.getContent() != null){					
					pkgModel.setContent_0(aa.getContent().replace("\n",""));
				}
			}else if(aa.getOrd().equals("1")){
				if(aa.getContent() != null){
					pkgModel.setContent_1(aa.getContent().replace("\n",""));
				}
			}else if(aa.getOrd().equals("2")){
				if(aa.getContent() != null){
					pkgModel.setContent_2(aa.getContent().replace("\n",""));
				}
			}else if(aa.getOrd().equals("3")){
				if(aa.getContent() != null){
				pkgModel.setContent_3(aa.getContent().replace("\n",""));
				}
			}else if(aa.getOrd().equals("4")){
				if(aa.getContent() != null){
				pkgModel.setContent_4(aa.getContent().replace("\n",""));
				}
			}else if(aa.getOrd().equals("5")){
				if(aa.getContent() != null){pkgModel.setContent_5(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("6")){
				if(aa.getContent() != null){pkgModel.setContent_6(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("7")){
				if(aa.getContent() != null){pkgModel.setContent_7(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("8")){
				if(aa.getContent() != null){pkgModel.setContent_8(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("9")){
				if(aa.getContent() != null){pkgModel.setContent_9(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("10")){
				if(aa.getContent() != null){pkgModel.setContent_10(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("11")){
				if(aa.getContent() != null){pkgModel.setContent_11(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("12")){
				if(aa.getContent() != null){pkgModel.setContent_12(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("13")){
				if(aa.getContent() != null){pkgModel.setContent_13(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("14")){
				if(aa.getContent() != null){pkgModel.setContent_14(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("15")){
				if(aa.getContent() != null){pkgModel.setContent_15(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("16")){
				if(aa.getContent() != null){pkgModel.setContent_16(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("17")){
				if(aa.getContent() != null){pkgModel.setContent_17(aa.getContent().replace("\r\n",""));}
			}else if(aa.getOrd().equals("18")){
				if(aa.getContent() != null){pkgModel.setContent_18(aa.getContent().replace("\r\n",""));}
			}
			if(aa.getOrd().equals("1")){
				if(aa.getNew_pn_cr_gubun() != null){pkgModel.setContent_19(aa.getNew_pn_cr_gubun());}
				bb = pkgModel.getContent_0()+" ▩ "+pkgModel.getContent_1()+" ▩ "+pkgModel.getContent_2()+" ▩ "+pkgModel.getContent_3()
						+" ▩ "+pkgModel.getContent_4()+" ▩ "+pkgModel.getContent_5()+" ▩ "+pkgModel.getContent_6()+" ▩ "+pkgModel.getContent_7()+" ▩ "+pkgModel.getContent_8()
						+" ▩ "+pkgModel.getContent_9()+" ▩ "+pkgModel.getContent_10()+" ▩ "+pkgModel.getContent_11()+" ▩ "+pkgModel.getContent_12()
						+" ▩ "+pkgModel.getContent_13()+" ▩ "+pkgModel.getContent_14()+" ▩ "+pkgModel.getContent_15()+" ▩ "+pkgModel.getContent_16()
						+" ▩ "+pkgModel.getContent_17()+" ▩ "+pkgModel.getContent_18()+" ▩ "+pkgModel.getContent_19();
				out.write(bb);
				out.newLine();				
			}
		}
		out.close();
		System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
		System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
		if(pkgModel.getPkgContetlList() != null){
			throw new Exception("error.biz.끝끝끝");
		}*/
		return "/main/Manual_Popup";
	}
	
	@RequestMapping(value = "/WorkLimitSave.do")
	public String workLimitSave(PkgModel pkgModel, Model model) throws Exception {
		pkgService.workLimitSave(pkgModel);
		return ResultUtil.handleSuccessResultParam(model, "저장 되었습니다.", "");
	}
	
}
