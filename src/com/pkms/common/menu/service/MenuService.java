package com.pkms.common.menu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.menu.dao.MenuDAO;
import com.pkms.common.menu.model.MenuAuthRoleModel;
import com.pkms.common.menu.model.MenuModel;
import com.pkms.common.session.service.SessionServiceIf;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 5.
 * 
 */
@Service("MenuService")
public class MenuService implements MenuServiceIf {

	@Resource(name = "MenuDAO")
	private MenuDAO menuDAO;

	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;
	
	private ArrayList<MenuModel> menuAllList = new ArrayList<MenuModel>();
	private Map<String, List<Integer>> menuAuthRoleMap = new HashMap<String, List<Integer>>();

	
	
	@Override
	public ArrayList<MenuModel> readListTopMenu() throws Exception {
		return readMenuList(1);
	}

	@Override
	public ArrayList<MenuModel> readListSubMenu() throws Exception {
		return readMenuList(2);
	}

	@Override
	public ArrayList<MenuModel> readListAllMenu() throws Exception {
		return readMenuList(0);
	}

	private ArrayList<MenuModel> readMenuList(int depth) throws Exception {

		ArrayList<MenuModel> menuList = new ArrayList<MenuModel>();
		for (MenuModel menu : getMenuAllList()) {
			// 권한 필터링
			if(isAuth(menu)){
				if (depth == 0) {
					menuList.add(menu);
				} else {
					if (menu.getMenu_depth() == depth) {
						menuList.add(menu);
					}
				}
			}
		}
		return menuList;
	}

	private ArrayList<MenuModel> getMenuAllList() throws Exception {

		if (this.menuAllList.size() == 0) {
			// 메뉴 항목 조회
			for (Object object : menuDAO.readList()) {
				menuAllList.add((MenuModel) object);
			}
			// 메뉴 권한 조회
			for (Object object : menuDAO.readListAuthRole()) {
				MenuAuthRoleModel menuAuthRoleModel = (MenuAuthRoleModel) object;
				String authRole = menuAuthRoleModel.getAuth_role();
				List<Integer> meunSeqList = menuAuthRoleMap.get(authRole);
				if(meunSeqList == null){
					meunSeqList = new ArrayList<Integer>();
				}
				meunSeqList.add(menuAuthRoleModel.getMenu_seq());
				menuAuthRoleMap.put(authRole, meunSeqList);
			}
		}
		return this.menuAllList;
	}
	
	private boolean isAuth(MenuModel menu) throws Exception{
		List<String> authList = sessionService.readAuth();
		for(String auth : authList){
			List<Integer> meunSeqList = menuAuthRoleMap.get(auth);
			if(meunSeqList != null){
				if(meunSeqList.contains(menu.getMenu_seq())){
					return true;
				}
			}
		}
		return false;
	}

	public MenuModel parentSeq(String prentStr) throws Exception {
		MenuModel Mmodel = new MenuModel();
		Mmodel.setUrl(prentStr);
		return menuDAO.readParentSeq(Mmodel);
	}
}
