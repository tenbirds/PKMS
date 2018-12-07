package com.pkms.sys.common.model;

import java.util.ArrayList;
import java.util.List;

import com.pkms.common.model.AbstractModel;

public class SysRoadMapSubModel extends AbstractModel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private String road_map_seq;
	private String system_seq;
	private String new_pn_cr_seq;
	private String content = "";
	private String contents[];
	private String code;
	private String codes[];
	private String start_date;
	private String start_dates[];
	private String end_date;
	private String end_dates[];
	private String reg_user;
	private String reg_date;
	private String update_user;
	private String update_date;
	private String mapping;
	private String map_code_seq;
	private String code_desc;
	private String tooltip;
	private String name;
	private String hname;
	private List<SysRoadMapMappingModel> mapping_list = new ArrayList<SysRoadMapMappingModel>();
	
	
	
	public String getRoad_map_seq() {
		return road_map_seq;
	}
	public void setRoad_map_seq(String road_map_seq) {
		this.road_map_seq = road_map_seq;
	}
	public List<SysRoadMapMappingModel> getMapping_list() {
		return mapping_list;
	}
	public void setMapping_list(List<SysRoadMapMappingModel> mapping_list) {
		this.mapping_list = mapping_list;
	}
	public String getHname() {
		return hname;
	}
	public void setHname(String hname) {
		this.hname = hname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public String getSystem_seq() {
		return system_seq;
	}
	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}
	public String getNew_pn_cr_seq() {
		return new_pn_cr_seq;
	}
	public void setNew_pn_cr_seq(String new_pn_cr_seq) {
		this.new_pn_cr_seq = new_pn_cr_seq;
	}
	public String getReg_user() {
		return reg_user;
	}
	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getUpdate_user() {
		return update_user;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String[] getContents() {
		return contents;
	}
	public void setContents(String[] contents) {
		this.contents = contents;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String[] getCodes() {
		return codes;
	}
	public void setCodes(String[] codes) {
		this.codes = codes;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String[] getStart_dates() {
		return start_dates;
	}
	public void setStart_dates(String[] start_dates) {
		this.start_dates = start_dates;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String[] getEnd_dates() {
		return end_dates;
	}
	public void setEnd_dates(String[] end_dates) {
		this.end_dates = end_dates;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getMapping() {
		return mapping;
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	public String getMap_code_seq() {
		return map_code_seq;
	}
	public void setMap_code_seq(String map_code_seq) {
		this.map_code_seq = map_code_seq;
	}
	public String getCode_desc() {
		return code_desc;
	}
	public void setCode_desc(String code_desc) {
		this.code_desc = code_desc;
	}
	
}
