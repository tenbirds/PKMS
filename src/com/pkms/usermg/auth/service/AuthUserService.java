package com.pkms.usermg.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pkms.common.model.AbstractModel.USER_TYPE;
import com.pkms.org.model.OrgModel;
import com.pkms.org.service.OrgServiceIf;
import com.pkms.usermg.auth.dao.AuthUserDAO;
import com.pkms.usermg.auth.model.AuthModel.AUTH_ROLE;
import com.pkms.usermg.auth.model.AuthUserModel;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.SktUserServiceIf;

/**
 * 권한 관리 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 24.
 * 
 */
@Service("AuthUserService")
public class AuthUserService implements AuthUserServiceIf {

	@Resource(name = "OrgService")
	private OrgServiceIf orgService;

	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;

	@Resource(name = "AuthUserDAO")
	private AuthUserDAO authUserDAO;

	@Override
	public void create(AuthUserModel authUserModel) throws Exception {
		ArrayList<AuthUserModel> createList = new ArrayList<AuthUserModel>();
		createList.add(authUserModel);
		authUserDAO.create(createList);
	}
	
	@Override
	public AuthUserModel read(AuthUserModel authUserModel) throws Exception {

		// Organization 조회
		OrgModel orgModel = new OrgModel();
		orgModel.setOption(authUserModel.getOption());
		orgService.readList(orgModel);

		// Tree 정보 세팅
		authUserModel.setTreeScript(orgModel.getTreeScript());

		return authUserModel;
	}

	@Override
	public List<?> readList(AuthUserModel authUserModel) throws Exception {

		SktUserModel sktUserModel = new SktUserModel();
		sktUserModel.setIndept(authUserModel.getAuth_user_group_id());
		sktUserModel.setSearchCondition(authUserModel.getSearchCondition());
		sktUserModel.setSearchKeyword(authUserModel.getSearchKeyword());

		List<?> sktUserList = sktUserService.readList(sktUserModel);

		List<AuthUserModel> resultList = new ArrayList<AuthUserModel>();

		for (Object object : sktUserList) {
			
			SktUserModel sktUser = (SktUserModel) object;
			
			AuthUserModel authUser = new AuthUserModel();
			authUser.setAuth_user_id(sktUser.getEmpno());
			
			AuthUserModel readAuthUser = authUserDAO.read(authUser);
			if(readAuthUser == null){
				readAuthUser = authUser;
			}

			readAuthUser.setAuth_user_name(sktUser.getHname());
			readAuthUser.setAuth_user_group_id(sktUser.getIndept());
			readAuthUser.setAuth_user_group_name(sktUser.getSosok());

			resultList.add(readAuthUser);
		}

		return resultList;
	}

	@Override
	public void update(AuthUserModel authUserModel) throws Exception {

		// 업데이트 대상 사용자 생성
		HashMap<String, AuthUserModel> updateUserMap = new HashMap<String, AuthUserModel>();
		for (String authUserId : authUserModel.getAuth_update_users()) {
			if (StringUtils.hasLength(authUserId)) {
				AuthUserModel model = new AuthUserModel();
				model.setAuth_user_id(authUserId);
				updateUserMap.put(authUserId, model);
			}
		}

		//
		for (String authUserId : authUserModel.getAuth_role_admins()) {
			AuthUserModel model = updateUserMap.get(authUserId);
			if (model != null) {
				model.setAuth_role_admin(true);
			}
		}

		//
		for (String authUserId : authUserModel.getAuth_role_managers()) {
			AuthUserModel model = updateUserMap.get(authUserId);
			if (model != null) {
				model.setAuth_role_manager(true);
			}
		}

		//
		for (String authUserId : authUserModel.getAuth_role_operators()) {
			AuthUserModel model = updateUserMap.get(authUserId);
			if (model != null) {
				model.setAuth_role_operator(true);
			}
		}

		// 권한 재 설정 시작
		for (AuthUserModel model : updateUserMap.values()) {

			// 해당 사용자 대한 권한 전체 삭제
			authUserDAO.delete(model);

			// 해당 사용자 대한 권한 새로 생성
			ArrayList<AuthUserModel> createList = new ArrayList<AuthUserModel>();

			if (model.isAuth_role_admin()) {
				AuthUserModel createModel = new AuthUserModel();
				createModel.setUser_id(model.getAuth_user_id());
				createModel.setAuth_role(AUTH_ROLE.ROLE_ADMIN);
				createModel.setUser_gubun(USER_TYPE.M.getCode());
				createList.add(createModel);
			}
			if (model.isAuth_role_manager()) {
				AuthUserModel createModel = new AuthUserModel();
				createModel.setUser_id(model.getAuth_user_id());
				createModel.setAuth_role(AUTH_ROLE.ROLE_MANAGER);
				createModel.setUser_gubun(USER_TYPE.M.getCode());
				createList.add(createModel);
			}
			if (model.isAuth_role_operator()) {
				AuthUserModel createModel = new AuthUserModel();
				createModel.setUser_id(model.getAuth_user_id());
				createModel.setAuth_role(AUTH_ROLE.ROLE_OPERATOR);
				createModel.setUser_gubun(USER_TYPE.M.getCode());
				createList.add(createModel);
			}
			if (createList.size() > 0) {
				authUserDAO.create(createList);
			}
		}

	}



}
