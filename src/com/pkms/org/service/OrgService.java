package com.pkms.org.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pkms.org.dao.OrgDAO;
import com.pkms.org.model.OrgModel;
import com.pkms.org.model.OrgModel.ORG_OPTION;
import com.pkms.usermg.auth.model.AuthModel;
import com.pkms.usermg.auth.model.AuthModel.AUTH_ROLE;
import com.pkms.usermg.auth.service.AuthServiceIf;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.SktUserServiceIf;

/**
 * SKT 조직도 관련 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 25.
 * 
 */
@Service("OrgService")
public class OrgService implements OrgServiceIf {
	
	static Logger logger = Logger.getLogger(OrgService.class);

	@Resource(name = "OrgDAO")
	private OrgDAO orgDAO;

	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;

	@Resource(name = "AuthService")
	private AuthServiceIf authService;
	
	private List<OrgModel> groupList = null;
	private List<SktUserModel> sktPartUserList_AUTH_GROUP_ONLY = null;
	private List<SktUserModel> sktPartUserList_MANAGER_GROUP_ONLY = null;
	private List<SktUserModel> sktPartUserList_ALL = null;
	
	/**
	 * SK텔레콤, 사장, 사업총괄, Network부문, Network운용본부, INFRA 솔루션, Core Infra본부
	 */
	private String[] expands = new String[]{"00009999","00000002", "00003466", "00001350", "00003355", "00003755", "00003964"};

	@Override
	public void initOrg() throws Exception {
		this.groupList = null;
		this.sktPartUserList_AUTH_GROUP_ONLY = null;
		this.sktPartUserList_MANAGER_GROUP_ONLY = null;
		this.sktPartUserList_ALL = null;
	}
	
	@Override
	public void readList(OrgModel orgModel) throws Exception {

		long lap0;
		long lap1;
		long lap2;
		long lap3;
		long lap4;
		long lap5;
		long lap6;

		// 전체 그룹 조회
		lap0 = System.currentTimeMillis();
		System.out.println("0: " + lap0);

		if(this.groupList == null) {
			this.groupList = orgDAO.readList(orgModel);
		}

		lap1 = System.currentTimeMillis();
		System.out.println("1: " + ((double) (lap1 - lap0) / 1000.0) + " 초");

		HashMap<String, OrgModel> groupMap = new HashMap<String, OrgModel>();

		lap2 = System.currentTimeMillis();
		System.out.println("2: " + ((double) (lap2 - lap1) / 1000.0) + " 초");

		// 옵션별 사용자 조회
		List<SktUserModel> sktPartUserList = null;
		if (ORG_OPTION.AUTH_GROUP_ONLY.equals(orgModel.getOption())) {
			if(this.sktPartUserList_AUTH_GROUP_ONLY == null) {
				this.sktPartUserList_AUTH_GROUP_ONLY = sktUserService.readListAuth(new SktUserModel());
			}
			sktPartUserList = this.sktPartUserList_AUTH_GROUP_ONLY;
		} else if (ORG_OPTION.MANAGER_GROUP_ONLY.equals(orgModel.getOption())) {
			if(this.sktPartUserList_MANAGER_GROUP_ONLY == null) {
				this.sktPartUserList_MANAGER_GROUP_ONLY = sktUserService.readListManager(new SktUserModel());
			}			
			sktPartUserList = this.sktPartUserList_MANAGER_GROUP_ONLY;
		} else if (ORG_OPTION.GROUP_ONLY.equals(orgModel.getOption())) {
			// GROUP_ONLY 일때는 사용자 조회 필요 없음.
			sktPartUserList = new ArrayList<SktUserModel>();
		} else {
			if(this.sktPartUserList_ALL == null) {
				this.sktPartUserList_ALL = sktUserService.readList(new SktUserModel());
			}
			sktPartUserList = this.sktPartUserList_ALL;
		}
		
		lap3 = System.currentTimeMillis();
		System.out.println("3: " + ((double) (lap3 - lap2) / 1000.0) + " 초");

		for (OrgModel group : this.groupList) {
			groupMap.put(group.getIndept(), group);
		}

		for (SktUserModel sktUserModel : sktPartUserList) {
			setShowGroup(sktUserModel.getIndept(), groupMap);
		}

		// 하위 그룹 매핑 맵 생성
		TreeMap<String, ArrayList<OrgModel>> childMap = new TreeMap<String, ArrayList<OrgModel>>();
		
		// 최상위 그룹 목록
		ArrayList<OrgModel> rootList = new ArrayList<OrgModel>();
		for (OrgModel organization : groupMap.values()) {

			// 상위 그룹 키값으로 하위 그룹 매팅
			String highpartdept = organization.getHighpartdept();
//			logger.debug("2: " + highpartdept + "--" + organization.getSosok() + "--" + organization.getIndept());
			if(highpartdept == null) {
				highpartdept = "00000000";
			}
			
			ArrayList<OrgModel> childList = childMap.get(highpartdept);
			if (childList == null) {
				childList = new ArrayList<OrgModel>();
			}
			childList.add(organization);
			childMap.put(highpartdept, childList);
			
			// 최상위 그룹 식별
			if ("00".equals(organization.getLevelcd())) {// || !StringUtils.hasLength(organization.getLevelcd())) {
				rootList.add(organization);
			}
		}

		// 그룹별 사용자 맵 생성
		TreeMap<String, ArrayList<SktUserModel>> userMap = new TreeMap<String, ArrayList<SktUserModel>>();

		// 권한 표시 옵션 체크
		AUTH_ROLE auth_role = orgModel.getAuth_role();
		ArrayList<String> authUserList = new ArrayList<String>();
		if (auth_role != null) {
			AuthModel authModel = new AuthModel();
			authModel.setAuth_role(auth_role);
			List<?> authList = authService.readList(authModel);
			for (Object object : authList) {
				AuthModel model = (AuthModel) object;
				authUserList.add(model.getUser_id());
			}
		}

		lap4 = System.currentTimeMillis();
		System.out.println("4: " + ((double) (lap4 - lap3) / 1000.0) + " 초");

		for (Object object : sktPartUserList) {
			SktUserModel sktUserModel = (SktUserModel) object;

			// 권한 표시 옵션이 있는 경우
			if (auth_role != null) {
				if (authUserList.contains(sktUserModel.getEmpno())) {
					System.out.println("------------: " + sktUserModel.getEmpno());
					sktUserModel.setAuthUser(true);
				} else {
					sktUserModel.setAuthUser(false);
				}
			} else {
				sktUserModel.setAuthUser(false);
			}
			// 그룹 별 사용자 매핑
			String indept = sktUserModel.getIndept();
			ArrayList<SktUserModel> userList = userMap.get(indept);
			if (userList == null) {
				userList = new ArrayList<SktUserModel>();
			}
			userList.add(sktUserModel);
			userMap.put(indept, userList);
		}


		
		
		lap5 = System.currentTimeMillis();
		System.out.println("5: " + ((double) (lap5 - lap4) / 1000.0) + " 초");

		StringBuffer sb = new StringBuffer();

		sb.append("[");

		// 최상위 그룹으로 부터 트리 생성 시작
		for (int i = 0; i < rootList.size(); i++) {
			if (i > 0) {
				sb.append(",");
			}
			createOrgTreeScript(true, sb, rootList.get(i), childMap, userMap, orgModel.getOption());
		}
		sb.append("]");

		orgModel.setTreeScript(sb.toString());

		lap6 = System.currentTimeMillis();
		System.out.println("6: " + ((double) (lap6 - lap5) / 1000.0) + " 초");
	}

	private void setShowGroup(String partdept, Map<String, OrgModel> groupMap) {
		OrgModel orgModel = groupMap.get(partdept);
		if (orgModel != null) {
			orgModel.setShow(true);
			setShowGroup(orgModel.getHighpartdept(), groupMap);
		}
	}

	/**
	 * 재귀 호출로 tree script 를 생성하는 메소드
	 * 
	 * @param option
	 * 
	 * @param StringBuffer
	 * @param OrgModel
	 * @param HashMap
	 *            <String, ArrayList<OrgModel>>
	 * @param HashMap
	 *            <String, ArrayList<SktUserModel>>
	 * @throws Exception
	 */
	private void createOrgTreeScript(boolean root, StringBuffer sb, OrgModel organization, TreeMap<String, ArrayList<OrgModel>> childMap, TreeMap<String, ArrayList<SktUserModel>> userMap, ORG_OPTION option) throws Exception {

		// if (ORG_OPTION.AUTH_GROUP_ONLY.equals(option)) {
		// if (!organization.isShow()) {
		// return;
		// }
		// }
		boolean expand = false;
		
		//Network운용본부: 기본으로 열린 상태
		for(String s : expands) {
			if(s.equals(organization.getIndept())) {
				expand = true;
				break;
			}
		}
		
		sb.append("{ " + (root ? "hideCheckbox: true, " : "") + " title: '" + organization.getDeptnm() + "', isFolder: true, expand: " + expand + ", key: '" + organization.getIndept() + "'");

		// 해당 그룹의 하위 그룹 조회
		ArrayList<OrgModel> childList = childMap.get(organization.getIndept());
		if (childList == null) {
			childList = new ArrayList<OrgModel>();
		}

		List<SktUserModel> sktPartUserList = userMap.get(organization.getIndept());
		if (sktPartUserList == null) {
			sktPartUserList = new ArrayList<SktUserModel>();
		}
		
		// 하위 트리 생성
		if ((childList.size() + sktPartUserList.size()) > 0) {
			sb.append(", children: [");

			int checkCount = 0;
			// 하위 그룹 트리 생성
			for (int i = 0; i < childList.size(); i++) {
				OrgModel org = childList.get(i);
				if (ORG_OPTION.AUTH_GROUP_ONLY.equals(option) || ORG_OPTION.MANAGER_GROUP_ONLY.equals(option)) {
					if (!org.isShow()) {
						continue;
					}
				}
				if (checkCount > 0) {
					sb.append(",");
				}
				createOrgTreeScript(false, sb, org, childMap, userMap, option);
				checkCount++;
			}

			if (ORG_OPTION.ALL.equals(option)) {

				if (childList.size() > 0 && sktPartUserList.size() > 0) {
					sb.append(",");
				}

				// 하위 사용자 트리 생성
				for (int i = 0; i < sktPartUserList.size(); i++) {
					if (i > 0) {
						sb.append(",");
					}
					SktUserModel userModel = sktPartUserList.get(i);
					sb.append("{title: '" + userModel.getHname() + "', group: '" + userModel.getSosok() + "', key: '" + userModel.getEmpno() + "' " + (userModel.isAuthUser() ? ", select: true" : "") + "}");
				}
			}
			sb.append("]}");
		} else {
			sb.append("}");
		}
		expand = false;
	}

}
