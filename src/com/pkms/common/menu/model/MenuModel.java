package com.pkms.common.menu.model;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 5.
 * 
 */
public class MenuModel {

	/**
	 * 메뉴 SEQ
	 */
	private int menu_seq;

	/**
	 * TOP 메뉴 코드
	 */
	private int top_seq;

	/**
	 * 부모 메뉴 코드
	 */
	private int parent_seq;

	/**
	 * Depth
	 */
	private int menu_depth;

	/**
	 * 메뉴 명
	 */
	private String name;

	/**
	 * 메뉴 URL
	 */
	private String url;

	/**
	 * TOP 이미지 On
	 */
	private String image_top_on;

	/**
	 * TOP 이미지 off
	 */
	private String image_top_off;

	/**
	 * 메뉴 순서
	 */
	private int ord;

	/**
	 * 타이틀 이미지
	 */
	private String image_title;

	public int getMenu_seq() {
		return menu_seq;
	}

	public void setMenu_seq(int menu_seq) {
		this.menu_seq = menu_seq;
	}

	public int getTop_seq() {
		return top_seq;
	}

	public void setTop_seq(int top_seq) {
		this.top_seq = top_seq;
	}

	public int getParent_seq() {
		return parent_seq;
	}

	public void setParent_seq(int parent_seq) {
		this.parent_seq = parent_seq;
	}

	public int getMenu_depth() {
		return menu_depth;
	}

	public void setMenu_depth(int menu_depth) {
		this.menu_depth = menu_depth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage_top_on() {
		return image_top_on;
	}

	public void setImage_top_on(String image_top_on) {
		this.image_top_on = image_top_on;
	}

	public String getImage_top_off() {
		return image_top_off;
	}

	public void setImage_top_off(String image_top_off) {
		this.image_top_off = image_top_off;
	}

	public String getImage_title() {
		return image_title;
	}

	public void setImage_title(String image_title) {
		this.image_title = image_title;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}
}