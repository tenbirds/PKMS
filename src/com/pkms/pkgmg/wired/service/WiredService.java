package com.pkms.pkgmg.wired.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.newmail.model.NewMailModel;
import com.pkms.common.newmail.service.NewMailServiceIf;
import com.pkms.common.workSystem.model.WorkSystemModel;
import com.pkms.common.workSystem.service.WorkSystemServiceIf;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.service.PkgEquipmentServiceIf;
import com.pkms.pkgmg.pkg21.dao.Pkg21DAO;
import com.pkms.pkgmg.pkg21.model.Pkg21FileModel;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.pkgmg.pkg21.service.Pkg21ServiceIf;
import com.pkms.pkgmg.wired.dao.WiredDAO;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.equipment.service.EquipmentServiceIf;
import com.pkms.sys.system.model.SystemFileModel;
import com.wings.util.WingsStringUtil;

@Service("WiredService")
public class WiredService implements WiredServiceIf {
	@Resource(name="WiredDAO")
	private WiredDAO wiredDAO;
	
	@Resource(name = "EquipmentService")
	private EquipmentServiceIf equipmentService;
	
	@Resource(name = "PkgEquipmentService")
	private PkgEquipmentServiceIf pkgEquipmentService;
	
	@Resource(name = "NewMailService")
	protected NewMailServiceIf newMailService;
	
	@Resource(name = "WorkSystemService")
	private WorkSystemServiceIf workSystemService;
	
	@Override
	public List<Pkg21Model> readList(Pkg21Model pkg21Model) throws Exception {
		pkg21Model.setTotalCount(wiredDAO.readTotalCount(pkg21Model));
		
		List<Pkg21Model> resultList = (List<Pkg21Model>) wiredDAO.readList(pkg21Model);
		return resultList;
	}
	
	@Override
	public Pkg21Model read(Pkg21Model pkg21Model) throws Exception {
		return wiredDAO.read(pkg21Model);
	}
	
	@Override
	public void create(Pkg21Model pkg21Model) throws Exception {
		
		String tmpStatus = pkg21Model.getStatus();
//		if(tmpStatus == "300") {
			pkg21Model.setStatus("301");
//		}		
		
		
		if("Y".equals(pkg21Model.getPatch_yn())){
			pkg21Model.setTitle("Patch");
		}else{
			if("F".equals(pkg21Model.getVer_gubun())){
				pkg21Model.setTitle("Full");				
			}else{
				pkg21Model.setTitle("Partial");
			}
		}
		
		pkg21Model.setPkg_seq(wiredDAO.create(pkg21Model));
		
		
		if(tmpStatus.equals("301")) {//임시저장일 경우 메일 안보낸다. 
		
//mail send start ------------------------------------------------------------------------------------------------
			ArrayList<String> gubun =  new ArrayList<String>() ;
			gubun.add("VU");
		
			NewMailModel inputModel = new NewMailModel();  // 메일 내용
			
			String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
			String lastOne = bits[bits.length-1];
			inputModel.setMailTitle("PKG 개발 결과 등록 (PKG명 : "+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+")"); // 제목 pkg21Model.getSystem_name()  
			inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"1"));//sms 내용
			String content ="";
			content = newMailService.genrerateStringWired(null ,pkg21Model , "1", ""); // PKG 개발 결과 등록 :1
			
			inputModel.setMailContent(content); // 내용
			
			newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);
//mail send end  -----------------------------------------------------------------------------------------------------------------------------------------------------		
		}
	}
	
	@Override
	public void update(Pkg21Model pkg21Model) throws Exception {
		
		String tmpStatus = pkg21Model.getStatus();
		pkg21Model.setStatus("301");
		
		
		if("Y".equals(pkg21Model.getPatch_yn())){
			pkg21Model.setTitle("Patch");
		}else{
			if("F".equals(pkg21Model.getVer_gubun())){
				pkg21Model.setTitle("Full");				
			}else{
				pkg21Model.setTitle("Partial");
			}
		}
		wiredDAO.update(pkg21Model);
		
		if(tmpStatus.equals("301") && (!"311".equals(pkg21Model.getSave_status()))) {//임시저장일 경우 메일 안보낸다. 	검증계획 수립시 	주요보완내역 저장 시에도 메일 보내지 않는다.
		//mail send start ------------------------------------------------------------------------------------------------
				ArrayList<String> gubun =  new ArrayList<String>() ;
				gubun.add("VU");
				NewMailModel inputModel = new NewMailModel();  // 메일 내용
//				inputModel.setMailTitle("SVT 계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
				String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
				String lastOne = bits[bits.length-1];
				inputModel.setMailTitle("PKG 개발 결과 등록 (PKG명 : "+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+")"); // 제목 pkg21Model.getSystem_name()  
				inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"1"));//sms 내용
				String content ="";
				content = newMailService.genrerateStringWired(null ,pkg21Model , "1", ""); // PKG 개발 결과 등록 :1			
				inputModel.setMailContent(content); // 내용
				
				newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);
		//mail send end  -----------------------------------------------------------------------------------------------------------------------------------------------------	
		}
	}
	
	

	
	@Override
	public void pkg_status_update(Pkg21Model pkg21Model) throws Exception {
		wiredDAO.pkg_status_update(pkg21Model);
	}
	
	@Override
	public void pkg_dev_status_update(Pkg21Model pkg21Model) throws Exception {
		wiredDAO.pkg_dev_status_update(pkg21Model);
	}
	
	@Override
	public void pkg_verify_update(Pkg21Model pkg21Model) throws Exception {
		wiredDAO.pkg_verify_update(pkg21Model);
	}
	
	@Override
	public void pkg_cha_yn_update(Pkg21Model pkg21Model) throws Exception {
		wiredDAO.pkg_cha_yn_update(pkg21Model);
	}
	
	@Override
	public Pkg21Model peTypeRead(Pkg21Model pkg21Model) throws Exception {
		return wiredDAO.peTypeRead(pkg21Model);
	}
	
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
		
		questList = wiredDAO.questList(pkg21Model);
		valueList = wiredDAO.valueList(pkg21Model);
		
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

			wiredDAO.pkg_vol_create(pkg21);
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

			wiredDAO.pkg_vol_update(pkg21);
		}
		
	}
	
	@Override
	public List<Pkg21FileModel> pkg_result_list(Pkg21Model pkg21Model) throws Exception {
		return (List<Pkg21FileModel>) wiredDAO.pkg_result_list(pkg21Model);
	}
	
	@Override
	public void pkg_status_delete(Pkg21Model pkg21Model) throws Exception {
		wiredDAO.pkg_status_delete(pkg21Model);
	}
	
	@Override
	public void pkg_status_delete_like(Pkg21Model pkg21Model) throws Exception {
		wiredDAO.pkg_status_delete_like(pkg21Model);
	}
	
	@Override
	public int readEQCntLeft(Pkg21Model pkg21Model) throws Exception {
		return wiredDAO.readEQCntLeft(pkg21Model);
	}

	@Override
	public List<SystemFileModel> sysFileList(SystemFileModel systemFileModel) throws Exception {
		return wiredDAO.sysFileList(systemFileModel);
	}
	
	@Override
	public void tangoWork(Pkg21Model pkg21Model, String work_gubun) throws Exception {
		WorkSystemModel tangoMainMdl = new WorkSystemModel();
		
		//기본정보 중 없는 것은 여기에서
		Pkg21Model p21Model = new Pkg21Model();
		p21Model = wiredDAO.read(pkg21Model);
		
		if("S".equals(work_gubun)){
			this.tango_S_Delete(pkg21Model);
		}
		
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
		
		if("F".equals(p21Model.getBypass_traffic())){ //전체
			tangoMainMdl.setJob_gubun2("PKG적용-Full");
		}else if("P".equals(p21Model.getBypass_traffic())){ //부분
			tangoMainMdl.setJob_gubun2("PKG적용-Partial");
		}else{ //패치
			tangoMainMdl.setJob_gubun2("PKG적용-Patch");
		}
		
		SimpleDateFormat DateFormat = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		Date current = new Date();
		String c_date = DateFormat.format (current);
		
		tangoMainMdl.setIns_date(c_date);//pkms_main
		tangoMainMdl.setC_date(c_date);//pkms_sub
		tangoMainMdl.setMaster_date_s(c_date);//pkms_sub
		
		tangoMainMdl.setIns_date(c_date);//pkms_main

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
							
							System.out.println("checkSeqs[i] : "+checkSeqs[i]);
							System.out.println("w_sTime : "+w_sTime);
							
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
				System.out.println("w_sTime : "+w_sTime);
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
	
	@Override
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
	}
}
