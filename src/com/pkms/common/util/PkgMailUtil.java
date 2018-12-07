package com.pkms.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pkms.common.mail.service.MailService;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgStatusModel;
import com.pkms.pkgmg.pkg.model.PkgUserModel;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.equipment.model.EquipmentUserModel;
import com.pkms.sys.system.model.SystemUserModel;


public class PkgMailUtil {
	
	static Logger logger = Logger.getLogger(PkgMailUtil.class);

	public static String getTitle4Status(PkgModel pkgModel) {
		String tit = null;
		String selectedStatus = pkgModel.getSelected_status();
		
		if("1".equals(selectedStatus)) {
			tit = "PKG 검증요청";
		} else if("2".equals(selectedStatus)) {
			tit = "PKG 상용검증접수";
		} else if("3".equals(selectedStatus)) {
			tit = "PKG 상용검증완료";
		} else if("4".equals(selectedStatus)) {
			tit = "PKG 초도일정수립";
		} else if("5".equals(selectedStatus)) {
			tit = "PKG 초도승인요청";
		} else if("6".equals(selectedStatus)) {
			tit = "PKG 초도승인";
			if("0".equals(pkgModel.getUser_active_status())) {
				tit = tit + " 완료";
			}
		} else if("7".equals(selectedStatus)) {
			tit = "PKG 초도적용완료";
		} else if("8".equals(selectedStatus)) {
			tit = "PKG 확대일정수립";
		} else if("9".equals(selectedStatus)) {
			tit = "PKG 확대적용완료";
		} else if("21".equals(selectedStatus)) {
			tit = "PKG 개발검증접수";
		} else if("23".equals(selectedStatus)) {
			tit = "PKG 개발완료보고";
		} else if("24".equals(selectedStatus)) {
			tit = "PKG 개발완료승인";
		} else if("26".equals(selectedStatus)) {
			tit = "PKG 검증계획승인";
		}
		
		if(pkgModel.isTurn_down()) {
			if("2".equals(selectedStatus) || "3".equals(selectedStatus) || "6".equals(selectedStatus)) {
				tit = "[반려] " + tit;
			} else if("7".equals(selectedStatus) || "9".equals(selectedStatus)) {
				tit = "[원복] " + tit;
			}
		}
		return _getTitle4Status(pkgModel, tit);
	}
	
	private static String _getTitle4Status(PkgModel pkgModel, String tit) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("[PKMS] " + tit + " - ");
		sb.append("(시스템: " + pkgModel.getSystem_name());
		sb.append(" | 버전: " + pkgModel.getVer() + ")");
		
		return sb.toString();
	}
	
	private static String getBasicContent(PkgModel pkgModel) {
		StringBuffer sb = new StringBuffer();
		if(pkgModel.getContent()!=null){
			pkgModel.setContent(pkgModel.getContent().replaceAll("\n","<br/>"));
		}
		if(pkgModel.getRm_issue_comment()!=null){
			pkgModel.setRm_issue_comment(pkgModel.getRm_issue_comment().replaceAll("\n","<br/>"));
		}
		sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[시스템]</b></td>");
		sb.append("<td align='left' valign='top'>"+pkgModel.getSystem_name()+"</td></tr>");
		sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[제목]</b></td>");
		sb.append("<td align='left' valign='top'>"+pkgModel.getTitle()+"</td></tr>");
		sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[담당자]</b></td>");
		sb.append("<td align='left' valign='top'>"+pkgModel.getSystem_user_name()+"</td></tr>");
		
		sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[대상 시스템]</b></td>");
		if("".equals(pkgModel.getE_target_system())){
			sb.append("<td align='left' valign='top'>"+"<p>"+pkgModel.getS_target_system()+"</p>"+"</td></tr>");
		}else{
			sb.append("<td align='left' valign='top'>"+"<p>"+pkgModel.getS_target_system()+"</p>"+"<p>"+pkgModel.getE_target_system()+"</p>"+"</td></tr>");	
		}
		
		sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[초도작업]</b></td>");
		if("".equals(pkgModel.getE_work_day())){
			sb.append("<td align='left' valign='top'>"+"<p>"+pkgModel.getS_work_day()+"</p>"+"</td></tr>");
		}else{
			sb.append("<td align='left' valign='top'>"+"<p>"+pkgModel.getS_work_day()+"</p>"+"<p>"+pkgModel.getE_work_day()+"</p>"+"</td></tr>");	
		}

		sb.append("<tr style='height:14pt'><td align='left' valign='top'><b>[버전/버전구분]</b></td>");
		sb.append("<td align='left' valign='top'>"+pkgModel.getVer()+"/"+pkgModel.getVer_gubun()+"</td></tr>");
		sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[주요 보완내역]</b></td>");
		sb.append("<td align='left' valign='top'>"+pkgModel.getContent()+"</td></tr>");
		sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[RM/CEM 관련feature]</b></td>");
		sb.append("<td align='left' valign='top'>"+pkgModel.getRm_issue_comment()+"</td></tr>");
		sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[PKG 적용시 RM 검토]</b></td>");
		sb.append("<td align='left' valign='top'>"+pkgModel.getSer_content()+"</td></tr>");
		sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[서비스중단시간]</b></td>");
		sb.append("<td align='left' valign='top'>"+pkgModel.getSer_downtime()+"</td></tr>");
		
		if("N".equals(pkgModel.getRoaming_link())){
			sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[로밍 영향도]</b></td>");
			sb.append("<td align='left' valign='top'>"+"로밍영향도 없음"+"</td></tr>");
		}else{
			sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[로밍 영향도]</b></td>");
			sb.append("<td align='left' valign='top'>"+"로밍연동-"+pkgModel.getRoaming_link()+": "+pkgModel.getRoaming_link_comment()+"</td></tr>");
		}
		
		if("N".equals(pkgModel.getPe_yn())){
			sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[과금 영향도]</b></td>");
			sb.append("<td align='left' valign='top'>"+"과금영향도 없음"+"</td></tr>");
		}else{
			sb.append("<tr style='height:14pt'><td align='left' valign='top' ><b>[과금 영향도]</b></td>");
			sb.append("<td align='left' valign='top'>"+"과금영향도 있음: "+pkgModel.getPe_yn_comment()+"</td></tr>");
		}
		
		sb.append("<tr style='height:14pt'><td align='left' colspan='2'><br/>");
		sb.append("<a href='http://pkms.sktelecom.com/'>▶ PKMS 바로가기(SK내부직원용)</a></td></tr>");
		sb.append("<tr style='height:14pt'><td align='left' colspan='2'>");
		sb.append("<a href='http://pkmss.sktelecom.com/'>▶ PKMS 바로가기(BP용)</a><br/><br/></td></tr>");
		sb.append("<tr style='height:14pt'><td colspan='2' align='left'>");
		System.out.println("※※※※메일 내용※※※※"+sb.toString()+"※※※※※※※");
		return sb.toString();
	}

	public static String getContent4Status(PkgModel pkgModel, PkgStatusModel pkgStatusModel) {
		String appendStr = null;
		String selectedStatus = pkgModel.getSelected_status();
		
		//반려일 경우
		if(pkgModel.isTurn_down()) {
			if("3".equals(selectedStatus)) {
				appendStr = getContent4Status3(pkgModel, pkgStatusModel);
			} else if("7".equals(selectedStatus)) {
				appendStr = getContent4Status7(pkgModel, pkgStatusModel);
			} else if("9".equals(selectedStatus)) {
				appendStr = getContent4Status9(pkgModel, pkgStatusModel);
			} else if("2".equals(selectedStatus) || "6".equals(selectedStatus) || "21".equals(selectedStatus)) {
				appendStr = getContent4Status99(pkgModel, pkgStatusModel);
			}
		} else {
			if("1".equals(selectedStatus)) {
				appendStr = getContent4Status1(pkgModel, pkgStatusModel);
			} else if("2".equals(selectedStatus)) {
				appendStr = getContent4Status2(pkgModel, pkgStatusModel);
			} else if("21".equals(selectedStatus)) {
				appendStr = getContent4Status2(pkgModel, pkgStatusModel);
			} else if("23".equals(selectedStatus)) {
				appendStr = getContent4Status5(pkgModel, pkgStatusModel);
			} else if("24".equals(selectedStatus)) {
				appendStr = getContent4Status6(pkgModel, pkgStatusModel);
			} else if("25".equals(selectedStatus)) {
				appendStr = getContent4Status5(pkgModel, pkgStatusModel);
			} else if("26".equals(selectedStatus)) {
				appendStr = getContent4Status6(pkgModel, pkgStatusModel);
			} else if("3".equals(selectedStatus)) {
				appendStr = getContent4Status3(pkgModel, pkgStatusModel);
			} else if("4".equals(selectedStatus)) {
				appendStr = getContent4Status4(pkgModel, pkgStatusModel);
			} else if("5".equals(selectedStatus)) {
				appendStr = getContent4Status5(pkgModel, pkgStatusModel);
			} else if("6".equals(selectedStatus)) {
				appendStr = getContent4Status6(pkgModel, pkgStatusModel);
			} else if("7".equals(selectedStatus)) {
				appendStr = getContent4Status7(pkgModel, pkgStatusModel);
			} else if("8".equals(selectedStatus)) {
				appendStr = getContent4Status8(pkgModel, pkgStatusModel);
			} else if("9".equals(selectedStatus)) {
				appendStr = getContent4Status9(pkgModel, pkgStatusModel);
			}
		}
		
		return getBasicContent(pkgModel) + appendStr;
	}
	
	private static String getContent4Status1(PkgModel pkgModel, PkgStatusModel pkgStatusModel) {
		StringBuffer sb = new StringBuffer();
		sb.append("<br/></td></tr>");
		return sb.toString();
	}
	
	private static String getContent4Status2(PkgModel pkgModel, PkgStatusModel pkgStatusModel) {
		StringBuffer sb = new StringBuffer();
		if(pkgStatusModel.getCol2()!=null){
			pkgStatusModel.setCol2(pkgStatusModel.getCol2().replaceAll("\n", "<br/>&nbsp;"));
		}
		sb.append(" <b>[검증접수일] :</b> " + pkgModel.getVerify_date_start() + "<br />");
		sb.append(" <b>[검증 Comment] :</b> <br/>&nbsp;" + pkgStatusModel.getCol2() + "<br/></td></tr>");
		
		return sb.toString();
	}
	
	private static String getContent4Status3(PkgModel pkgModel, PkgStatusModel pkgStatusModel) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" <b>[검증일자] :</b> " + pkgModel.getVerify_date_start() + " ~ " + pkgModel.getVerify_date_end() + "<br />");
		sb.append(" <b>[주요 개선 기능 변경 요청서] :</b> " + pkgStatusModel.getCol3() + ((!pkgStatusModel.getCol4().equals("")) ? " (" + pkgStatusModel.getCol4() + ")<br />" : "<br />" ));
		sb.append(" <b>[신규/보완/개선 항목 검증결과 (예외처리 포함)] :</b> " + pkgStatusModel.getCol5() + ((!pkgStatusModel.getCol6().equals("")) ? " (" + pkgStatusModel.getCol6() + ")<br />" : "<br />" ));
		sb.append(" <b>[과금 검증 결과] :</b> " + pkgStatusModel.getCol7() + ((!pkgStatusModel.getCol8().equals("")) ? " (" + pkgStatusModel.getCol8() + ")<br />" : "<br />" ));
		sb.append(" <b>[기존 서비스 영향도 검토] :</b> " + pkgStatusModel.getCol9() + ((!pkgStatusModel.getCol10().equals("")) ? " (" + pkgStatusModel.getCol10() + ")<br />" : "<br />" ));
		sb.append(" <b>[PKG 적용 절차서] :</b> " + pkgStatusModel.getCol11() + ((!pkgStatusModel.getCol12().equals("")) ? " (" + pkgStatusModel.getCol12() + ")<br />" : "<br />" ));
		sb.append(" <b>[PKG 적용 전후 CheckList] :</b> " + pkgStatusModel.getCol13() + ((!pkgStatusModel.getCol14().equals("")) ? " (" + pkgStatusModel.getCol14() + ")<br />" : "<br />" ));
		sb.append(" <b>[CoD/PoD 변경사항] :</b> " + pkgStatusModel.getCol15() + ((!pkgStatusModel.getCol16().equals("")) ? " (" + pkgStatusModel.getCol16() + ")<br />" : "<br />" ));
		sb.append(" <b>[운용팀 공지사항: 추가된 FLT, 변경된 기능 등] :</b> " + pkgStatusModel.getCol17() + ((!pkgStatusModel.getCol18().equals("")) ? " (" + pkgStatusModel.getCol18() + ")<br/></td></tr>" : "<br/></td></tr>" ));
		
		return sb.toString();
	}
	
	private static String getContent4Status4(PkgModel pkgModel, PkgStatusModel pkgStatusModel) {
		StringBuffer sb = new StringBuffer();
		List<PkgEquipmentModel> eModelList = pkgModel.getPkgEquipmentModelList();
		
		sb.append(" <b>[서비스 중단 시간] :</b> " + pkgModel.getDowntime() + "<br />");
		sb.append(" <b>[영향 시스템] :</b> " + pkgModel.getImpact_systems() + "<br />");
		sb.append(" <b>* 초도 적용 장비 </b> ");
		if(eModelList == null){
			sb.append(" :<br /></td></tr> ");
		}

		int i =1;
		for(PkgEquipmentModel eModel : eModelList) {
			sb.append("   - " + eModel.getTeam_name() + " > " + eModel.getEquipment_name() 
					+ " [" + eModel.getWork_date() + " " + eModel.getStart_time1() + ":" + eModel.getStart_time2() + " ~ " + eModel.getEnd_time1() + ":" + eModel.getEnd_time2() 
					+ " ]<br />");
				
			if(i==eModelList.size()){ //리스트의 마지막 데이터에서 </td></tr>를 붙여주기 1021 - ksy
				sb.append("</td></tr>");
			}
			i++;
		}
		return sb.toString();
	}

	private static String getContent4Status5(PkgModel pkgModel, PkgStatusModel pkgStatusModel) {
		StringBuffer sb = new StringBuffer();
		List<PkgUserModel> pkgUserModelList = pkgModel.getPkgUserModelList();
		
		int i=1;
		for(PkgUserModel pkgUserModel : pkgUserModelList) {
			sb.append(" <b>[승인 " + pkgUserModel.getOrd() + "차] :</b> " + pkgUserModel.getUser_name() + "(" + pkgUserModel.getSosok() + ")<br />");
			
			if(i==pkgUserModelList.size()){ //리스트의 마지막 데이터에서 </td></tr>를 붙여주기 1021 - ksy
				sb.append("</td></tr>");
			}
			i++;
		}
		return sb.toString();
	}
	
	private static String getContent4Status6(PkgModel pkgModel, PkgStatusModel pkgStatusModel) {
		StringBuffer sb = new StringBuffer();
		List<PkgUserModel> pkgUserModelList = pkgModel.getPkgUserModelList();
		
		if("0".equals(pkgModel.getUser_active_status())) {
			sb.append("                      <b>최종 승인 완료</b>                                       <br />");
			
		} else {
			String as = pkgModel.getUser_active_status();
			int inAs = Integer.parseInt(as);
			inAs = inAs - 1;
			sb.append("                      <b>" + inAs + " 차 승인</b>                                       <br />");
			
		}
		if(pkgUserModelList==null){
			sb.append("</td></tr>");
		}
		String status = "";
		
		int i=1;
		for(PkgUserModel pkgUserModel : pkgUserModelList) {
			if("F".equals(pkgUserModel.getStatus())) {
				status = "승인";
			} else {
				status = "요청";
			}
			
			sb.append(" <b>[" + status + " " + pkgUserModel.getOrd() + "차] :</b> " + pkgUserModel.getUser_name() + ((pkgUserModel.getAu_comment() == null) ? "" : " [" + pkgUserModel.getAu_comment() + "]") + "<br />");
			
			if(i==pkgUserModelList.size()){ //리스트의 마지막 데이터에서 </td></tr>를 붙여주기 1021 - ksy
				sb.append("</td></tr>");
			}
			i++;
		}
		return sb.toString();
	}
	
	private static String getContent4Status7(PkgModel pkgModel, PkgStatusModel pkgStatusModel) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" <b>[모니터링 기간] :</b> " + pkgStatusModel.getCol1() + " 일<br />");
		sb.append(" <b>[현장 호시험 결과] :</b> " + pkgStatusModel.getCol2() + ((!pkgStatusModel.getCol3().equals("")) ? " (" + pkgStatusModel.getCol3() + ")<br />" : "<br />" ));
		sb.append(" <b>[시스템 감시 사항: 부하/메모리/ALM/FLT/STS 등] :</b> " + pkgStatusModel.getCol4() + ((!pkgStatusModel.getCol5().equals("")) ? " (" + pkgStatusModel.getCol5() + ")<br />" : "<br />" ));
		sb.append(" <b>[품질 감시 사항: 서비스 품질 등] :</b> " + pkgStatusModel.getCol6() + ((!pkgStatusModel.getCol7().equals("")) ? " (" + pkgStatusModel.getCol7() + ")<br />" : "<br />" ));
		sb.append(" <b>[상용 과금 검증 결과] :</b> " + pkgStatusModel.getCol8() + ((!pkgStatusModel.getCol9().equals("")) ? " (" + pkgStatusModel.getCol9() + ")<br />" : "<br />" ));
		sb.append(" <b>[기타 문제사항 및 F/U 내역] :</b> " + pkgStatusModel.getCol10() + ((!pkgStatusModel.getCol11().equals("")) ? " (" + pkgStatusModel.getCol11() + ")<br/></td></tr>" : "<br/></td></tr>" ));
		
		return sb.toString();
	}
	
	private static String getContent4Status8(PkgModel pkgModel, PkgStatusModel pkgStatusModel) {
		StringBuffer sb = new StringBuffer();
		List<PkgEquipmentModel> eModelList = pkgModel.getPkgEquipmentModelList();
		
		sb.append(" <b>[서비스 중단 시간] :</b> " + pkgModel.getDowntime() + "<br />");
		sb.append(" <b>[영향 시스템] :</b> " + pkgModel.getImpact_systems() + "<br />");
		sb.append(" <b>* 확대 적용 장비 </b> ");
		if(eModelList==null){
			sb.append(":<br /></td></tr>");
		}
		
		int i =1;
		for(PkgEquipmentModel eModel : eModelList) {
			sb.append("   - " + eModel.getTeam_name() + " > " + eModel.getEquipment_name() 
					+ " [" + eModel.getWork_date() + " " + eModel.getStart_time1() + ":" + eModel.getStart_time2() + " ~ " + eModel.getEnd_time1() + ":" + eModel.getEnd_time2() 
					+ " ]<br />");
			
			if(i==eModelList.size()){ //리스트의 마지막 데이터에서 </td></tr>를 붙여주기 1021 - ksy
				sb.append("</td></tr>");
			}
			i++;
		}
		return sb.toString();
	}
	
	private static String getContent4Status9(PkgModel pkgModel, PkgStatusModel pkgStatusModel) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" <b>[모니터링 기간] :</b> " + pkgStatusModel.getCol1() + " 일<br />");
		sb.append(" <b>[현장 호시험 결과] :</b> " + pkgStatusModel.getCol2() + ((!pkgStatusModel.getCol3().equals("")) ? " (" + pkgStatusModel.getCol3() + ")<br />" : "<br />" ));
		sb.append(" <b>[시스템 감시 사항: 부하/메모리/ALM/FLT/STS 등] :</b> " + pkgStatusModel.getCol4() + ((!pkgStatusModel.getCol5().equals("")) ? " (" + pkgStatusModel.getCol5() + ")<br />" : "<br />" ));
		sb.append(" <b>[품질 감시 사항: 서비스 품질 등] :</b> " + pkgStatusModel.getCol6() + ((!pkgStatusModel.getCol7().equals("")) ? " (" + pkgStatusModel.getCol7() + ")<br />" : "<br />" ));
		sb.append(" <b>[상용 과금 검증 결과] :</b> " + pkgStatusModel.getCol8() + ((!pkgStatusModel.getCol9().equals("")) ? " (" + pkgStatusModel.getCol9() + ")<br />" : "<br />" ));
		sb.append(" <b>[기타 문제사항 및 F/U 내역] :</b> " + pkgStatusModel.getCol10() + ((!pkgStatusModel.getCol11().equals("")) ? " (" + pkgStatusModel.getCol11() + ")<br/></td></tr>" : "<br/></td></tr>" ));
		
		return sb.toString();
	}
	
	private static String getContent4Status99(PkgModel pkgModel, PkgStatusModel pkgStatusModel) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" <b>[반려 사유] :</b> " + pkgStatusModel.getCol20() + "<br/></td></tr>");
		
		return sb.toString();
	}
	
	public static String[] getToAddress4Status(PkgModel pkgModel) {
		List<Object> list = new ArrayList<Object>();
		
		//검증,승인,사업계획,개발,협력업체 담당자
		List<SystemUserModel> systemUserModelList = pkgModel.getSystemUserModelList();
		
		if(systemUserModelList != null) {
			list.addAll(systemUserModelList);
		}
		
		//운용(장비별) 담당자
		List<EquipmentUserModel> EquipmentUserModelList = null;
		Map<SysModel, List<EquipmentUserModel>> equipmentUserModelMap = pkgModel.getEquipmentUserModelMap();
		
		if(equipmentUserModelMap != null) {
			Collection<List<EquipmentUserModel>> collection = equipmentUserModelMap.values();
			Iterator<List<EquipmentUserModel>> it = collection.iterator();
			
			while(it.hasNext()) {
				EquipmentUserModelList = (List<EquipmentUserModel>) it.next();
				
				list.addAll(EquipmentUserModelList);
			}
		}
		
		//return
		String[] rets = new String[list.size()];
		int i = 0;
		
		for(Object object : list) {
			if(object instanceof SystemUserModel) {
				rets[i] = ((SystemUserModel)object).getUser_email();
			} else if(object instanceof EquipmentUserModel) {
				rets[i] = ((EquipmentUserModel)object).getUser_email();
			}
			i++;
		}
		
		return rets;
	}
	
}
