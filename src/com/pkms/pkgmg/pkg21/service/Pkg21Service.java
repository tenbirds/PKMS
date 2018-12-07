package com.pkms.pkgmg.pkg21.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.pkms.common.newmail.model.NewMailModel;
import com.pkms.common.newmail.service.NewMailServiceIf;
import com.pkms.common.workSystem.model.WorkSystemModel;
import com.pkms.common.workSystem.service.WorkSystemServiceIf;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.service.PkgEquipmentServiceIf;
import com.pkms.pkgmg.pkg21.model.Pkg21FileModel;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.pkgmg.pkg21.controller.Pkg21Controller;
import com.pkms.pkgmg.pkg21.dao.Pkg21DAO;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.equipment.service.EquipmentServiceIf;
import com.pkms.sys.system.model.SystemFileModel;

import com.wings.util.WingsStringUtil;

@Service("Pkg21Service")
public class Pkg21Service implements Pkg21ServiceIf {
	@Resource(name="Pkg21DAO")
	private Pkg21DAO pkg21DAO;
	
	@Resource(name = "EquipmentService")
	private EquipmentServiceIf equipmentService;
	
	@Resource(name = "PkgEquipmentService")
	private PkgEquipmentServiceIf pkgEquipmentService;
	
	@Resource(name = "NewMailService")
	protected NewMailServiceIf newMailService;
	
	@Resource(name = "WorkSystemService")
	private WorkSystemServiceIf workSystemService;
	
	static Logger logger = Logger.getLogger(Pkg21Controller.class);
	
	@Override
	public List<Pkg21Model> readList(Pkg21Model pkg21Model) throws Exception {
		pkg21Model.setTotalCount(pkg21DAO.readTotalCount(pkg21Model));
		
		List<Pkg21Model> resultList = (List<Pkg21Model>) pkg21DAO.readList(pkg21Model);
		return resultList;
	}
	
	@Override
	public Pkg21Model read(Pkg21Model pkg21Model) throws Exception {
		return pkg21DAO.read(pkg21Model);
	}
	
	@Override
	public void create(Pkg21Model pkg21Model, HttpServletRequest request) throws Exception {
		if("Y".equals(pkg21Model.getPatch_yn())){
			pkg21Model.setTitle("Patch");
		}else{
			if("F".equals(pkg21Model.getVer_gubun())){
				pkg21Model.setTitle("Full");				
			}else{
				pkg21Model.setTitle("Partial");
			}
		}
		
		pkg21Model.setPkg_seq(pkg21DAO.create(pkg21Model));
		
//mail send start ------------------------------------------------------------------------------------------------
		ArrayList<String> gubun =  new ArrayList<String>() ;
		if("N".equals(pkg21Model.getDev_yn())) {//cvt(상용) 검증 요청 생략
			gubun.add("DV");	
			if("Y".equals(pkg21Model.getVol_yn()) ){
				gubun.add("VO");//용량검증
			}
		}else {
				gubun.add("VU");
				gubun.add("DV");	
			if("Y".equals(pkg21Model.getVol_yn()) ){
				gubun.add("VO");//용량검증
			}
		}
		
		
		/*if (request.getRequestURL().indexOf("localhost") == -1) {*/
			NewMailModel inputModel = new NewMailModel();  // 메일 내용
			
			String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
			String lastOne = bits[bits.length-1];
			inputModel.setMailTitle("SVT 계획수립 (PKG명 : "+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+")"); // 제목 pkg21Model.getSystem_name()  
			String content ="";
			content = newMailService.genrerateString(null ,pkg21Model , "1", ""); // SVT 계획수립 :1
			inputModel.setMailContent(content); // 내용
			
			newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);
		/*}*/
//mail send end  -----------------------------------------------------------------------------------------------------------------------------------------------------		
	}
	
	@Override
	public void update(Pkg21Model pkg21Model) throws Exception {
		if("Y".equals(pkg21Model.getPatch_yn())){
			pkg21Model.setTitle("Patch");
		}else{
			if("F".equals(pkg21Model.getVer_gubun())){
				pkg21Model.setTitle("Full");				
			}else{
				pkg21Model.setTitle("Partial");
			}
		}
		pkg21DAO.update(pkg21Model);
		
		
		//mail send start ------------------------------------------------------------------------------------------------
				ArrayList<String> gubun =  new ArrayList<String>() ;
				if("N".equals(pkg21Model.getDev_yn())) {//cvt(상용) 검증 요청 생략
					gubun.add("DV");	
					if("Y".equals(pkg21Model.getVol_yn()) ){
						gubun.add("VO");//용량검증
					}
				}else {
						gubun.add("VU");
						gubun.add("DV");	
					if("Y".equals(pkg21Model.getVol_yn()) ){
						gubun.add("VO");//용량검증
					}
				}
				NewMailModel inputModel = new NewMailModel();  // 메일 내용
//				inputModel.setMailTitle("SVT 계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
				String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
				String lastOne = bits[bits.length-1];
				inputModel.setMailTitle("SVT 계획수립 (PKG명 : "+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+")"); // 제목 pkg21Model.getSystem_name()  
				String content ="";
				content = newMailService.genrerateString(null ,pkg21Model , "1", ""); // SVT 계획수립 :1				
				inputModel.setMailContent(content); // 내용
				
				newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);
		//mail send end  -----------------------------------------------------------------------------------------------------------------------------------------------------		
	}
	
	@Override
	public void pkg_delete(Pkg21Model pkg21Model) throws Exception {
		pkg21DAO.pkg_delete(pkg21Model);
	}
	
	@Override
	public void pkg_status_update(Pkg21Model pkg21Model) throws Exception {
		pkg21DAO.pkg_status_update(pkg21Model);
	}
	
	@Override
	public void pkg_dev_status_update(Pkg21Model pkg21Model) throws Exception {
		pkg21DAO.pkg_dev_status_update(pkg21Model);
	}
	
	@Override
	public void pkg_verify_update(Pkg21Model pkg21Model) throws Exception {
		pkg21DAO.pkg_verify_update(pkg21Model);
	}
	
	@Override
	public void pkg_cha_yn_update(Pkg21Model pkg21Model) throws Exception {
		pkg21DAO.pkg_cha_yn_update(pkg21Model);
	}
	
	@Override
	public Pkg21Model peTypeRead(Pkg21Model pkg21Model) throws Exception {
		return pkg21DAO.peTypeRead(pkg21Model);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PkgEquipmentModel> getPkgEquipment(Pkg21Model pkg21Model, String work_gubun) throws Exception {
		List<PkgEquipmentModel> pkgEquipmentModelList = new ArrayList<PkgEquipmentModel>();
		PkgEquipmentModel pkgEquipmentModel = null;
		List<PkgEquipmentModel> sEModelList = null;
		
		SysModel sysModel = new SysModel();
		sysModel.setSystem_seq(pkg21Model.getSystem_seq());
		
		List<SysModel> equiList = (List<SysModel>)equipmentService.readList(sysModel);
		
		PkgEquipmentModel pkgEModel = new PkgEquipmentModel();
		pkgEModel.setPkg_seq(pkg21Model.getPkg_seq());
		
		if("E".equals(work_gubun)) {
			pkgEModel.setWork_gubun("S");
		} else if("S".equals(work_gubun)) {
			pkgEModel.setWork_gubun("E");
		}
		sEModelList = pkgEquipmentService.read21List(pkgEModel);
		
		pkgEModel.setWork_gubun(work_gubun);
		List<PkgEquipmentModel> pkgEModelList = pkgEquipmentService.read21List(pkgEModel);
		
		boolean isExist = false;
		MainLoop:
		for(SysModel equiModel : equiList) {
			//초도인 경우: 확대적용 장비 제외, 확대인 경우: 초도적용 장비는 제외
			for(PkgEquipmentModel sEModel : sEModelList) {
				if(equiModel.getEquipment_seq().equals(sEModel.getEquipment_seq())) {
					continue MainLoop;
				}
			}

			for(PkgEquipmentModel peModel : pkgEModelList) {
				if(equiModel.getEquipment_seq().equals(peModel.getEquipment_seq())) {
					pkgEquipmentModel = (PkgEquipmentModel) peModel.clone();
					isExist = true;
					break;
				}
			}
			
			if(!isExist) {
				pkgEquipmentModel = new PkgEquipmentModel();
				pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());					
				pkgEquipmentModel.setEquipment_seq(equiModel.getEquipment_seq());
			}
			
			pkgEquipmentModel.setIdc_name(equiModel.getIdc_name());
			pkgEquipmentModel.setEquipment_name(equiModel.getName());
			pkgEquipmentModel.setTeam_name(equiModel.getTeam_name());
			pkgEquipmentModel.setTeam_code(equiModel.getTeam_code());
			pkgEquipmentModel.setService_area(equiModel.getService_area());
			
			pkgEquipmentModelList.add(pkgEquipmentModel);
			
			isExist = false;
		}
		
		return pkgEquipmentModelList;
	}
	
	@Override
	public List<PkgEquipmentModel> getPkgEquipment4E(Pkg21Model pkg21Model, String work_gubun) throws Exception {
		List<PkgEquipmentModel> sEModelList = null;
		
		//일정수립한 장비목록
		PkgEquipmentModel pkgEModel = new PkgEquipmentModel();
		pkgEModel.setPkg_seq(pkg21Model.getPkg_seq());

		pkgEModel.setWork_gubun("S");
		sEModelList = pkgEquipmentService.read21List(pkgEModel);
		return sEModelList;
	}
	
	@Override
	public List<Pkg21Model> getPkgVol(Pkg21Model pkg21Model) throws Exception {
		List<Pkg21Model> questList = null;
		List<Pkg21Model> valueList = null;
		List<Pkg21Model> returnList = new ArrayList<Pkg21Model>();
		Pkg21Model result = null;
		
		questList = pkg21DAO.questList(pkg21Model);
		valueList = pkg21DAO.valueList(pkg21Model);
		
		for(Pkg21Model qModel : questList){
			result = new Pkg21Model();
			result.setVol_no(qModel.getVol_no());
			result.setChk_seq(qModel.getChk_seq());
			result.setTitle(qModel.getTitle());

			if(valueList != null){
				for(Pkg21Model vModel : valueList){					
					if(vModel.getChk_seq().equals(qModel.getChk_seq())){
						result.setChk_result(vModel.getChk_result());
					}
				}
			}
			returnList.add(result);
		}

		return returnList;
	}
	
	@Override
	public void pkg_vol_create(Pkg21Model pkg21Model) throws Exception {
		Pkg21Model pkg21 = new Pkg21Model();

		String[] chkSeqs = pkg21Model.getChk_seqs();
		String[] chk_results = pkg21Model.getChk_results();
		
		String[] cchk_results = WingsStringUtil.getNotNullStringArray(chk_results);
		
		for(int i = 0; i < chkSeqs.length; i++) {
			pkg21 = new Pkg21Model();
			pkg21.setPkg_seq(pkg21Model.getPkg_seq());
			pkg21.setChk_seq(chkSeqs[i]);
			pkg21.setChk_result(cchk_results[i]);

			pkg21DAO.pkg_vol_create(pkg21);
		}
	}
	
	@Override
	public void pkg_vol_update(Pkg21Model pkg21Model) throws Exception {
		Pkg21Model pkg21 = new Pkg21Model();

		String[] chkSeqs = pkg21Model.getChk_seqs();
		String[] chk_results = pkg21Model.getChk_results();
		
		String[] cchk_results = WingsStringUtil.getNotNullStringArray(chk_results);
		
		for(int i = 0; i < chkSeqs.length; i++) {
			pkg21 = new Pkg21Model();
			pkg21.setPkg_seq(pkg21Model.getPkg_seq());
			pkg21.setChk_seq(chkSeqs[i]);
			pkg21.setChk_result(cchk_results[i]);

			pkg21DAO.pkg_vol_update(pkg21);
		}
		
	}
	
	@Override
	public List<Pkg21FileModel> pkg_result_list(Pkg21Model pkg21Model) throws Exception {
		return (List<Pkg21FileModel>) pkg21DAO.pkg_result_list(pkg21Model);
	}
	
	@Override
	public void pkg_status_delete(Pkg21Model pkg21Model) throws Exception {
		pkg21DAO.pkg_status_delete(pkg21Model);
	}
	
	@Override
	public void pkg_status_delete_like(Pkg21Model pkg21Model) throws Exception {
		pkg21DAO.pkg_status_delete_like(pkg21Model);
	}
	
	@Override
	public int readEQCntLeft(Pkg21Model pkg21Model) throws Exception {
		return pkg21DAO.readEQCntLeft(pkg21Model);
	}

	@Override
	public List<SystemFileModel> sysFileList(SystemFileModel systemFileModel) throws Exception {
		return pkg21DAO.sysFileList(systemFileModel);
	}
	
	/*@Override
	public void tangoWork(Pkg21Model pkg21Model, String work_gubun) throws Exception {
		WorkSystemModel tangoMainMdl = new WorkSystemModel();
		
		//기본정보 중 없는 것은 여기에서
		Pkg21Model p21Model = new Pkg21Model();
		p21Model = pkg21DAO.read(pkg21Model);
		
		tangoMainMdl.setNo(pkg21Model.getPkg_seq());
		
		String total_title = "";
		String roaming = "";
		String area = "";
		String ver = "";
		String eq_name = "";
		String bypass_traffic = "";
		String full_system_name = "";
		
		ver = p21Model.getVer();
		if("상".equals(p21Model.getRoaming_link())){
			roaming = "[로밍연동-상]";
		}else if("중".equals(p21Model.getRoaming_link())){
			roaming = "[로밍연동-중]";
		}else if("하".equals(p21Model.getRoaming_link())){
			roaming = "[로밍연동-하]";
		}
		
		if("Y".equals(p21Model.getBypass_traffic())){
			bypass_traffic = "[우회소통]";
		}
		
		tangoMainMdl.setMaster_file_id(pkg21Model.getMaster_file_id()); //첨부파일 연동
		tangoMainMdl.setImpo("Y"); //pkms_main
		tangoMainMdl.setBackground_target(bypass_traffic + "PKMS의 작업절차서 파일 참조"); //pkms_main
		tangoMainMdl.setJob_bunya("3"); //pkms_main
		tangoMainMdl.setJob_gubun1("SW 작업"); //pkms_main
		
		if("F".equals(p21Model.getVer_gubun())){ //전체
			tangoMainMdl.setJob_gubun2("PKG적용-Full");
		}else if("P".equals(p21Model.getVer_gubun())){ //부분
			tangoMainMdl.setJob_gubun2("PKG적용-Partial");
		}else{ //패치
			tangoMainMdl.setJob_gubun2("PKG적용-Partial");
		}
		
		SimpleDateFormat DateFormat = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		Date current = new Date();
		String c_date = DateFormat.format (current);
		
		tangoMainMdl.setIns_date(c_date);//pkms_main
		tangoMainMdl.setC_date(c_date);//pkms_sub
		tangoMainMdl.setMaster_date_s(c_date);//pkms_sub

		tangoMainMdl.setGojang_step("원복");//pkms_main
		tangoMainMdl.setWork_effect("소통저조");//pkms_main
		tangoMainMdl.setWork_rank("L");//pkms_main
		
		tangoMainMdl.setWork_name(pkg21Model.getSession_user_name());	//pkms_sub

		tangoMainMdl.setIns_name(pkg21Model.getSession_user_name()); //pkms_main
		tangoMainMdl.setIns_id(pkg21Model.getSession_user_id()); //pkms_main
		tangoMainMdl.setIns_sosok(pkg21Model.getSession_user_group_name()); //pkms_main
		tangoMainMdl.setIns_sosok_code(pkg21Model.getSession_user_group_id()); //pkms_sub	
		tangoMainMdl.setWork_phone(pkg21Model.getSession_user_mobile_phone());
		tangoMainMdl.setWork_result_date_s(c_date); //pkms_sub
		tangoMainMdl.setSystem_seq(pkg21Model.getSystem_seq());
		tangoMainMdl.setWork_gubun(work_gubun);
		
		WorkSystemModel sysUserMdl = new WorkSystemModel();
		sysUserMdl.setSystem_seq(pkg21Model.getSystem_seq());
		if(null != workSystemService.read_Sys_User_Info(sysUserMdl)){
			sysUserMdl = workSystemService.read_Sys_User_Info(sysUserMdl);
			tangoMainMdl.setJob_man(sysUserMdl.getJob_man());
			tangoMainMdl.setJob_man_post(sysUserMdl.getJob_man_post());
			tangoMainMdl.setTarget_system(sysUserMdl.getTarget_system());
			tangoMainMdl.setWork_summary(sysUserMdl.getTarget_system());			

			if("S".equals(work_gubun)) {
				sysUserMdl.setPkg_seq(pkg21Model.getPkg_seq());
				sysUserMdl.setS_status("131");
				if(null != workSystemService.readIns_Data_Pkg21(sysUserMdl)){					
					sysUserMdl = workSystemService.readIns_Data_Pkg21(sysUserMdl);
					tangoMainMdl.setWork_name(sysUserMdl.getIns_name());
					tangoMainMdl.setIns_name(sysUserMdl.getIns_name());
					tangoMainMdl.setIns_id(sysUserMdl.getIns_id());
					tangoMainMdl.setIns_sosok(sysUserMdl.getIns_sosok());
					tangoMainMdl.setIns_sosok_code(sysUserMdl.getIns_sosok_code());
				}
			}
		}
		
		
		//리스트에 있는 여러개의 장비 이름을 가져과 콤마로 구분해서 장비이름 넣기 SYSTEM_NAME
		String system_name = "";
		
		//pkgEquipmentModelList에 영향있는 변수들 if문 위에서 선언 1125
		String w_sTime ="";
		String w_eTime ="";
		
		int seq = 0;
		int am_seq = 0;
		int pe_cnt = 0;
		int am_cnt = 0;
		int pm_cnt = 0;
		
		WorkSystemModel WorkSysModel = new WorkSystemModel();
		WorkSysModel.setPkg_seq(pkg21Model.getPkg_seq());
		List<WorkSystemModel> noCreateList = workSystemService.noCreateList(WorkSysModel);
		String[] eq_seq = null;
		int nc_cnt=0;
		if(null != noCreateList){
			eq_seq = new String[noCreateList.size()];
			for(WorkSystemModel nc : noCreateList){
				eq_seq[nc_cnt] = nc.getEquipment_seq();
				nc_cnt++;
			}
		}
		
		if(nc_cnt > 0){
			WorkSysModel.setEquipment_seqs(eq_seq);
		}
		WorkSysModel.setWork_gubun(work_gubun);
		
		List<WorkSystemModel> teamWorkDateList = workSystemService.read21TeamWorkDate(WorkSysModel);
		if(teamWorkDateList != null){
			for(WorkSystemModel workDate : teamWorkDateList){
				WorkSysModel = workSystemService.read_SeqMax_Main(WorkSysModel);
				seq = WorkSysModel.getSeq(); //pkms_main

				String team_code= "0000" + workDate.getMaster_team_code();				
				seq++;
				am_cnt=0;
				pe_cnt=0;
				pm_cnt=0;
				List<String> areaList = new ArrayList<String>();
				List<String> areaSetList = new ArrayList<String>();
				
				String[] checkSeqs = pkg21Model.getCheck_seqs_e();
				String[] start_dates = pkg21Model.getStart_dates();
				String[] end_dates = pkg21Model.getEnd_dates();
				String[] start_times1 = pkg21Model.getStart_times1();
				String[] start_times2 = pkg21Model.getStart_times2();
				String[] end_times1 = pkg21Model.getEnd_times1();
				String[] end_times2 = pkg21Model.getEnd_times2();
				String[] ampms = pkg21Model.getAmpms();
				
				String[] cstart_dates = WingsStringUtil.getNotNullStringArray(start_dates);
				String[] cend_dates = WingsStringUtil.getNotNullStringArray(end_dates);
				String[] cstart_times1 = WingsStringUtil.getNotNullStringArray(start_times1);
				String[] cstart_times2 = WingsStringUtil.getNotNullStringArray(start_times2);
				String[] cend_times1 = WingsStringUtil.getNotNullStringArray(end_times1);
				String[] cend_times2 = WingsStringUtil.getNotNullStringArray(end_times2);
				String[] campms = WingsStringUtil.getNotNullStringArray(ampms);
				for(int i = 0; i < checkSeqs.length; i++) {
					if(!"N".equals(checkSeqs[i])){
						if(workDate.getStart_date().equals(cstart_dates[i]) &&
						   workDate.getEnd_date().equals(cend_dates[i]) &&
						   workDate.getStart_time1().equals(cstart_times1[i]) &&
						   workDate.getEnd_time1().equals(cend_times1[i]) &&
						   workDate.getAmpm().equals(campms[i])
							){
							PkgEquipmentModel pkgEqModel = new PkgEquipmentModel();
							pkgEqModel.setEquipment_seq(checkSeqs[i]);
							pkgEqModel = pkgEquipmentService.readName(pkgEqModel);
							if("주간".equals(campms[i])){
								am_cnt++;
								if(am_cnt == 1){
									am_seq = seq + 1;
									eq_name = pkgEqModel.getEquipment_name();
									full_system_name = pkgEqModel.getEquipment_name();
								}else if(am_cnt == 2){
									eq_name = eq_name + " 등";
								}
								if(am_cnt > 1){
									full_system_name = full_system_name + ";" + pkgEqModel.getEquipment_name();
								}
							}else{
								pm_cnt++;
								if(pm_cnt == 1){
									eq_name = pkgEqModel.getEquipment_name();
									full_system_name = pkgEqModel.getEquipment_name();
								}else if(pm_cnt == 2){
									eq_name = eq_name + " 등";
								}
								if(pm_cnt > 1){
									full_system_name = full_system_name + ";" + pkgEqModel.getEquipment_name();									
								}
							}
							
							w_sTime = cstart_dates[i] + " " + cstart_times1[i] + ":" + cstart_times2[i];
							w_eTime = cend_dates[i] + " " + cend_times1[i] + ":" + cend_times2[i];
							
							logger.debug("checkSeqs[i] : "+checkSeqs[i]);
							logger.debug("w_sTime : "+w_sTime);
							
							//시스템 이름
							if(pe_cnt==0){
								system_name = "["+ pkgEqModel.getIdc_name() +"]"+ pkgEqModel.getEquipment_name();				
							}else{
								system_name = system_name +","+"["+ pkgEqModel.getIdc_name() +"]"+ pkgEqModel.getEquipment_name();
							}
							
							areaList.add(pkgEqModel.getIdc_name());
							
							pe_cnt++;
								
							//수정위한 보조 테이블 입력
							WorkSysModel.setSeq(seq);
							WorkSysModel.setEquipment_seq(checkSeqs[i]);
							WorkSysModel.setStart_date(cstart_dates[i]);
							WorkSysModel.setEnd_date(cend_dates[i]);
							WorkSysModel.setStart_time1(cstart_times1[i]);
							WorkSysModel.setStart_time2(cstart_times2[i]);
							WorkSysModel.setEnd_time1(cend_times1[i]);
							WorkSysModel.setEnd_time2(cend_times2[i]);
							WorkSysModel.setAmpm(campms[i]);
							
							workSystemService.create_21work_equipment(WorkSysModel);
							
							if(am_cnt > 0){
								WorkSysModel.setSeq(am_seq);
								WorkSysModel.setEquipment_seq(checkSeqs[i]);
								WorkSysModel.setStart_date(cstart_dates[i]);
								WorkSysModel.setEnd_date(cend_dates[i]);
								WorkSysModel.setStart_time1(cstart_times1[i]);
								WorkSysModel.setStart_time2(cstart_times2[i]);
								WorkSysModel.setEnd_time1(cend_times1[i]);
								WorkSysModel.setEnd_time2(cend_times2[i]);
								WorkSysModel.setAmpm(campms[i]);
								
								workSystemService.create_21work_equipment(WorkSysModel);
							}
						}
					}
				}
				logger.debug("w_sTime : "+w_sTime);
				sysUserMdl.setMaster_team_code(workDate.getMaster_team_code());

				tangoMainMdl.setSeq(seq);	//SEQ
				tangoMainMdl.setTeam_code(team_code);
				
				tangoMainMdl.setWork_plandate_s(w_sTime); // 작업시작시간 WORK_PLANDATE_S  2013-03-25 02:00
				tangoMainMdl.setWork_plandate_e(w_eTime); // 작업종료시간 WORK_PLANDATE_E  2013-03-25 07:00

				tangoMainMdl.setSystem_name(system_name); // 장비 이름 SYSTEM_NAME
				
				tangoMainMdl.setWork_sosok(workDate.getWork_sosok());
				for(String str : areaList){
					if(!areaSetList.contains(str)){
						if(!"".equals(str.replaceAll(" ", "")) && str != null){										
							areaSetList.add(str);
						}
					}
				}
				
				int areaSetList_cnt=0;
				for(String str : areaSetList){
					if(areaSetList_cnt==0){
						area = str + ";"; 
					}else{
						area = area + ";" + str;
					}
					areaSetList_cnt++;
				}
				
				tangoMainMdl.setService_effect_area(area); //pkms_main
				tangoMainMdl.setFull_system_name(full_system_name); //pkms_target_system
				if(!"".equals(roaming+bypass_traffic)){
					bypass_traffic = bypass_traffic + " ";
				}
				
				String content = "■ 시스템 : " + p21Model.getSystem_name()+"<br/>" +
						 "■ 제목 : "+ p21Model.getTitle() + "<br/>" +
						 "■ 대상시스템 : ";
				
				if("S".equals(work_gubun)) {//초도 - S
					total_title = roaming + bypass_traffic + area + " " + eq_name + " " + ver +" 초도";
					content = content + "초도 : " + area + eq_name + "<br/>";
				}else{ //확대 - E					
					total_title = roaming + bypass_traffic + area + " " + eq_name + " " + ver +" 확대";
					content = content + "확대 : " + area + eq_name + "<br/>";
				}
				content = content + "■ CVT대표담당자 : "+ p21Model.getSystem_user_name()+"M <br/>"
						 + "■ DVT대표담당자 : "+ p21Model.getDev_system_user_name()+"M <br/>"
						 + "■ PKG버젼 : " +ver + "<br/>"
						 + "■ 버전 구분 : ";
				if("F".equals(p21Model.getVer_gubun())){
					content = content + "Full <br/>";
				}else{
					content = content + "Partial <br/>";
				}
				
				String pe_yn = "";
				if("Y".equals(p21Model.getPe_yn())){
					pe_yn = "있음";
				}else{
					pe_yn = "없음";
				}
				
				content = content +"■ 서비스중단시간 : " + p21Model.getSer_downtime() + "(분) <br/>"
						 +"■ 로밍 영향도 : " + roaming + "<br/>"
						 +"■ 우회소통 : " + bypass_traffic + "<br/>"
						 +"■ 과금영향도 : " + pe_yn + "<br/>";

				tangoMainMdl.setWork_content(content); //pkms_main
				
				tangoMainMdl.setTitle(total_title);	//제목 TITLE
				
				if(am_cnt > 0){
					tangoMainMdl.setTitle("[주간] "+ total_title);	//제목 TITLE
				}
				
				//iwcs-update
				WorkSystemModel model_bak = new WorkSystemModel();		
				model_bak = tangoMainMdl;
				//작업관리_업무PKMS DB연동 (insert) - PKGMGR_PKMS(본사 작업)
				
				workSystemService.create_pkms_main(tangoMainMdl);
				tangoMainMdl = model_bak;
				
				String work_seq = String.valueOf(seq);

				tangoMainMdl.setWork_seq(work_seq);//pkms_sub, pkms_target_system

				tangoMainMdl.setState("미승인");//승인 or 미승인
				
				tangoMainMdl.setWork_realdate_s(w_sTime);
				tangoMainMdl.setWork_realdate_e(w_eTime);
				
				tangoMainMdl.setWork_result("양호"); //pkms_sub
				tangoMainMdl.setWork_result_id(pkg21Model.getSession_user_id()); //pkms_sub
				tangoMainMdl.setWork_result_name(pkg21Model.getSession_user_name()); //pkms_sub
				
				tangoMainMdl.setOrg_gubun("SKT"); //pkms_sub

				//------------------------공통부분 끝--------------------
				
				List<WorkSystemModel> readTJList = workSystemService.readTJ(sysUserMdl);
				
				if((readTJList != null) && (readTJList.size() > 0)){
					for(WorkSystemModel tjModel : readTJList){						
						tangoMainMdl.setMaster_id(tjModel.getMaster_id());
						tangoMainMdl.setMaster_name(tjModel.getConfirm_name());
						tangoMainMdl.setMaster_team_code("0000"+tjModel.getMaster_team_code());
						tangoMainMdl.setMaster_team_name(tjModel.getMaster_team_name());
						tangoMainMdl.setMaster_movetel(tjModel.getMaster_movetel());
					}
				}else{
					tangoMainMdl.setMaster_id(pkg21Model.getSession_user_id());
					tangoMainMdl.setMaster_name(pkg21Model.getSession_user_name());
					tangoMainMdl.setMaster_team_code(pkg21Model.getSession_user_group_id());
					tangoMainMdl.setMaster_team_name(pkg21Model.getSession_user_group_name());
					tangoMainMdl.setMaster_movetel(pkg21Model.getSession_user_mobile_phone());
				}
				
				int sub_seq =0;
				WorkSystemModel workSeqModel = new WorkSystemModel();
				workSeqModel = workSystemService.read_SeqMax_Sub(workSeqModel);
				sub_seq = workSeqModel.getSeq() + 1;

				model_bak = tangoMainMdl;
				
				if("S".equals(work_gubun)) {//초도 - S
					tangoMainMdl.setWork_result("");
					tangoMainMdl.setWork_result_date_s(null);
					
					tangoMainMdl.setState("미승인");
					tangoMainMdl.setMaster_id("");
					tangoMainMdl.setMaster_name("");
					tangoMainMdl.setMaster_movetel("");
					
					WorkSystemModel work = new WorkSystemModel();
					work.setSystem_seq(p21Model.getSystem_seq());
					work = workSystemService.read_Work_Info(work);
					
					tangoMainMdl.setWork_name(work.getWork_name());
					tangoMainMdl.setWork_phone(work.getWork_phone());
					tangoMainMdl.setWork_sosok(work.getWork_sosok());
					tangoMainMdl.setWork_confirm_name(null);
				}else{
					tangoMainMdl.setWork_name(pkg21Model.getSession_user_name());
					tangoMainMdl.setWork_phone(pkg21Model.getSession_user_mobile_phone());
					tangoMainMdl.setWork_sosok(pkg21Model.getSession_user_group_name());
				}
				
				tangoMainMdl.setSeq(sub_seq);
				workSystemService.create_pkms_sub(tangoMainMdl);
				
				if("S".equals(work_gubun)) {
					tangoMainMdl.setSeq(sub_seq);
					
					tangoMainMdl.setState("승인");
					tangoMainMdl.setWork_confirm_name(pkg21Model.getSession_user_name());
					tangoMainMdl.setWork_confirm_id(pkg21Model.getSession_user_id());
					tangoMainMdl.setWork_confirm_date_s(tangoMainMdl.getMaster_date_s());
					
					WorkSystemModel TJMdl = new WorkSystemModel();
					TJMdl.setMaster_team_code(pkg21Model.getSession_user_group_id().substring(4, 8));
					List<WorkSystemModel> readTJ = workSystemService.readTJ(TJMdl);
					if((readTJ != null) && (readTJ.size() > 0)){
						for(WorkSystemModel tjModel : readTJ){
							tangoMainMdl.setMaster_id(tjModel.getMaster_id());
							tangoMainMdl.setMaster_name(tjModel.getConfirm_name());
							tangoMainMdl.setMaster_team_code("0000"+tjModel.getMaster_team_code());
							tangoMainMdl.setMaster_team_name(tjModel.getMaster_team_name());
							tangoMainMdl.setMaster_movetel(tjModel.getMaster_movetel());
						}
					}else{
						tangoMainMdl.setMaster_id(pkg21Model.getSession_user_id());
						tangoMainMdl.setMaster_name(pkg21Model.getSession_user_name());
						tangoMainMdl.setMaster_team_code(pkg21Model.getSession_user_group_id());
						tangoMainMdl.setMaster_team_name(pkg21Model.getSession_user_group_name());
						tangoMainMdl.setMaster_movetel(pkg21Model.getSession_user_mobile_phone());
					}
					
					sub_seq++;
					tangoMainMdl.setSeq(sub_seq);
					workSystemService.create_pkms_sub(tangoMainMdl);
				}
				
				tangoMainMdl = model_bak;
			}
		}
	}*/
	
	@Override
	public void tangoWork(Pkg21Model pkg21Model, String work_gubun) throws Exception {
		WorkSystemModel tangoMainMdl = new WorkSystemModel();
		
		//기본정보 중 없는 것은 여기에서
		Pkg21Model p21Model = new Pkg21Model();
		p21Model = pkg21DAO.read(pkg21Model);
		
		tangoMainMdl.setNo(pkg21Model.getPkg_seq());
		logger.debug("----------tangoWork 11111------");
		String total_title = "";
		String roaming = "";
		String area = "";
		String ver = "";
		String eq_name = "";
		String bypass_traffic = "";
		String full_system_name = "";
		
		ver = p21Model.getVer();
		if("상".equals(p21Model.getRoaming_link())){
			roaming = "[로밍연동-상]";
		}else if("중".equals(p21Model.getRoaming_link())){
			roaming = "[로밍연동-중]";
		}else if("하".equals(p21Model.getRoaming_link())){
			roaming = "[로밍연동-하]";
		}
		
		if("Y".equals(p21Model.getBypass_traffic())){
			bypass_traffic = "[우회소통]";
		}
		
		tangoMainMdl.setMaster_file_id(pkg21Model.getMaster_file_id()); //첨부파일 연동
		tangoMainMdl.setImpo("Y"); //pkms_main
		tangoMainMdl.setBackground_target(bypass_traffic + "PKMS의 작업절차서 파일 참조"); //pkms_main
		tangoMainMdl.setJob_bunya("3"); //pkms_main
		tangoMainMdl.setJob_gubun1("SW 작업"); //pkms_main
		
		if("F".equals(p21Model.getVer_gubun())){ //전체
			tangoMainMdl.setJob_gubun2("PKG적용-Full");
		}else if("P".equals(p21Model.getVer_gubun())){ //부분
			tangoMainMdl.setJob_gubun2("PKG적용-Partial");
		}else{ //패치
			tangoMainMdl.setJob_gubun2("PKG적용-Partial");
		}
		
		SimpleDateFormat DateFormat = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		Date current = new Date();
		String c_date = DateFormat.format (current);
		
		tangoMainMdl.setIns_date(c_date);//pkms_main
		tangoMainMdl.setC_date(c_date);//pkms_sub
		tangoMainMdl.setMaster_date_s(c_date);//pkms_sub

		tangoMainMdl.setGojang_step("원복");//pkms_main
		tangoMainMdl.setWork_effect("소통저조");//pkms_main
		tangoMainMdl.setWork_rank("L");//pkms_main
		
		tangoMainMdl.setWork_name(pkg21Model.getSession_user_name());	//pkms_sub

		tangoMainMdl.setIns_name(pkg21Model.getSession_user_name()); //pkms_main
		tangoMainMdl.setIns_id(pkg21Model.getSession_user_id()); //pkms_main
		tangoMainMdl.setIns_sosok(pkg21Model.getSession_user_group_name()); //pkms_main
		tangoMainMdl.setIns_sosok_code(pkg21Model.getSession_user_group_id()); //pkms_sub	
		tangoMainMdl.setWork_phone(pkg21Model.getSession_user_mobile_phone());
		tangoMainMdl.setWork_result_date_s(c_date); //pkms_sub
		tangoMainMdl.setSystem_seq(pkg21Model.getSystem_seq());
		tangoMainMdl.setWork_gubun(work_gubun);
		
		WorkSystemModel sysUserMdl = new WorkSystemModel();
		sysUserMdl.setSystem_seq(pkg21Model.getSystem_seq());
		if(null != workSystemService.read_Sys_User_Info(sysUserMdl)){
			sysUserMdl = workSystemService.read_Sys_User_Info(sysUserMdl);
			tangoMainMdl.setJob_man(sysUserMdl.getJob_man());
			tangoMainMdl.setJob_man_post(sysUserMdl.getJob_man_post());
			tangoMainMdl.setTarget_system(sysUserMdl.getTarget_system());
			tangoMainMdl.setWork_summary(sysUserMdl.getTarget_system());			

			if("S".equals(work_gubun)) {
				sysUserMdl.setPkg_seq(pkg21Model.getPkg_seq());
				sysUserMdl.setS_status("131");
				if(null != workSystemService.readIns_Data_Pkg21(sysUserMdl)){					
					sysUserMdl = workSystemService.readIns_Data_Pkg21(sysUserMdl);
					tangoMainMdl.setWork_name(sysUserMdl.getIns_name());
					tangoMainMdl.setIns_name(sysUserMdl.getIns_name());
					tangoMainMdl.setIns_id(sysUserMdl.getIns_id());
					tangoMainMdl.setIns_sosok(sysUserMdl.getIns_sosok());
					tangoMainMdl.setIns_sosok_code(sysUserMdl.getIns_sosok_code());
				}
			}
		}
		
		
		//리스트에 있는 여러개의 장비 이름을 가져과 콤마로 구분해서 장비이름 넣기 SYSTEM_NAME
		String system_name = "";
		
		//pkgEquipmentModelList에 영향있는 변수들 if문 위에서 선언 1125
		String w_sTime ="";
		String w_eTime ="";
		
		int seq = 0;
		int am_seq = 0;
		int pe_cnt = 0;
		int am_cnt = 0;
		int pm_cnt = 0;
		
		WorkSystemModel WorkSysModel = new WorkSystemModel();
		WorkSysModel.setPkg_seq(pkg21Model.getPkg_seq());

		WorkSysModel.setWork_gubun(work_gubun);
		
		List<WorkSystemModel> teamWorkDateList = workSystemService.read21TeamWorkDate(WorkSysModel);
		if(teamWorkDateList != null){
			for(WorkSystemModel workDate : teamWorkDateList){
				WorkSysModel = workSystemService.read_SeqMax_Tango(WorkSysModel);
				seq = WorkSysModel.getSeq(); //pkms_main

				String team_code= "0000" + workDate.getMaster_team_code();				
				am_cnt=0;
				pe_cnt=0;
				pm_cnt=0;
				List<String> areaList = new ArrayList<String>();
				List<String> areaSetList = new ArrayList<String>();
				
				String[] checkSeqs = pkg21Model.getCheck_seqs_e();
				String[] start_dates = pkg21Model.getStart_dates();
				String[] end_dates = pkg21Model.getEnd_dates();
				String[] start_times1 = pkg21Model.getStart_times1();
				String[] start_times2 = pkg21Model.getStart_times2();
				String[] end_times1 = pkg21Model.getEnd_times1();
				String[] end_times2 = pkg21Model.getEnd_times2();
				String[] ampms = pkg21Model.getAmpms();
				
				String[] cstart_dates = WingsStringUtil.getNotNullStringArray(start_dates);
				String[] cend_dates = WingsStringUtil.getNotNullStringArray(end_dates);
				String[] cstart_times1 = WingsStringUtil.getNotNullStringArray(start_times1);
				String[] cstart_times2 = WingsStringUtil.getNotNullStringArray(start_times2);
				String[] cend_times1 = WingsStringUtil.getNotNullStringArray(end_times1);
				String[] cend_times2 = WingsStringUtil.getNotNullStringArray(end_times2);
				String[] campms = WingsStringUtil.getNotNullStringArray(ampms);
				for(int i = 0; i < checkSeqs.length; i++) {
					if(!"N".equals(checkSeqs[i])){
						if(workDate.getStart_date().equals(cstart_dates[i]) &&
						   workDate.getEnd_date().equals(cend_dates[i]) &&
						   workDate.getStart_time1().equals(cstart_times1[i]) &&
						   workDate.getEnd_time1().equals(cend_times1[i]) &&
						   workDate.getAmpm().equals(campms[i])
							){
							PkgEquipmentModel pkgEqModel = new PkgEquipmentModel();
							pkgEqModel.setEquipment_seq(checkSeqs[i]);
							pkgEqModel = pkgEquipmentService.readName(pkgEqModel);
							if("주간".equals(campms[i])){
								am_cnt++;
								if(am_cnt == 1){
									am_seq = seq + 1;
									eq_name = pkgEqModel.getEquipment_name();
									full_system_name = pkgEqModel.getEquipment_name();
								}else if(am_cnt == 2){
									eq_name = eq_name + " 등";
								}
								if(am_cnt > 1){
									full_system_name = full_system_name + ";" + pkgEqModel.getEquipment_name();
								}
							}else{
								pm_cnt++;
								if(pm_cnt == 1){
									eq_name = pkgEqModel.getEquipment_name();
									full_system_name = pkgEqModel.getEquipment_name();
								}else if(pm_cnt == 2){
									eq_name = eq_name + " 등";
								}
								if(pm_cnt > 1){
									full_system_name = full_system_name + ";" + pkgEqModel.getEquipment_name();									
								}
							}
							
							w_sTime = cstart_dates[i] + " " + cstart_times1[i] + ":" + cstart_times2[i];
							w_eTime = cend_dates[i] + " " + cend_times1[i] + ":" + cend_times2[i];
							
							logger.debug("checkSeqs[i] : "+checkSeqs[i]);
							logger.debug("w_sTime : "+w_sTime);
							
							//시스템 이름
							if(pe_cnt==0){
								system_name = "["+ pkgEqModel.getIdc_name() +"]"+ pkgEqModel.getEquipment_name();				
							}else{
								system_name = system_name +","+"["+ pkgEqModel.getIdc_name() +"]"+ pkgEqModel.getEquipment_name();
							}
							
							areaList.add(pkgEqModel.getIdc_name());
							
							pe_cnt++;
								
							//수정위한 보조 테이블 입력
							WorkSysModel.setSeq(seq);
							WorkSysModel.setEquipment_seq(checkSeqs[i]);
							WorkSysModel.setStart_date(cstart_dates[i]);
							WorkSysModel.setEnd_date(cend_dates[i]);
							WorkSysModel.setStart_time1(cstart_times1[i]);
							WorkSysModel.setStart_time2(cstart_times2[i]);
							WorkSysModel.setEnd_time1(cend_times1[i]);
							WorkSysModel.setEnd_time2(cend_times2[i]);
							WorkSysModel.setAmpm(campms[i]);
							WorkSysModel.setPkg_seq((pkg21Model.getPkg_seq()));
							workSystemService.create_21work_equipment(WorkSysModel);
							
							if(am_cnt > 0){
								WorkSysModel.setSeq(am_seq);
								WorkSysModel.setEquipment_seq(checkSeqs[i]);
								WorkSysModel.setStart_date(cstart_dates[i]);
								WorkSysModel.setEnd_date(cend_dates[i]);
								WorkSysModel.setStart_time1(cstart_times1[i]);
								WorkSysModel.setStart_time2(cstart_times2[i]);
								WorkSysModel.setEnd_time1(cend_times1[i]);
								WorkSysModel.setEnd_time2(cend_times2[i]);
								WorkSysModel.setAmpm(campms[i]);
								WorkSysModel.setPkg_seq((pkg21Model.getPkg_seq()));
								workSystemService.create_21work_equipment(WorkSysModel);
							}
						}
					}
				}
				logger.debug("w_sTime : "+w_sTime);
				sysUserMdl.setMaster_team_code(workDate.getMaster_team_code());

				tangoMainMdl.setSeq(seq);	//SEQ
				tangoMainMdl.setTeam_code(team_code);
				
				tangoMainMdl.setWork_plandate_s(w_sTime); // 작업시작시간 WORK_PLANDATE_S  2013-03-25 02:00
				tangoMainMdl.setWork_plandate_e(w_eTime); // 작업종료시간 WORK_PLANDATE_E  2013-03-25 07:00

				tangoMainMdl.setSystem_name(system_name); // 장비 이름 SYSTEM_NAME
				
				tangoMainMdl.setWork_sosok(workDate.getWork_sosok());
				for(String str : areaList){
					if(!areaSetList.contains(str)){
						if(!"".equals(str.replaceAll(" ", "")) && str != null){										
							areaSetList.add(str);
						}
					}
				}
				
				int areaSetList_cnt=0;
				for(String str : areaSetList){
					if(areaSetList_cnt==0){
						area = str + ";"; 
					}else{
						area = area + ";" + str;
					}
					areaSetList_cnt++;
				}
				
				tangoMainMdl.setService_effect_area(area); //pkms_main
				tangoMainMdl.setFull_system_name(full_system_name); //pkms_target_system
				if(!"".equals(roaming+bypass_traffic)){
					bypass_traffic = bypass_traffic + " ";
				}
				
				String content = "■ 시스템 : " + p21Model.getSystem_name()+"<br/>" +
						 "■ 제목 : "+ p21Model.getTitle() + "<br/>" +
						 "■ 대상시스템 : ";
				
				if("S".equals(work_gubun)) {//초도 - S
					total_title = roaming + bypass_traffic + area + " " + eq_name + " " + ver +" 초도";
					content = content + "초도 : " + area + eq_name + "<br/>";
				}else{ //확대 - E					
					total_title = roaming + bypass_traffic + area + " " + eq_name + " " + ver +" 확대";
					content = content + "확대 : " + area + eq_name + "<br/>";
				}
				content = content + "■ CVT대표담당자 : "+ p21Model.getSystem_user_name()+"M <br/>"
						 + "■ DVT대표담당자 : "+ p21Model.getDev_system_user_name()+"M <br/>"
						 + "■ PKG버젼 : " +ver + "<br/>"
						 + "■ 버전 구분 : ";
				if("F".equals(p21Model.getVer_gubun())){
					content = content + "Full <br/>";
				}else{
					content = content + "Partial <br/>";
				}
				
				String pe_yn = "";
				if("Y".equals(p21Model.getPe_yn())){
					pe_yn = "있음";
				}else{
					pe_yn = "없음";
				}
				
				content = content +"■ 서비스중단시간 : " + p21Model.getSer_downtime() + "(분) <br/>"
						 +"■ 로밍 영향도 : " + roaming + "<br/>"
						 +"■ 우회소통 : " + bypass_traffic + "<br/>"
						 +"■ 과금영향도 : " + pe_yn + "<br/>";

				tangoMainMdl.setWork_content(content); //pkms_main
				
				tangoMainMdl.setTitle(total_title);	//제목 TITLE
				
				if(am_cnt > 0){
					tangoMainMdl.setTitle("[주간] "+ total_title);	//제목 TITLE
				}
				
				//iwcs-update
//				WorkSystemModel model_bak = new WorkSystemModel();		
//				model_bak = tangoMainMdl;
				//작업관리_업무PKMS DB연동 (insert) - PKGMGR_PKMS(본사 작업)
				
//				TangoModel tangomdl = new TangoModel();
				
				//최종 완성될 JSONObject 선언(전체)
	        	JSONObject jsonObject = new JSONObject();
	        	jsonObject.put("lnkgSystemNm", "PKMS");
	        	jsonObject.put("lnkgSystemWorkId", String.valueOf(seq));
	        	jsonObject.put("dmnDivCd", "105");
	        	if(work_gubun.equals("S")){
					if("F".equals(p21Model.getVer_gubun())){ //전체
						jsonObject.put("workTypCd", "101020101");
					}else if("P".equals(p21Model.getVer_gubun())){ //부분
						jsonObject.put("workTypCd", "101020201");
					}else{ //패치
						jsonObject.put("workTypCd", "101020301");
					}
				}else{
					if("F".equals(p21Model.getVer_gubun())){ //전체
						jsonObject.put("workTypCd", "101020102");
					}else if("P".equals(p21Model.getVer_gubun())){ //부분
						jsonObject.put("workTypCd", "101020202");
					}else{ //패치
						jsonObject.put("workTypCd", "101020302");
					}
				}
	        	jsonObject.put("workNm", tangoMainMdl.getTitle());
	        	jsonObject.put("genWorkTerrLstCd", "");
	        	jsonObject.put("purpWorkCd", "");
	        	jsonObject.put("rstrWayCtt", tangoMainMdl.getWork_summary());
	        	jsonObject.put("attnMtrCtt", tangoMainMdl.getBackground_target());
	        	jsonObject.put("workDtlCtt", content);
	        	jsonObject.put("workStaDate", tangoMainMdl.getWork_plandate_s());
	        	jsonObject.put("workEndDate", tangoMainMdl.getWork_plandate_e());
	        	jsonObject.put("srvcStopTime", p21Model.getSer_downtime());
	        	jsonObject.put("rstrSrvcStopTime", "");
	        	String reqpId = "SKT" + pkg21Model.getSession_user_id();
	        	jsonObject.put("reqpId", reqpId);
	        	jsonObject.put("reqDate", c_date);
	        	if(am_cnt > 0){
	        		jsonObject.put("dytmWorkYn", "Y");
	        		jsonObject.put("dytmWorkCd", "");	        		
	        	}else{
	        		jsonObject.put("dytmWorkYn", "N");
	        		jsonObject.put("dytmWorkCd", "");
	        	}
	        	jsonObject.put("cmdWorkYn", "N");
	        	jsonObject.put("svrWorkYn", "N");
	        	jsonObject.put("emsWorkYn", "N");
	        	jsonObject.put("workReqOrgId", pkg21Model.getSession_user_group_id());
	        	String workRegrtId = "SKT" + tangoMainMdl.getIns_id();
	        	jsonObject.put("workRegrtId", workRegrtId);
	        	jsonObject.put("workInfuRegrtSelCd", "");
	        	jsonObject.put("frstRegDate", tangoMainMdl.getIns_date());
	        	String frstRegUserId = "SKT" + tangoMainMdl.getIns_id();
	        	jsonObject.put("frstRegUserId", frstRegUserId);
	        	jsonObject.put("lastChgDate", "");
	        	jsonObject.put("lastChgUserId", "");

//				workSystemService.create_pkms_main(tangoMainMdl);
//				tangoMainMdl = model_bak;
				
				String work_seq = String.valueOf(seq);

				tangoMainMdl.setWork_seq(work_seq);//pkms_sub, pkms_target_system

				tangoMainMdl.setState("미승인");//승인 or 미승인
				
				tangoMainMdl.setWork_realdate_s(w_sTime);
				tangoMainMdl.setWork_realdate_e(w_eTime);
				
				tangoMainMdl.setWork_result("양호"); //pkms_sub
				tangoMainMdl.setWork_result_id(pkg21Model.getSession_user_id()); //pkms_sub
				tangoMainMdl.setWork_result_name(pkg21Model.getSession_user_name()); //pkms_sub
				
				tangoMainMdl.setOrg_gubun("SKT"); //pkms_sub

				//------------------------공통부분 끝--------------------
				
				List<WorkSystemModel> readTJList = workSystemService.readTJ(sysUserMdl);
				
				if((readTJList != null) && (readTJList.size() > 0)){
					for(WorkSystemModel tjModel : readTJList){						
						tangoMainMdl.setMaster_id(tjModel.getMaster_id());
						tangoMainMdl.setMaster_name(tjModel.getConfirm_name());
						tangoMainMdl.setMaster_team_code("0000"+tjModel.getMaster_team_code());
						tangoMainMdl.setMaster_team_name(tjModel.getMaster_team_name());
						tangoMainMdl.setMaster_movetel(tjModel.getMaster_movetel());
					}
				}else{
					tangoMainMdl.setMaster_id(pkg21Model.getSession_user_id());
					tangoMainMdl.setMaster_name(pkg21Model.getSession_user_name());
					tangoMainMdl.setMaster_team_code(pkg21Model.getSession_user_group_id());
					tangoMainMdl.setMaster_team_name(pkg21Model.getSession_user_group_name());
					tangoMainMdl.setMaster_movetel(pkg21Model.getSession_user_mobile_phone());
				}
				
				// '시행/참조'부서
	        	JSONArray workDept = new JSONArray();
	        	
	        	JSONObject workDeptInfo_main = new JSONObject();//시행부서
	        	workDeptInfo_main.put("orgDivCd", "1");
	        	workDeptInfo_main.put("orgId", tangoMainMdl.getMaster_team_code());
	        	workDeptInfo_main.put("workProgStatCd", "002002");
	        	workDeptInfo_main.put("workStatChgDate", tangoMainMdl.getIns_date());
	        	workDeptInfo_main.put("realWorkStaDate", tangoMainMdl.getWork_plandate_s());
	        	workDeptInfo_main.put("realWorkEndDate", tangoMainMdl.getWork_plandate_e());
	        	String workChrrId = "SKT" + tangoMainMdl.getIns_id();
	        	workDeptInfo_main.put("workChrrId", workChrrId);
	        	workDeptInfo_main.put("workRsltCd", "1");
	        	workDeptInfo_main.put("workRsltDtlCtt", "");
	        	workDeptInfo_main.put("realWorkStaChrrId", "");
	        	workDeptInfo_main.put("realWorkEndChrrId", "");
	        	workDeptInfo_main.put("workRstrStaDate", "");
	        	workDeptInfo_main.put("workRstrEndDate", "");
	        	workDeptInfo_main.put("frstRegDate", tangoMainMdl.getC_date());
	        	String getMaster_id = "SKT" + tangoMainMdl.getMaster_id();
	        	workDeptInfo_main.put("frstRegUserId", getMaster_id);
	        	workDeptInfo_main.put("lastChgDate", "");
	        	workDeptInfo_main.put("lastChgUserId", "");
	        	
	        	workDept.add(workDeptInfo_main);
	        	
	        	List<WorkSystemModel> deptInfo_sub = workSystemService.sub_Team(tangoMainMdl);
	        	for(WorkSystemModel diMdl : deptInfo_sub){
	        		JSONObject workDeptInfo_sub = new JSONObject();//시행부서
	        		workDeptInfo_sub.put("orgDivCd", "2");
	        		workDeptInfo_sub.put("orgId", diMdl.getMaster_team_code());
	        		workDeptInfo_sub.put("workProgStatCd", "002002");
	        		workDeptInfo_sub.put("workStatChgDate", tangoMainMdl.getIns_date());
	        		workDeptInfo_sub.put("realWorkStaDate", tangoMainMdl.getWork_plandate_s());
	        		workDeptInfo_sub.put("realWorkEndDate", tangoMainMdl.getWork_plandate_e());
	        		workDeptInfo_sub.put("workChrrId", workChrrId);
	        		workDeptInfo_sub.put("workRsltCd", "1");
	        		workDeptInfo_sub.put("workRsltDtlCtt", "");
	        		workDeptInfo_sub.put("realWorkStaChrrId", "");
	        		workDeptInfo_sub.put("realWorkEndChrrId", "");
	        		workDeptInfo_sub.put("workRstrStaDate", "");
		        	workDeptInfo_sub.put("workRstrEndDate", "");
		        	workDeptInfo_sub.put("frstRegDate", c_date);
		        	workDeptInfo_sub.put("frstRegUserId", getMaster_id);
		        	workDeptInfo_sub.put("lastChgDate", "");
		        	workDeptInfo_sub.put("lastChgUserId", "");
		        	
		        	workDept.add(workDeptInfo_sub);
	        	}
	        	jsonObject.put("workDept", workDept);
	        	
	        	// 승인정보 (검토/승인 정보)
	        	JSONArray workAprvHst = new JSONArray();
	        	JSONObject workAprvHst_main = new JSONObject();
	        	workAprvHst_main.put("orgId", tangoMainMdl.getMaster_team_code());
	        	workAprvHst_main.put("workProgStatCd", "002002");
	        	workAprvHst_main.put("rvAprvDivVal", "1");
	        	workAprvHst_main.put("aprvrId", getMaster_id);
	        	workAprvHst_main.put("aprvDate", c_date);
	        	workAprvHst_main.put("rvOponCtt", "");
	        	if(work_gubun.equals("S")){	        		
	        		workAprvHst_main.put("aprvYn", "Y");
	        	}else{
	        		workAprvHst_main.put("aprvYn", "N");
	        	}
	        	workAprvHst_main.put("frstRegDate", c_date);
	        	workAprvHst_main.put("frstRegUserId", getMaster_id);
	        	workAprvHst_main.put("lastChgDate", "");
	        	workAprvHst_main.put("lastChgUserId", "");
	        	workAprvHst.add(workAprvHst_main);
	        	
	        	jsonObject.put("workAprvHst", workAprvHst);
	        	
	        	//작업 부가정보
	        	JSONObject workAddInfo = new JSONObject();
	        	workAddInfo.put("lnkgEqpCtt", "");
	        	workAddInfo.put("refcOrgCd", "");
	        	
	        	if("N".equals(p21Model.getCha_yn())){
	        		workAddInfo.put("chrInfuYn", "N");
	        	}else{
	        		workAddInfo.put("chrInfuYn", "Y");
	        	}
	        	
	        	if("N".equals(p21Model.getRoaming_link())){
	        		workAddInfo.put("roamLnkgYn", "N");
	        	}else{
	        		workAddInfo.put("roamLnkgYn", "Y");
	        	}

	        	workAddInfo.put("testEndNotiYn", "");
//	        	workAprvHst_main.put("cmdregYn", "");
//	        	workAprvHst_main.put("cmdInclYn", "");
	        	workAddInfo.put("eqpPkgVerVal", p21Model.getVer());
	        	if("F".equals(p21Model.getVer_gubun())){
	        		workAddInfo.put("eqpPkgDivCd", "F");
	        	}else{
	        		workAddInfo.put("eqpPkgDivCd", "P");
	        	}
	        	
	        	workAddInfo.put("workOnrCd", "");
	        	workAddInfo.put("copWorkMeansCd", "");
	        	workAddInfo.put("frstRegDate", c_date);
	        	workAddInfo.put("frstRegUserId", frstRegUserId);
	        	workAddInfo.put("lastChgDate", "");
	        	workAddInfo.put("lastChgUserId", "");
	        	
	        	jsonObject.put("workAddInfo", workAddInfo);
	        	
	        	// 첨부파일
	        	JSONArray attchFile = new JSONArray();
	        	
	        	WorkSystemModel fileModel = new WorkSystemModel();
	    		fileModel.setMaster_file_id(p21Model.getMaster_file_id());
	    		fileModel.setFile_name("cvtProcedure");
	    		int file_tango = 0;
	    		
	    		if(null != workSystemService.readFileTango(fileModel)){	    			
	    			List<WorkSystemModel>readFilecvtP = workSystemService.readFileTango(fileModel);
	    			for(WorkSystemModel cvtP : readFilecvtP){
	    				file_tango++;
	    				JSONObject attchFile_p = new JSONObject();
	    				attchFile_p.put("atflClCd", "001");
	    				attchFile_p.put("atflNm", cvtP.getFile_org_name());
	    				attchFile_p.put("fileUrl", "http://pkms.sktelecom.com/");
	    				attchFile_p.put("frstRegDate", c_date);
	    				attchFile_p.put("frstRegUserId", frstRegUserId);
	    				attchFile_p.put("lastChgDate", "");
	    				attchFile_p.put("lastChgUserId", "");

	    				attchFile.add(attchFile_p);
	    			}
	    		}
	    		
	    		fileModel.setFile_name("cvtResult");
	    		if(null != workSystemService.readFileTango(fileModel)){
	    			List<WorkSystemModel>readFilecvtS = workSystemService.readFileTango(fileModel);
	    			for(WorkSystemModel cvtS : readFilecvtS){
	    				file_tango++;
	    				JSONObject attchFile_s = new JSONObject();
	    				attchFile_s.put("atflClCd", "002");
	    				attchFile_s.put("atflNm", cvtS.getFile_org_name());
	    				attchFile_s.put("fileUrl", "http://pkms.sktelecom.com/");
	    				attchFile_s.put("frstRegDate", c_date);
	    				attchFile_s.put("frstRegUserId", frstRegUserId);
	    				attchFile_s.put("lastChgDate", "");
	    				attchFile_s.put("lastChgUserId", "");
	    				
	    				attchFile.add(attchFile_s);
	    			}
	    		}
	    		
	    		fileModel.setFile_name("cvtSupstate");
	    		if(null != workSystemService.readFileTango(fileModel)){	    			
	    			List<WorkSystemModel>readFilecvtR = workSystemService.readFileTango(fileModel);
	    			for(WorkSystemModel cvtR : readFilecvtR){
	    				file_tango++;
	    				JSONObject attchFile_r = new JSONObject();
	    				attchFile_r.put("atflClCd", "003");
	    				attchFile_r.put("atflNm", cvtR.getFile_org_name());
	    				attchFile_r.put("fileUrl", "http://pkms.sktelecom.com/");
	    				attchFile_r.put("frstRegDate", c_date);
	    				attchFile_r.put("frstRegUserId", frstRegUserId);
	    				attchFile_r.put("lastChgDate", "");
	    				attchFile_r.put("lastChgUserId", "");
	    				attchFile.add(attchFile_r);
	    			}
	    		}
	    		
	    		if(file_tango == 0){
	    			JSONObject attchFile_main = new JSONObject();
	    			attchFile_main.put("atflClCd", "");
	    			attchFile_main.put("atflNm", "");
	    			attchFile_main.put("fileUrl", "");
	    			attchFile_main.put("frstRegDate", "");
	    			attchFile_main.put("frstRegUserId", "");
	    			attchFile_main.put("lastChgDate", "");
	    			attchFile_main.put("lastChgUserId", "");
	    			attchFile.add(attchFile_main);
	    		}
	    		
	    		jsonObject.put("attchFile", attchFile);
	    		
	    		// 장비정보
	    		JSONArray eqpInfo = new JSONArray();
	    		
    			for(int i = 0; i < checkSeqs.length; i++) {    				
    				if(!"N".equals(checkSeqs[i])){
    					JSONObject eqpInfo_main = new JSONObject();
    					WorkSystemModel readEqMdl = new WorkSystemModel();
    					readEqMdl.setEquipment_seq(checkSeqs[i]);
    					readEqMdl = workSystemService.readEq_Pkg21(readEqMdl);
   						
						eqpInfo_main.put("eqpId", readEqMdl.getTango_id());
						eqpInfo_main.put("eqpNm", readEqMdl.getEquipment_name());
						eqpInfo_main.put("svrIp", "");
			    		eqpInfo_main.put("svrCnntAcntgId", "");
			    		eqpInfo_main.put("rootAcntgUseYn", "");
			    		eqpInfo_main.put("workRegrtId", "");
			    		eqpInfo_main.put("oprrId", "");
			    		eqpInfo_main.put("secureGwOprrId", "");
			    		eqpInfo_main.put("frstRegDate", c_date);
			    		eqpInfo_main.put("frstRegUserId", frstRegUserId);
			    		eqpInfo_main.put("lastChgDate", "");
			    		eqpInfo_main.put("lastChgUserId", "");
    					
			    		eqpInfo.add(eqpInfo_main);
    				}
    			}
    			
    			jsonObject.put("eqpInfo", eqpInfo);
				//장비정보안의 사용자정보, 명령어정보, 스크립트 정보는 존재하지 않음
    			//장비정보안의 사용자정보, 명령어정보, 스크립트 정보는 존재하지 않음
    			//장비정보안의 사용자정보, 명령어정보, 스크립트 정보는 존재하지 않음
	        	
    			//추가 필요 항목
    			JSONObject etcInfo = new JSONObject();
    			
    			JSONObject etcItm1 = new JSONObject();
    			etcItm1.put("pkgSrno", pkg21Model.getPkg_seq());
    			etcInfo.put("etcItm1",etcItm1);
    			
    			JSONObject etcItm2 = new JSONObject();
    			etcItm2.put("dmnCd", "105");
    			etcItm2.put("workPrjNm", pkg21Model.getTitle());
    			etcItm2.put("workDgr", "");
    			etcItm2.put("workSchdStaDt", w_sTime);
    			etcItm2.put("workSchdEndDt", w_eTime);
    			etcItm2.put("reqOrgId", tangoMainMdl.getMaster_team_code());
    			etcItm2.put("regOrgId", tangoMainMdl.getMaster_team_code());
    			etcItm2.put("regUserId", getMaster_id);
    			etcItm2.put("workDtlCtt", pkg21Model.getContent());
    			etcItm2.put("prjProgStatCd", "04");
    			etcItm2.put("workProgGdCd", "01");
    			if(am_cnt > 0){
    				etcItm2.put("dytmWorkYn", "Y");    				
    			}else{
    				etcItm2.put("dytmWorkYn", "N");
    			}
    			etcItm2.put("workPurpCtt", "");
    			etcItm2.put("frstRegDate", c_date);
    			etcItm2.put("frstRegUserId", frstRegUserId);
    			
    			etcInfo.put("etcItm2",etcItm2);
    			
    			JSONArray etcItm3 = new JSONArray();    			
    			for(int i = 0; i < checkSeqs.length; i++) {    				
    				if(!"N".equals(checkSeqs[i])){
    					JSONObject etcItm3_main = new JSONObject();
    					WorkSystemModel readEqMdl = new WorkSystemModel();
    					readEqMdl.setEquipment_seq(checkSeqs[i]);
    					readEqMdl = workSystemService.readEq_Pkg21(readEqMdl);
    					
    					etcItm3_main.put("eqpId", readEqMdl.getTango_id());
    					etcItm3_main.put("eqpNm", readEqMdl.getEquipment_name());
    					
    					etcItm3.add(etcItm3_main);
    				}
    			}
    			etcInfo.put("etcItm3",etcItm3);
    			jsonObject.put("etcInfo", etcInfo);
    			String url_seq = String.valueOf(seq);
    			
    			String url = "https://openapistg.tango.sktelecom.com/tango-operation-owm-biz/workManagement/lnkgWorkInfo/PKMS/" + url_seq;
//    			String url = "https://openapi.tango.sktelecom.com/tango-operation-owm-biz/workManagement/lnkgWorkInfo/PKMS/" + url_seq;
    			String jason = jsonObject.toString();
    			logger.debug("=== TANGO DATA START====");
    			logger.debug(jason);
    			logger.debug("=== TANGO DATA END====");
    			
    			//HTTP
    			/*HttpClient httpclient = HttpClients.createDefault();
    			HttpPost httpPost = new HttpPost(url);
    			
    	        StringEntity input = new StringEntity(jason, "UTF-8");
    	        input.setContentType("application/json");
    	        httpPost.setEntity(input);
    	        //개발
    	        httpPost.addHeader("X-Auth-token", "KL0f2qXACuE8VVxYHtTAISJcV+liFVksnyZg4XAOFEjd5YEAgKduLCz1brpAJwjF");
    	        //상용
//    	        httpPost.addHeader("X-Auth-token", "aBdF4QE0bC4yPpVO98H7KW0TrEzF1NWITCWL4CQ4hcVKNNdqt8BxSFS6vYUJtPdz");
    	        httpPost.addHeader("X-Data-Type", "");*/
    	        
    	        /*HttpResponse response = httpclient.execute(httpPost);
    	        
    			String serverResponse ="";
    	        if(response.getStatusLine().getStatusCode() != 200){
    	        	logger.error("\n========Failed========\n HTTP error code : " + response.getStatusLine().getStatusCode() +
    	        			"\n HTTP error reason : " + response.getStatusLine().getReasonPhrase());
    	        	
    	        	if(response.getStatusLine().getStatusCode() != 400)
    	        		logger.error(new String(("{\"error\":\""+response.getStatusLine().getStatusCode() + response.getStatusLine().getReasonPhrase() +"\"}").getBytes(), "UTF-8"));
    	        }
    	        
    	        BufferedReader br = new BufferedReader(
    	        		new InputStreamReader((response.getEntity().getContent()), "UTF-8"));
    	        
    	        String output;
    	        logger.debug("Output from Server .... \n");
    	        	while((output = br.readLine()) != null){
    	        		serverResponse += output;
    	        		logger.debug(output);
    	        	}
    	        	
    	        	logger.debug(serverResponse);*/
    			
    			
    			
    			//SPRING REST
    		    /*HttpHeaders headers = new HttpHeaders();
    		    headers.setContentType(MediaType.APPLICATION_JSON);
    		    //개발
    		    
    		     
    		    headers.add("X-Auth-token", "KL0f2qXACuE8VVxYHtTAISJcV+liFVksnyZg4XAOFEjd5YEAgKduLCz1brpAJwjF");
    		    
    		    //상용
    		    
//    		    headers.add("X-Auth-token", "aBdF4QE0bC4yPpVO98H7KW0TrEzF1NWITCWL4CQ4hcVKNNdqt8BxSFS6vYUJtPdz");
    		     
    		    headers.add("X-Data-Type", "");
    		    
    		    HttpEntity param= new HttpEntity(JSONInput, headers);
    		    
    		    RestTemplate restTemplate = new RestTemplate();
    		    String result = restTemplate.postForObject(url, param, String.class);
    		    logger.debug("=== TANGO RESULT====");
				logger.debug(result);
				logger.debug("=== TANGO RESULT====");*/
			}
		}
	}
	
	private void tango_S_Delete (Pkg21Model pkg21Model) throws Exception {
		WorkSystemModel tangoDelMdl = new WorkSystemModel();
		tangoDelMdl.setPkg_seq(pkg21Model.getPkg_seq());
		tangoDelMdl.setWork_gubun("S");
		List<WorkSystemModel> delSeqList = workSystemService.read_Seq_S(tangoDelMdl);
		if(delSeqList != null){
			for(WorkSystemModel delModel : delSeqList){
				workSystemService.update_pkms_main_del(delModel);
				workSystemService.delete_Equipment(delModel);
			}
		}
	}
	
	/*@Override
	public void tango_E_Delete (Pkg21Model pkg21Model, String eq_seq) throws Exception {
		WorkSystemModel tangoTCMdl = new WorkSystemModel();
		WorkSystemModel tangoMSMdl = new WorkSystemModel();
		WorkSystemModel tangoDelMdl = new WorkSystemModel();
		
		tangoDelMdl.setEquipment_seq(eq_seq);
		if(null != workSystemService.read_TeamCode(tangoDelMdl)){			
			tangoTCMdl = workSystemService.read_TeamCode(tangoDelMdl);
			tangoDelMdl.setTeam_code(tangoTCMdl.getTeam_code());
			
			tangoDelMdl.setWork_gubun("E");
			tangoDelMdl.setPkg_seq(pkg21Model.getPkg_seq());
			if(null != workSystemService.read_MainSeq(tangoDelMdl)){
				
				tangoMSMdl = workSystemService.read_MainSeq(tangoDelMdl);
				tangoDelMdl.setSeq(tangoMSMdl.getSeq());
				
				workSystemService.update_pkms_main_del(tangoDelMdl);
				workSystemService.delete_Equipment(tangoDelMdl);
			}
		}
	}*/
	
	@Override
	public void tango_E_Delete (Pkg21Model pkg21Model, String eq_seq) throws Exception {
		WorkSystemModel tangoDelMdl = new WorkSystemModel();
		
		tangoDelMdl.setEquipment_seq(eq_seq);
		tangoDelMdl.setPkg_seq(pkg21Model.getPkg_seq());
		
		if(null != workSystemService.read_TangoSeq(tangoDelMdl)){			
			tangoDelMdl = workSystemService.read_TeamCode(tangoDelMdl);
			workSystemService.delete_Equipment(tangoDelMdl);
			
			JSONObject workDel = new JSONObject();
			workDel.put("lnkgSystemNm", "PKMS");
			String seq = String.valueOf(tangoDelMdl.getSeq());
			workDel.put("lnkgSystemWorkId", seq);
			/* 상용			 
			String url = "https://openapistg.tango.sktelecom.com/tango-operation-owm-biz/workManagement/lnkgWorkInfo/PKMS/" + seq + "?method=delete";
			 */
			/* 개발			 
			 */
			String url = "https://openapi.tango.sktelecom.com/tango-operation-owm-biz/workManagement/lnkgWorkInfo/PKMS/" + seq + "?method=delete";;
			String jason = workDel.toString();
			logger.debug("=== TANGO DATA START====");
			logger.debug(jason);
			logger.debug("=== TANGO DATA END====");
			
			//HTTP
			/*HttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			
	        StringEntity input = new StringEntity(jason, "UTF-8");
	        input.setContentType("application/json");
	        httpPost.setEntity(input);
	        //개발
	        httpPost.addHeader("X-Auth-token", "KL0f2qXACuE8VVxYHtTAISJcV+liFVksnyZg4XAOFEjd5YEAgKduLCz1brpAJwjF");
	        //상용
//	        httpPost.addHeader("X-Auth-token", "aBdF4QE0bC4yPpVO98H7KW0TrEzF1NWITCWL4CQ4hcVKNNdqt8BxSFS6vYUJtPdz");
	        httpPost.addHeader("X-Data-Type", "");
	        
	        HttpResponse response = httpclient.execute(httpPost);
	        
	        String serverResponse ="";
	        if(response.getStatusLine().getStatusCode() != 200){
	        	logger.error("\n========Failed========\n HTTP error code : " + response.getStatusLine().getStatusCode() +
	        			"\n HTTP error reason : " + response.getStatusLine().getReasonPhrase());
	        	
	        	if(response.getStatusLine().getStatusCode() != 400)
	        		logger.error(new String(("{\"error\":\""+response.getStatusLine().getStatusCode() + response.getStatusLine().getReasonPhrase() +"\"}").getBytes(), "UTF-8"));
	        }
	        
	        BufferedReader br = new BufferedReader(
	        		new InputStreamReader((response.getEntity().getContent()), "UTF-8"));
	        
	        String output;
	        logger.debug("Output from Server .... \n");
	        	while((output = br.readLine()) != null){
	        		serverResponse += output;
	        		logger.debug(output);
	        	}
	        	
	        	logger.debug(serverResponse);*/
			
			//SPRING
			/*
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    //개발
		    
		     
		    headers.add("X-Auth-token", "KL0f2qXACuE8VVxYHtTAISJcV+liFVksnyZg4XAOFEjd5YEAgKduLCz1brpAJwjF");
		    
		    //상용
		    
		    headers.add("X-Auth-token", "aBdF4QE0bC4yPpVO98H7KW0TrEzF1NWITCWL4CQ4hcVKNNdqt8BxSFS6vYUJtPdz");
		     
		    headers.add("X-Data-Type", "");
		    
		    HttpEntity param= new HttpEntity(JSONInput, headers);
		    
		    RestTemplate restTemplate = new RestTemplate();
		    String result = restTemplate.postForObject(url, param, String.class);
		    logger.debug("=== TANGO RESULT====");
			logger.debug(result);
			logger.debug("=== TANGO RESULT====");
		    */
		    
		}
		
	}
	
	/**
	 * CELL 참여자 등록
	 */
	@Override
	public void createCellUserList (Pkg21Model pkg21Model) throws Exception {
		pkg21DAO.createCellUserList(pkg21Model);
	}
	
	@Override
	public void deleteCellUserList(Pkg21Model pkg21Model) throws Exception {
		pkg21DAO.deleteCellUserList(pkg21Model);
	}
	
	/* 20181203 eryoon
	  * * 초대적용 시 확대적용이 남은 갯수 체크(non-Javadoc)
	  * @see com.pkms.pkgmg.pkg21.service.Pkg21ServiceIf#getEqCount(com.pkms.pkgmg.pkg21.model.Pkg21Model)
	  */
	@Override
	public int getEqCount(Pkg21Model pkg21Model) throws Exception {
		return pkg21DAO.getEqCount(pkg21Model) ;
	}
} 

