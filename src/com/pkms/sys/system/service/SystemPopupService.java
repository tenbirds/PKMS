package com.pkms.sys.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.group1.service.Group1ServiceIf;
import com.pkms.sys.group2.service.Group2ServiceIf;
import com.pkms.sys.group3.service.Group3ServiceIf;
import com.pkms.sys.system.model.SystemUserModel.SYSTEM_USER_CHARGE_GUBUN;

/**
 * 
 * 
 * 시스템 선택 팝업 관련 기능 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Service("SystemPopupService")
public class SystemPopupService implements SystemPopupServiceIf {

	@Resource(name = "Group1Service")
	private Group1ServiceIf group1Service;

	@Resource(name = "Group2Service")
	private Group2ServiceIf group2Service;

	@Resource(name = "Group3Service")
	private Group3ServiceIf group3Service;

	@Resource(name = "SystemService")
	private SystemServiceIf systemService;

	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;

	private final String OPTION_ALL = "1";

	@Override
	public void read(SysModel sysModel) throws Exception {

		StringBuffer sb = new StringBuffer();
		sb.append("[");

		//String option = sysModel.getUserCondition();
		if ("Y".equals(sysModel.getCheck_auth())) {
			ArrayList<String> chargeGubunList = new ArrayList<String>();
			Object sessionObject = sessionService.read("PKG_SYSTEM_BY_GRANTED");
			if (sessionObject != null && sessionObject instanceof SYSTEM_USER_CHARGE_GUBUN[]) {
				SYSTEM_USER_CHARGE_GUBUN[] system_user_charge_gubun = (SYSTEM_USER_CHARGE_GUBUN[]) sessionObject;
				for (SYSTEM_USER_CHARGE_GUBUN gubun : system_user_charge_gubun) {
					if (gubun == null) {
						sysModel.setEquipment_user_auth("Y");
					} else {
						chargeGubunList.add(gubun.getCode());
					}
				}
			}
			sysModel.setSystem_user_charge_gubun(chargeGubunList.toArray(new String[0]));
		}

		// 최상위 그룹 조회
		List<SysModel> group1List = group1Service.readList(sysModel);
		for (int i = 0; i < group1List.size(); i++) {
			if (i > 0) {
				sb.append(",");
			}
			SysModel group1 = group1List.get(i);
			createSysTreeScript(sb, group1, 0, sysModel);
		}

		sb.append("]");

		sysModel.setTreeScript(sb.toString());
	}

	/**
	 * 재귀 호출로 tree script 를 생성하는 메소드
	 * 
	 * @param sb
	 * @param sysModel
	 * @param depth
	 * @throws Exception
	 */
	private void createSysTreeScript(StringBuffer sb, SysModel sysModel, int depth, SysModel condition) throws Exception {
		
		if(condition.getSystem_popup_gubun().equals("Y")){
			if (depth == 2) {
				sb.append("{title: '" + sysModel.getName().replace("'", "`") + "', key: '"+sysModel.getGroup3_seq() +"', isFolder: true, expand: false");
			}else if(depth == 1){
				sb.append("{title: '" + sysModel.getName().replace("'", "`") + "', key: '"+sysModel.getGroup2_seq() +"', isFolder: true, expand: false");
			}else if(depth == 0){
				sb.append("{title: '" + sysModel.getName().replace("'", "`") + "', key: '"+sysModel.getGroup1_seq() +"', isFolder: true, expand: false");
			}
			else if(depth == 3){
				sb.append("{title: '" + sysModel.getName().replace("'", "`") + "', key: '" + sysModel.getSystem_seq() + "'");
			}
		}else{
			if (depth <= 2) {
				sb.append("{title: '" + sysModel.getName().replace("'", "`") + "', key: 'x', isFolder: true, expand: false");
			} else {
				sb.append("{title: '" + sysModel.getName().replace("'", "`") + "', key: '" + sysModel.getSystem_seq() + "'");
			}
		}
		
		List<?> sysList = new ArrayList<Object>();
		if (depth == 0) {
			SysModel model = new SysModel();
			model.setCheck_auth(condition.getCheck_auth());
			model.setEquipment_user_auth(condition.getEquipment_user_auth());
			model.setSystem_user_charge_gubun(condition.getSystem_user_charge_gubun());
			model.setGroup1_seq(sysModel.getGroup1_seq());
			if (OPTION_ALL.equals(condition.getUserCondition())) {
				sysList = group2Service.readList(model);
			} else {
				sysList = group2Service.readList4User(model);
			}

		} else if (depth == 1) {
			SysModel model = new SysModel();
			model.setCheck_auth(condition.getCheck_auth());
			model.setEquipment_user_auth(condition.getEquipment_user_auth());
			model.setSystem_user_charge_gubun(condition.getSystem_user_charge_gubun());
			model.setGroup2_seq(sysModel.getGroup2_seq());
			if (OPTION_ALL.equals(condition.getUserCondition())) {
				sysList = group3Service.readList(model);
			} else {
				sysList = group3Service.readList4User(model);
			}
		} else if (depth == 2) {
			SysModel model = new SysModel();
			model.setCheck_auth(condition.getCheck_auth());
			model.setEquipment_user_auth(condition.getEquipment_user_auth());
			model.setSystem_user_charge_gubun(condition.getSystem_user_charge_gubun());
			model.setGroup3_seq(sysModel.getGroup3_seq());
			if (OPTION_ALL.equals(condition.getUserCondition())) {
				sysList = systemService.readList(model);
			} else {
				sysList = systemService.readList4User(model);
			}
		}

		// 하위 트리 생성
		if (sysList.size() > 0) {
			sb.append(", children: [");
			// 하위 그룹 트리 생성
			for (int i = 0; i < sysList.size(); i++) {
				if (i > 0) {
					sb.append(",");
				}
				createSysTreeScript(sb, (SysModel) sysList.get(i), depth + 1, condition);
			}
			sb.append("]}");

		} else {
			sb.append("}");
		}

	}

}
