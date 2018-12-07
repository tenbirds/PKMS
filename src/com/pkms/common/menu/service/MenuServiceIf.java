package com.pkms.common.menu.service;

import java.util.ArrayList;

import com.pkms.common.menu.model.MenuModel;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 5.
 * 
 */
public interface MenuServiceIf {

	public ArrayList<MenuModel> readListTopMenu() throws Exception;

	public ArrayList<MenuModel> readListSubMenu() throws Exception;

	public ArrayList<MenuModel> readListAllMenu() throws Exception;
	
	public MenuModel parentSeq(String prentStr) throws Exception;
}
