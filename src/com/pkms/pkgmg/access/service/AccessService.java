package com.pkms.pkgmg.access.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.springframework.stereotype.Service;

import com.pkms.common.workSystem.model.WorkSystemModel;
import com.pkms.common.workSystem.service.WorkSystemServiceIf;
import com.pkms.pkgmg.access.dao.AccessDAO;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.service.PkgEquipmentServiceIf;
import com.pkms.pkgmg.pkg21.controller.Pkg21Controller;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.wings.util.WingsStringUtil;

@Service("AccessService")
public class AccessService implements AccessServiceIf{
	@Resource(name="AccessDAO")
	private AccessDAO accessDAO;
	
	@Resource(name = "PkgEquipmentService")
	private PkgEquipmentServiceIf pkgEquipmentService;
	
	@Resource(name = "WorkSystemService")
	private WorkSystemServiceIf workSystemService;
	
	static Logger logger = Logger.getLogger(Pkg21Controller.class);
	
	@Override
	public List<Pkg21Model> readList(Pkg21Model pkg21Model) throws Exception {
		pkg21Model.setTotalCount(accessDAO.readTotalCount(pkg21Model));
		
		List<Pkg21Model> resultList = (List<Pkg21Model>) accessDAO.readList(pkg21Model);
		return resultList;
	}
	
	@Override
	public Pkg21Model read(Pkg21Model pkg21Model) throws Exception {
		return accessDAO.read(pkg21Model);
	}
	
	@Override
	public void create(Pkg21Model pkg21Model) throws Exception {
		pkg21Model.setPkg_seq(accessDAO.create(pkg21Model));
	}
	
	@Override
	public void update(Pkg21Model pkg21Model) throws Exception {
		accessDAO.update(pkg21Model);
	}
	
	public void pkg_status_update(Pkg21Model pkg21Model) throws Exception {
		accessDAO.pkg_status_update(pkg21Model);
	}
	
	@Override
	public List<Pkg21Model> getChkList(Pkg21Model pkg21Model,String chk_type) throws Exception {
		List<Pkg21Model> questList = null;
		List<Pkg21Model> valueList = null;
		List<Pkg21Model> returnList = new ArrayList<Pkg21Model>();
		Pkg21Model result = null;
		
		pkg21Model.setChk_type(chk_type);
		
		questList = accessDAO.questList(pkg21Model);
		valueList = accessDAO.valueList(pkg21Model);
		
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
	public void pkg_chk_create(Pkg21Model pkg21Model) throws Exception {
		Pkg21Model pkg21 = new Pkg21Model();

		String[] chkSeqs = pkg21Model.getChk_seqs();
		String[] chk_results = pkg21Model.getChk_results();
		
		String[] cchk_results = WingsStringUtil.getNotNullStringArray(chk_results);
		
		for(int i = 0; i < chkSeqs.length; i++) {
			pkg21 = new Pkg21Model();
			pkg21.setPkg_seq(pkg21Model.getPkg_seq());
			pkg21.setChk_seq(chkSeqs[i]);
			pkg21.setChk_result(cchk_results[i]);

			accessDAO.pkg_chk_create(pkg21);
		}
		
	}
	
	@Override
	public void pkg_chk_delete(Pkg21Model pkg21Model) throws Exception {
		Pkg21Model pkg21 = new Pkg21Model();

		String[] chkSeqs = pkg21Model.getChk_seqs();
		String[] chk_results = pkg21Model.getChk_results();
		
		String[] cchk_results = WingsStringUtil.getNotNullStringArray(chk_results);
		
		for(int i = 0; i < chkSeqs.length; i++) {
			pkg21 = new Pkg21Model();
			pkg21.setPkg_seq(pkg21Model.getPkg_seq());
			pkg21.setChk_seq(chkSeqs[i]);
			pkg21.setChk_result(cchk_results[i]);

			accessDAO.pkg_chk_delete(pkg21);
		}
		
	}
	
	@Override
	public void pkg_chk_delete_all(Pkg21Model pkg21Model) throws Exception {
		accessDAO.pkg_chk_delete_all(pkg21Model);
	}
	
	@Override
	public List<PkgEquipmentModel> getPkgEquipment(Pkg21Model pkg21Model, String work_gubun) throws Exception {
		List<PkgEquipmentModel> pkgEquipmentModelList = new ArrayList<PkgEquipmentModel>();
		PkgEquipmentModel pkgEModel = new PkgEquipmentModel();
		pkgEModel.setPkg_seq(pkg21Model.getPkg_seq());
		pkgEModel.setWork_gubun(work_gubun);
		
		pkgEquipmentModelList = pkgEquipmentService.readAccess(pkgEModel);
		return pkgEquipmentModelList;
	}
	
	@Override
	public void equipment_delete_file(Pkg21Model pkg21Model) throws Exception {
		accessDAO.equipment_delete_file(pkg21Model);
	}
	
	@Override
	public void not_like_delete_file(Pkg21Model pkg21Model) throws Exception {
		accessDAO.not_like_delete_file(pkg21Model);
	}
	
	@Override
	public void pkg_status_delete(Pkg21Model pkg21Model) throws Exception {
		accessDAO.pkg_status_delete(pkg21Model);
	}
	
	@Override
	public void pkg_status_delete_like(Pkg21Model pkg21Model) throws Exception {
		accessDAO.pkg_status_delete_like(pkg21Model);
	}
	
	@Override
	public Pkg21Model copy_cnt(Pkg21Model pkg21Model) throws Exception {
		return accessDAO.copy_cnt(pkg21Model);
	}
	
	@Override
	public void copy_file(Pkg21Model pkg21Model) throws Exception {
		accessDAO.copy_file(pkg21Model);
	}
	
	@Override
	public void copy_file_delete(Pkg21Model pkg21Model) throws Exception {
		accessDAO.copy_file_delete(pkg21Model);
	}
	
	@Override
	public void tangoWork(Pkg21Model pkg21Model, String work_gubun) throws Exception {
		WorkSystemModel tangoMainMdl = new WorkSystemModel();
		
		//기본정보 중 없는 것은 여기에서
		Pkg21Model p21Model = new Pkg21Model();
		p21Model = accessDAO.read(pkg21Model);
		
		tangoMainMdl.setNo(pkg21Model.getPkg_seq());

		tangoMainMdl.setMaster_file_id(pkg21Model.getMaster_file_id()); //첨부파일 연동
		tangoMainMdl.setImpo("Y"); //pkms_main
		tangoMainMdl.setBackground_target("PKMS의 작업절차서 파일 참조"); //pkms_main
		tangoMainMdl.setJob_bunya("1"); //pkms_main
		tangoMainMdl.setJob_gubun1("SW 작업"); //pkms_main
		if("F".equals(p21Model.getVer_gubun())){ //전체
			tangoMainMdl.setJob_gubun2("PKG적용-Full");
		}else { //부분
			tangoMainMdl.setJob_gubun2("PKG적용-Patch");
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
		
		tangoMainMdl.setIns_name(pkg21Model.getSession_user_name()); //pkms_main
		tangoMainMdl.setIns_id(pkg21Model.getSession_user_id()); //pkms_main
		tangoMainMdl.setIns_sosok(pkg21Model.getSession_user_group_name()); //pkms_main
		tangoMainMdl.setIns_sosok_code(pkg21Model.getSession_user_group_id()); //pkms_sub	
		tangoMainMdl.setWork_phone(pkg21Model.getSession_user_mobile_phone());
		tangoMainMdl.setWork_result_date_s(c_date); //pkms_sub
		tangoMainMdl.setSystem_seq(pkg21Model.getSystem_seq());
		
		WorkSystemModel sysUserMdl = new WorkSystemModel();
		sysUserMdl.setSystem_seq(pkg21Model.getSystem_seq());
		if(null != workSystemService.read_Sys_User_Info(sysUserMdl)){
			sysUserMdl = workSystemService.read_Sys_User_Info(sysUserMdl);
			tangoMainMdl.setJob_man(sysUserMdl.getJob_man());
			tangoMainMdl.setJob_man_post(sysUserMdl.getJob_man_post());
			tangoMainMdl.setTarget_system(sysUserMdl.getTarget_system());
			tangoMainMdl.setWork_summary(sysUserMdl.getTarget_system());
		}
		
		WorkSystemModel WorkSysModel = new WorkSystemModel();
		int seq = 0;
		String w_sTime ="";
		String w_eTime ="";
		String team_code = pkg21Model.getSession_user_group_id();
		
		WorkSysModel = workSystemService.read_SeqMax_Main(WorkSysModel);
		seq = WorkSysModel.getSeq() + 1;
		
		w_sTime = pkg21Model.getStart_date()+" "+pkg21Model.getStart_time1()+":"+pkg21Model.getStart_time2();
		w_eTime = pkg21Model.getEnd_date()+" "+pkg21Model.getEnd_time1()+":"+pkg21Model.getEnd_time2();
		
		sysUserMdl.setMaster_team_code(team_code.substring(4, 8));
		
		tangoMainMdl.setSeq(seq);	//SEQ
		tangoMainMdl.setTeam_code(team_code);
		
		tangoMainMdl.setWork_plandate_s(w_sTime); // 작업시작시간 WORK_PLANDATE_S  2013-03-25 02:00
		tangoMainMdl.setWork_plandate_e(w_eTime); // 작업종료시간 WORK_PLANDATE_E  2013-03-25 07:00
		
		tangoMainMdl.setSystem_name(p21Model.getSystem_name_real());
		tangoMainMdl.setWork_sosok(pkg21Model.getSession_user_group_name());
		tangoMainMdl.setService_effect_area(""); // 서비스 영향 지역 - 없음
		tangoMainMdl.setFull_system_name(p21Model.getSystem_name_real());
		
		String ver = "";
		String title = "";
		ver = p21Model.getVer();
		title = p21Model.getSystem_name_real()+" "+pkg21Model.getPatch_title()+" "+ver;
		
		String content = "■ 시스템 : " + p21Model.getSystem_name_real()+"<br/>" +
				 "■ 제목 : "+ p21Model.getTitle() + "<br/>" +
				 "■ 대상시스템 : ";
		if("1S".equals(work_gubun)) {
			tangoMainMdl.setWork_gubun("S");
			title = title + " 1차초도";
			content = content + "1차초도";
		}else if("2S".equals(work_gubun)) {
			tangoMainMdl.setWork_gubun("S");
			title = title + " 2차초도";
			content = content + "2차초도";
		}else if("3S".equals(work_gubun)) {
			tangoMainMdl.setWork_gubun("S");
			title = title + " 3차초도";
			content = content + "3차초도";
		}else if("1C".equals(work_gubun)) {
			tangoMainMdl.setWork_gubun("C");
			title = title + " 1차상용";
			content = content + "1차상용";
		}else if("2C".equals(work_gubun)) {
			tangoMainMdl.setWork_gubun("C");
			title = title + " 2차상용";
			content = content + "2차상용";
		}else if("1E".equals(work_gubun)) {
			tangoMainMdl.setWork_gubun("E");
			title = title + " 확대";
			content = content + "확대";
		}
		
		content = content + "■ CVT대표담당자 : "+ p21Model.getSystem_user_name()+"M <br/>"
				 + "■ DVT대표담당자 : "+ p21Model.getDev_system_user_name()+"M <br/>"
				 + "■ PKG버젼 : " +ver + "<br/>"
				 + "■ 버전 구분 : ";
		
		if("F".equals(p21Model.getVer_gubun())){
			content = content + "Full <br/>";
		}else{
			content = content + "Patch <br/>";
		}
		content = content +"■ 서비스중단시간 : " + p21Model.getSer_downtime() + "(분) <br/>";
		tangoMainMdl.setWork_content(content); //pkms_main
		tangoMainMdl.setTitle(title);
		workSystemService.create_pkms_main(tangoMainMdl);
		
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
		sysUserMdl.setSystem_seq(p21Model.getSystem_seq());
		if("1S".equals(work_gubun)) {
			sysUserMdl.setWork_gubun("DA");
		}else if("2S".equals(work_gubun)) {
			sysUserMdl.setWork_gubun("DA");
		}else if("3S".equals(work_gubun)) {
			sysUserMdl.setWork_gubun("DA");
		}else if("1C".equals(work_gubun)) {
			sysUserMdl.setWork_gubun("AU");
		}else if("2C".equals(work_gubun)) {
			sysUserMdl.setWork_gubun("AU");
		}else if("1E".equals(work_gubun)) {
			sysUserMdl.setWork_gubun("LA");
		}
		List<WorkSystemModel> readTJList = workSystemService.readTJacc(sysUserMdl);
		
		int sub_seq =0;
		WorkSystemModel workSeqModel = new WorkSystemModel();
		workSeqModel = workSystemService.read_SeqMax_Sub(workSeqModel);
		sub_seq = workSeqModel.getSeq() + 1;
		
		WorkSystemModel work = new WorkSystemModel();
		work.setSystem_seq(p21Model.getSystem_seq());
		work = workSystemService.read_Work_Info(work);
		
		tangoMainMdl.setWork_name(work.getWork_name());
		tangoMainMdl.setWork_phone(work.getWork_phone());
		tangoMainMdl.setWork_sosok(work.getWork_sosok());
		tangoMainMdl.setWork_confirm_name(null);
		
		if((readTJList != null) && (readTJList.size() > 0)){
			for(WorkSystemModel tjModel : readTJList){						
				tangoMainMdl.setMaster_id(tjModel.getMaster_id());
				tangoMainMdl.setMaster_name(tjModel.getConfirm_name());
				tangoMainMdl.setMaster_team_code("0000"+tjModel.getMaster_team_code());
				tangoMainMdl.setMaster_team_name(tjModel.getMaster_team_name());
				tangoMainMdl.setMaster_movetel(tjModel.getMaster_movetel());
				
				tangoMainMdl.setWork_result("");
				tangoMainMdl.setWork_result_date_s(null);
				
				tangoMainMdl.setState("미승인");

				tangoMainMdl.setSeq(sub_seq);
				workSystemService.create_pkms_sub_acc(tangoMainMdl);
				
				sub_seq++;
			}
		}else{
			tangoMainMdl.setMaster_id(pkg21Model.getSession_user_id());
			tangoMainMdl.setMaster_name(pkg21Model.getSession_user_name());
			tangoMainMdl.setMaster_team_code(pkg21Model.getSession_user_group_id());
			tangoMainMdl.setMaster_team_name(pkg21Model.getSession_user_group_name());
			tangoMainMdl.setMaster_movetel(pkg21Model.getSession_user_mobile_phone());
			
			tangoMainMdl.setWork_result("");
			tangoMainMdl.setWork_result_date_s(null);
			
			tangoMainMdl.setState("미승인");

			tangoMainMdl.setSeq(sub_seq);
			workSystemService.create_pkms_sub_acc(tangoMainMdl);
		}
		
	}
	
	/*@Override
	public void tangoWork(Pkg21Model pkg21Model, String work_gubun, String max_ord) throws Exception {
		WorkSystemModel tangoMainMdl = new WorkSystemModel();
		
		//기본정보 중 없는 것은 여기에서
		Pkg21Model p21Model = new Pkg21Model();
		p21Model = accessDAO.read(pkg21Model);
		
		tangoMainMdl.setNo(pkg21Model.getPkg_seq());

		tangoMainMdl.setMaster_file_id(pkg21Model.getMaster_file_id()); //첨부파일 연동
		tangoMainMdl.setImpo("Y"); //pkms_main
		tangoMainMdl.setBackground_target("PKMS의 작업절차서 파일 참조"); //pkms_main
		tangoMainMdl.setJob_bunya("1"); //pkms_main
		tangoMainMdl.setJob_gubun1("SW 작업"); //pkms_main
		if("F".equals(p21Model.getVer_gubun())){ //전체
			tangoMainMdl.setJob_gubun2("PKG적용-Full");
		}else { //부분
			tangoMainMdl.setJob_gubun2("PKG적용-Patch");
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
		
		tangoMainMdl.setIns_name(pkg21Model.getSession_user_name()); //pkms_main
		tangoMainMdl.setIns_id(pkg21Model.getSession_user_id()); //pkms_main
		tangoMainMdl.setIns_sosok(pkg21Model.getSession_user_group_name()); //pkms_main
		tangoMainMdl.setIns_sosok_code(pkg21Model.getSession_user_group_id()); //pkms_sub	
		tangoMainMdl.setWork_phone(pkg21Model.getSession_user_mobile_phone());
		tangoMainMdl.setWork_result_date_s(c_date); //pkms_sub
		tangoMainMdl.setSystem_seq(pkg21Model.getSystem_seq());
		
		WorkSystemModel sysUserMdl = new WorkSystemModel();
		sysUserMdl.setSystem_seq(pkg21Model.getSystem_seq());
		if(null != workSystemService.read_Sys_User_Info(sysUserMdl)){
			sysUserMdl = workSystemService.read_Sys_User_Info(sysUserMdl);
			tangoMainMdl.setJob_man(sysUserMdl.getJob_man());
			tangoMainMdl.setJob_man_post(sysUserMdl.getJob_man_post());
			tangoMainMdl.setTarget_system(sysUserMdl.getTarget_system());
			tangoMainMdl.setWork_summary(sysUserMdl.getTarget_system());
		}
		
		WorkSystemModel WorkSysModel = new WorkSystemModel();
		int seq = 0;
		String w_sTime ="";
		String w_eTime ="";
		String team_code = pkg21Model.getSession_user_group_id();
		WorkSysModel = workSystemService.read_SeqMax_Tango(WorkSysModel);
//		WorkSysModel = workSystemService.read_SeqMax_Main(WorkSysModel);
//		seq = WorkSysModel.getSeq() + 1;
		seq = WorkSysModel.getSeq();
		w_sTime = pkg21Model.getStart_date()+" "+pkg21Model.getStart_time1()+":"+pkg21Model.getStart_time2();
		w_eTime = pkg21Model.getEnd_date()+" "+pkg21Model.getEnd_time1()+":"+pkg21Model.getEnd_time2();
		
		sysUserMdl.setMaster_team_code(team_code.substring(4, 8));
		
		tangoMainMdl.setSeq(seq);	//SEQ
		tangoMainMdl.setTeam_code(team_code);
		
		tangoMainMdl.setWork_plandate_s(w_sTime); // 작업시작시간 WORK_PLANDATE_S  2013-03-25 02:00
		tangoMainMdl.setWork_plandate_e(w_eTime); // 작업종료시간 WORK_PLANDATE_E  2013-03-25 07:00
		
		tangoMainMdl.setSystem_name(p21Model.getSystem_name_real());
		tangoMainMdl.setWork_sosok(pkg21Model.getSession_user_group_name());
		tangoMainMdl.setService_effect_area(""); // 서비스 영향 지역 - 없음
		tangoMainMdl.setFull_system_name(p21Model.getSystem_name_real());
		
		String ver = "";
		String title = "";
		ver = p21Model.getVer();
		title = p21Model.getSystem_name_real()+" "+pkg21Model.getPatch_title()+" "+ver;
		
		String content = "■ 시스템 : " + p21Model.getSystem_name_real()+"<br/>" +
				 "■ 제목 : "+ p21Model.getTitle() + "<br/>" +
				 "■ 대상시스템 : ";
		if("1S".equals(work_gubun)) {
			tangoMainMdl.setWork_gubun("S");
			title = title + " 1차초도";
			content = content + "1차초도";
		}else if("2S".equals(work_gubun)) {
			tangoMainMdl.setWork_gubun("S");
			title = title + " 2차초도";
			content = content + "2차초도";
		}else if("3S".equals(work_gubun)) {
			tangoMainMdl.setWork_gubun("S");
			title = title + " 3차초도";
			content = content + "3차초도";
		}else if("1C".equals(work_gubun)) {
			tangoMainMdl.setWork_gubun("C");
			title = title + " 1차상용";
			content = content + "1차상용";
		}else if("2C".equals(work_gubun)) {
			tangoMainMdl.setWork_gubun("C");
			title = title + " 2차상용";
			content = content + "2차상용";
		}else if("1E".equals(work_gubun)) {
			tangoMainMdl.setWork_gubun("E");
			title = title + " 확대";
			content = content + "확대";
		}
		
		content = content + "■ CVT대표담당자 : "+ p21Model.getSystem_user_name()+"M <br/>"
				 + "■ DVT대표담당자 : "+ p21Model.getDev_system_user_name()+"M <br/>"
				 + "■ PKG버젼 : " +ver + "<br/>"
				 + "■ 버전 구분 : ";
		
		if("F".equals(p21Model.getVer_gubun())){
			content = content + "Full <br/>";
		}else{
			content = content + "Patch <br/>";
		}
		content = content +"■ 서비스중단시간 : " + p21Model.getSer_downtime() + "(분) <br/>";
		tangoMainMdl.setWork_content(content); //pkms_main
		tangoMainMdl.setTitle(title);
//		workSystemService.create_pkms_main(tangoMainMdl);
		
		JSONObject jsonObject = new JSONObject();
    	jsonObject.put("lnkgSystemNm", "PKMS");
    	jsonObject.put("lnkgSystemWorkId", String.valueOf(seq));
    	jsonObject.put("dmnDivCd", "101");
    	
    	if("1S".equals(work_gubun)) {
    		if("F".equals(p21Model.getVer_gubun())){ //전체
				jsonObject.put("workTypCd", "101020101");
			}else{ //패치
				jsonObject.put("workTypCd", "101020301");
			}
		}else if("2S".equals(work_gubun)) {
			if("F".equals(p21Model.getVer_gubun())){ //전체
				jsonObject.put("workTypCd", "101020101");
			}else{ //패치
				jsonObject.put("workTypCd", "101020301");
			}
		}else if("3S".equals(work_gubun)) {
			if("F".equals(p21Model.getVer_gubun())){ //전체
				jsonObject.put("workTypCd", "101020101");
			}else{ //패치
				jsonObject.put("workTypCd", "101020301");
			}
		}else if("1C".equals(work_gubun)) {
			if("F".equals(p21Model.getVer_gubun())){ //전체
				jsonObject.put("workTypCd", "101020102");
			}else{ //패치
				jsonObject.put("workTypCd", "101020302");
			}
		}else if("2C".equals(work_gubun)) {
			if("F".equals(p21Model.getVer_gubun())){ //전체
				jsonObject.put("workTypCd", "101020102");
			}else{ //패치
				jsonObject.put("workTypCd", "101020302");
			}
		}else if("1E".equals(work_gubun)) {
			if("F".equals(p21Model.getVer_gubun())){ //전체
				jsonObject.put("workTypCd", "101020102");
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
    	if("주간".equals(pkg21Model.getAmpm())){
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
		sysUserMdl.setSystem_seq(p21Model.getSystem_seq());
		if("1S".equals(work_gubun)) {
			sysUserMdl.setWork_gubun("DA");
		}else if("2S".equals(work_gubun)) {
			sysUserMdl.setWork_gubun("DA");
		}else if("3S".equals(work_gubun)) {
			sysUserMdl.setWork_gubun("DA");
		}else if("1C".equals(work_gubun)) {
			sysUserMdl.setWork_gubun("AU");
		}else if("2C".equals(work_gubun)) {
			sysUserMdl.setWork_gubun("AU");
		}else if("1E".equals(work_gubun)) {
			sysUserMdl.setWork_gubun("LA");
		}
		List<WorkSystemModel> readTJList = workSystemService.readTJacc(sysUserMdl);
		
		int sub_seq =0;
		WorkSystemModel workSeqModel = new WorkSystemModel();
		workSeqModel = workSystemService.read_SeqMax_Sub(workSeqModel);
		sub_seq = workSeqModel.getSeq() + 1;
		
		WorkSystemModel work = new WorkSystemModel();
		work.setSystem_seq(p21Model.getSystem_seq());
		work = workSystemService.read_Work_Info(work);
		
		tangoMainMdl.setWork_name(work.getWork_name());
		tangoMainMdl.setWork_phone(work.getWork_phone());
		tangoMainMdl.setWork_sosok(work.getWork_sosok());
		tangoMainMdl.setWork_confirm_name(null);
		
		if((readTJList != null) && (readTJList.size() > 0)){
			for(WorkSystemModel tjModel : readTJList){						
				tangoMainMdl.setMaster_id(tjModel.getMaster_id());
				tangoMainMdl.setMaster_name(tjModel.getConfirm_name());
				tangoMainMdl.setMaster_team_code("0000"+tjModel.getMaster_team_code());
				tangoMainMdl.setMaster_team_name(tjModel.getMaster_team_name());
				tangoMainMdl.setMaster_movetel(tjModel.getMaster_movetel());
				
				tangoMainMdl.setWork_result("");
				tangoMainMdl.setWork_result_date_s(null);
				
				tangoMainMdl.setState("미승인");

				tangoMainMdl.setSeq(sub_seq);
//				workSystemService.create_pkms_sub_acc(tangoMainMdl);
				
				sub_seq++;
			}
		}else{
			tangoMainMdl.setMaster_id(pkg21Model.getSession_user_id());
			tangoMainMdl.setMaster_name(pkg21Model.getSession_user_name());
			tangoMainMdl.setMaster_team_code(pkg21Model.getSession_user_group_id());
			tangoMainMdl.setMaster_team_name(pkg21Model.getSession_user_group_name());
			tangoMainMdl.setMaster_movetel(pkg21Model.getSession_user_mobile_phone());
			
			tangoMainMdl.setWork_result("");
			tangoMainMdl.setWork_result_date_s(null);
			
			tangoMainMdl.setState("미승인");

			tangoMainMdl.setSeq(sub_seq);
//			workSystemService.create_pkms_sub_acc(tangoMainMdl);
		}
		
		
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
		workAprvHst_main.put("aprvYn", "N");//승인
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
    	
    	workAddInfo.put("chrInfuYn", "N");
    	workAddInfo.put("roamLnkgYn", "N");

    	workAddInfo.put("testEndNotiYn", "");
//    	workAprvHst_main.put("cmdregYn", "");
//    	workAprvHst_main.put("cmdInclYn", "");
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
		fileModel.setFile_name("AccPKGStd");
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
		
		fileModel.setFile_name("AccSvtResult");
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
		
		fileModel.setFile_name("AccSvtSupstate");
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
		WorkSystemModel eqModel = new WorkSystemModel();
		eqModel.setPkg_seq(pkg21Model.getPkg_seq());
		eqModel.setWork_gubun(work_gubun);
		eqModel.setOrd(max_ord);
		
		List<WorkSystemModel>readEqAcc = workSystemService.readEqAcc(fileModel);
		for(WorkSystemModel eq : readEqAcc){
			JSONObject eqpInfo_main = new JSONObject();
			
			eqpInfo_main.put("eqpId", eq.getTango_id());
			eqpInfo_main.put("eqpNm", eq.getEquipment_name());
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
		etcItm2.put("dmnCd", "101");
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
		if("주간".equals(pkg21Model.getAmpm())){
			etcItm2.put("dytmWorkYn", "Y");    				
		}else{
			etcItm2.put("dytmWorkYn", "N");
		}
		etcItm2.put("workPurpCtt", "");
		etcItm2.put("frstRegDate", c_date);
		etcItm2.put("frstRegUserId", frstRegUserId);
		
		etcInfo.put("etcItm2",etcItm2);
		
		JSONArray etcItm3 = new JSONArray();
		for(WorkSystemModel eq2 : readEqAcc){
			JSONObject etcItm3_main = new JSONObject();
			
			etcItm3_main.put("eqpId", eq2.getTango_id());
			etcItm3_main.put("eqpNm", eq2.getEquipment_name());
			
			etcItm3.add(etcItm3_main);
		}
		
		etcInfo.put("etcItm3",etcItm3);
		jsonObject.put("etcInfo", etcInfo);
		
		String url_seq = String.valueOf(seq);
		String url = "https://openapistg.tango.sktelecom.com/tango-operation-owm-biz/workManagement/lnkgWorkInfo/workCmdInfo/PKMS/" + url_seq;
//		String url = "https://openapi.tango.sktelecom.com/tango-operation-owm-biz/workManagement/lnkgWorkInfo/workCmdInfo/PKMS/" + url_seq;
		String jason = jsonObject.toString();
		System.out.println("=== TANGO DATA START====");
		System.out.println(jason);
		System.out.println("=== TANGO DATA END====");
		
		//HTTP
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		
        StringEntity input = new StringEntity(jason, "UTF-8");
        input.setContentType("application/json");
        httpPost.setEntity(input);
        //개발
        httpPost.addHeader("X-Auth-token", "KL0f2qXACuE8VVxYHtTAISJcV+liFVksnyZg4XAOFEjd5YEAgKduLCz1brpAJwjF");
        //상용
        httpPost.addHeader("X-Auth-token", "aBdF4QE0bC4yPpVO98H7KW0TrEzF1NWITCWL4CQ4hcVKNNdqt8BxSFS6vYUJtPdz");
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
        	
        	logger.debug(serverResponse);
		//SPRING REST
		
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
	    System.out.println("=== TANGO RESULT====");
		System.out.println(result);
		System.out.println("=== TANGO RESULT====");
	    
	}*/
}
