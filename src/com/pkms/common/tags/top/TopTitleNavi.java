package com.pkms.common.tags.top;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.pkms.common.menu.model.MenuModel;

/**
 * 컨텐츠 영역 상단 타이블과 메뉴 경로 표시 기능을 담당하는 TabLib 클래스. <br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 9.
 * 
 */
public class TopTitleNavi extends TagSupport {

	private static final long serialVersionUID = 1L;

	private ArrayList<MenuModel> menuList;
	private String uri;

	public int doEndTag() throws JspException {

		try {

			JspWriter out = pageContext.getOut();

			StringBuffer contents = new StringBuffer();

			if (isMenuListValidation()) {

				if("/usermg/auth/AuthUser_Read.do".equals(this.uri)){
					this.uri = "/usermg/auth/Auth_Read.do";
					
				}else if("/pkgmg/schedule/Schedule_ReadList.do".equals(this.uri)){
					this.uri = "/pkgmg/pkg/Pkg_ReadList.do";
					
				}else if("/pkgmg/diary/Diary_ReadList.do".equals(this.uri)){
					this.uri = "/pkgmg/pkg/Pkg_ReadList.do";
				}
				
				for (MenuModel menuModel : menuList) {
					if (parseUriForMenuId(this.uri).equals(parseUriForMenuId(menuModel.getUrl()))) {

						// 타이틀
						contents.append("<div class=\"tit\">" + menuModel.getName() + "</div>");

						// 위치 경로
						contents.append("<div class=\"location\">");
						
						/**
						 * 2018.11.14 eryoon 수정 
						 * AS-IS: contents.append("<a href=\"#\"><img src=\"/images/ico_home.png\" alt=\"\"></a>");
						 **/
						contents.append("<a href=\"/\"><img src=\"/images/ico_home.png\" alt=\"\"></a>");

						ArrayList<MenuModel> naviMenuList = new ArrayList<MenuModel>();
						setNavigationMenu(menuModel, naviMenuList);

						for (int i = naviMenuList.size() - 1; i >= 0; i--) {
							contents.append(" &gt; ");

							/**
							 * 2018.11.14 eryoon 수정 
							 * AS-IS: 
							 * if (i == 0) {
							 *	contents.append("<span><a href=\"#\">" + naviMenuList.get(i).getName() + "</a></span>");
							 * } else {
							 * 	contents.append("<a href=\"#\">" + naviMenuList.get(i).getName() + "</a>");
							 * }
							 **/
							if (i == 0) {
								contents.append("<span><a href='"+naviMenuList.get(i).getUrl()+"'>" + naviMenuList.get(i).getName() + "</a></span>");
							} else {
								contents.append("<a href='"+naviMenuList.get(i).getUrl()+"'>" + naviMenuList.get(i).getName() + "</a>");
							}
						}

						contents.append("</div>");

						break;
					}
				}
			}

			out.println(contents);

			return EVAL_PAGE;

		} catch (IOException e) {
			throw new JspException();
		}

	}

	public void setMenuList(ArrayList<MenuModel> menuList) {
		this.menuList = menuList;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	private String parseUriForMenuId(String uri) {
		try {
			uri = uri.substring(0, uri.lastIndexOf("."));
			uri = uri.substring(0, uri.lastIndexOf("_"));
		} catch (Exception ex) {
		}
		return uri;
	}

	private void setNavigationMenu(MenuModel menu, ArrayList<MenuModel> naviMenuList) throws JspException {

		if (naviMenuList.size() > 5) {
			throw new JspException("메뉴 경로 정보가 비정상적 입니다.");
		}

		naviMenuList.add(menu);

		MenuModel parent = getParentMenu(menu);

		if (parent != null) {
			setNavigationMenu(parent, naviMenuList);
		}
	}

	private MenuModel getParentMenu(MenuModel menu) {

		if (isMenuListValidation()) {
			for (MenuModel menuModel : menuList) {
				if (menu.getParent_seq() == menuModel.getMenu_seq() && menu.getMenu_seq() != menuModel.getMenu_seq()) {
					return menuModel;
				}
			}
		}
		return null;
	}

	private boolean isMenuListValidation() {
		if (menuList != null && menuList.size() > 0) {
			return true;
		}
		return false;
	}

}
