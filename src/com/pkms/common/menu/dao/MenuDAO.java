package com.pkms.common.menu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.common.menu.model.MenuAuthRoleModel;
import com.pkms.common.menu.model.MenuModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * @author skywarker
 * 
 */
@Repository("MenuDAO")
public class MenuDAO extends IbatisAbstractDAO {

	/**
	 * 메뉴 정보 목록을 DB에서 조회
	 * 
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readList() throws Exception {
		return readList("MenuDAO.readList", new MenuModel());
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<?> readListAuthRole() throws Exception {
		return readList("MenuDAO.readListAuthRole", new MenuAuthRoleModel());
	}
	
	public MenuModel readParentSeq(MenuModel model) throws Exception {
		return (MenuModel) read("MenuDAO.readParentSeq", model);
	}

}
