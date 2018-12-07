package com.pkms.sys.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pkms.common.model.AbstractModel;

public class SysRoadMapMappingModel extends AbstractModel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String road_map_mapping_seq;
	private String road_map_seq;
	private String new_pn_cr_seq;
	private String mapping;
	private String title ="";
	private String hname ="";
	private String reg_date;
	
	
	public String getHname() {
		return hname;
	}
	public void setHname(String hname) {
		this.hname = hname;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getRoad_map_mapping_seq() {
		return road_map_mapping_seq;
	}
	public void setRoad_map_mapping_seq(String road_map_mapping_seq) {
		this.road_map_mapping_seq = road_map_mapping_seq;
	}
	public String getRoad_map_seq() {
		return road_map_seq;
	}
	public void setRoad_map_seq(String road_map_seq) {
		this.road_map_seq = road_map_seq;
	}
	public String getNew_pn_cr_seq() {
		return new_pn_cr_seq;
	}
	public void setNew_pn_cr_seq(String new_pn_cr_seq) {
		this.new_pn_cr_seq = new_pn_cr_seq;
	}
	public String getMapping() {
		return mapping;
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
