package com.pkms.usermg.auth.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.model.AbstractModel.USER_TYPE;
import com.pkms.org.model.OrgModel;
import com.pkms.org.service.OrgServiceIf;
import com.pkms.usermg.auth.dao.AuthDAO;
import com.pkms.usermg.auth.model.AuthModel;

/**
 * 권한 관리 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 24.
 * 
 */
@Service("AuthService")
public class AuthService implements AuthServiceIf {

	@Resource(name = "OrgService")
	private OrgServiceIf orgService;

	@Resource(name = "AuthDAO")
	private AuthDAO authDAO;

	@Override
	public AuthModel read(AuthModel authModel) throws Exception {

		// Organization 조회
		OrgModel orgModel = new OrgModel();
		orgModel.setAuth_role(authModel.getAuth_role());
		orgModel.setOption(authModel.getOption());
		orgService.readList(orgModel);

		// Tree 정보 세팅
		authModel.setTreeScript(orgModel.getTreeScript());

		return authModel;
	}

	@Override
	public List<?> readList(AuthModel authModel) throws Exception {
		// 권한 별 사용자 목록을 조회
		return authDAO.readList(authModel);
	}

	@Override
	public void update(AuthModel authModel) throws Exception {

		// 해당 권한에 대한 사용자 전체 삭제
		authDAO.delete(authModel);

		// 해당 권한에 대한 사용자 새로 생성
		String selectedKeys = authModel.getSelectedKeys();
		if (selectedKeys != null) {
			String[] keys = selectedKeys.split(",");
			if (keys != null && keys.length > 0) {

				ArrayList<AuthModel> createList = new ArrayList<AuthModel>();

				for (int i = 0; i < keys.length; i++) {

					AuthModel model = new AuthModel();
					model.setAuth_role(authModel.getAuth_role());
					model.setUser_id(keys[i]);
					model.setUser_gubun(USER_TYPE.M.getCode());
					createList.add(model);

					// Query 나누기 (오라클 제약사항)
					if (i % 300 == 0 && i > 0) {
						authDAO.create(createList);
						createList.clear();
					} else if (i == keys.length - 1) {
						authDAO.create(createList);
						createList.clear();
					}
				}
			}
		}

	}

}
