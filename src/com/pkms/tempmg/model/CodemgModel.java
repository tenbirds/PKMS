package com.pkms.tempmg.model;

import com.pkms.common.model.AbstractModel;

public class CodemgModel extends AbstractModel{
 
	 private String gubun;          
	 private String common_code;    
	 private String name;           
	 private String col1;           
	 private String col2;           
	 private String col3;           
	 private String col4;           
	 private String ord;  
	 private String order_seq;//db 에서 ord로 set 해오지 못한다.
	 private String remark;         
	 private String use_yn;         
	 private String modify_yn;      
	 private String reg_user_id;    
	 private String reg_date;       
	 private String update_user_id; 
	 private String update_date;
	 private String code_name;
	 
	 
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getCommon_code() {
		return common_code;
	}
	public void setCommon_code(String common_code) {
		this.common_code = common_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getCol4() {
		return col4;
	}
	public void setCol4(String col4) {
		this.col4 = col4;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getModify_yn() {
		return modify_yn;
	}
	public void setModify_yn(String modify_yn) {
		this.modify_yn = modify_yn;
	}
	public String getReg_user_id() {
		return reg_user_id;
	}
	public void setReg_user_id(String reg_user_id) {
		this.reg_user_id = reg_user_id;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(String update_user_id) {
		this.update_user_id = update_user_id;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	public String getOrder_seq() {
		return order_seq;
	}
	public void setOrder_seq(String order_seq) {
		this.order_seq = order_seq;
	} 

	 
}
