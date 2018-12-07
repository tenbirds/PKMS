package com.pkms.tempmg.model;

import java.util.ArrayList;
import java.util.List;

import com.pkms.common.attachfile.model.AttachFileMasterKey;
import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

public class TempmgModel extends AbstractModel implements Cloneable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private final List<String> fixPrevCol;
	private final List<String> fixNextCol;

	public final String PREV = "P";
	public final String VARIABLE = "V";
	public final String NEXT = "N";
	public final String EXCEL_FILE_NAME = "템플릿목록";

	private String tpl_seq;
	private String new_tpl_seq;
	private String tpl_ver;
	private String ord;
	private String title;
	private String use_yn = "N";
	private List<CodeModel> useYnItems;
	private String reg_user;
	private String reg_date;
	private String update_user;
	private String update_date;
	private int cnt = 0;
	private int pkgUseCnt = 0;
	private String position;

	private String searchCondition;
	private String searchKeyword;
	
	private List<CodeModel> searchConditionsList;
	private final String[][] searchConditions = new String[][] { {"0", "템플릿 버전"} };
	
	@AttachFileMasterKey
	private String master_file_id;
	private AttachFileModel file1;
	private String file_org_name;
	private String file_name;
	private String file_path;

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	public static final String[] templateWidths = new String[]{
//		"100", //	중요도                                      
//		"500", //	제목                                        
//		"500", //	검증 결과                                   
//		"200", //	시스템                                      
//		"200", //	요구분류                                    
//		"300", //	요구근거                                    
//		"300", //	S/W블럭                                     
//		"200", //	담당자                                      
//		"200", //	수정 후 버전                                
//		"500", //	보완내역                                    
//		"500", //	문제점 및 배경 설명 (기존 호처리 스킴)      
//		"500", //	영향범위                                    
//		"500", //	검증분야 및 방법내역                        
//		"100", //	검증분야 개수                               
//		"500", //	자제 검증 결과                              
//		"500", //	현장 확인 방법                              
//		"500", //	현장 공지 사항                              
//		"500"  //	추가 요청 사항 등 SKT 의견
		
		"50", //	중요도                                      
		"300", //	제목                                        
		"100", //	검증 결과                                   
		"150", //	시스템                                      
		"100", //	요구분류                                    
		"300", //	요구근거                                    
		"150", //	S/W블럭                                     
		"100", //	담당자                                      
		"100", //	수정 후 버전                                
		"300", //	보완내역                                    
		"300", //	문제점 및 배경 설명 (기존 호처리 스킴)      
		"300", //	영향범위                                    
		"300", //	검증분야 및 방법내역                        
//		"50", //	검증분야 개수                               
		"100", //	자제 검증 결과                              
		"300", //	현장 확인 방법                              
		"300", //	현장 공지 사항                              
		"300", //	추가 요청 사항 등 SKT 의견
		"50", //	검증내역개수
		"50",  //	개선내역 개수
		"100", // 개발 검증결과
		"200", // 개발 검증시험항목
		"250", // 개발 검증 시험결과
		"250", // 개발 검증 시험내역
		"200", // 개발 검증 comment
		"100"  // 비고
		};

	public TempmgModel() {
		fixPrevCol = new ArrayList<String>();
		fixNextCol = new ArrayList<String>();
		setFixPrevCol();
		setFixNextCol();
		setSearchConditionsList();
	}

	public List<String> getFixPrevCol() {
		return fixPrevCol;
	}
	
	public void setFixPrevCol() {
		fixPrevCol.add("No");
		fixPrevCol.add("분류");
		fixPrevCol.add("중요도");
		fixPrevCol.add("제목");
		fixPrevCol.add("상용 검증 결");
		
		fixPrevCol.add("시스템");
		fixPrevCol.add("요구분류");
		fixPrevCol.add("요구근거");
		fixPrevCol.add("S/W블럭");
		fixPrevCol.add("담당자");
		fixPrevCol.add("수정 후 버전");
		fixPrevCol.add("보완내역");
		fixPrevCol.add("문제점 및 배경 설명 (기존 호처리 스킴)");
		fixPrevCol.add("영향범위");
//		fixPrevCol.add("검증분야 및 방법/내역");
		fixPrevCol.add("검증내역 및 결과");
//		fixPrevCol.add("검증분야 개수");
		
		fixPrevCol.add("자체 검증 결과");
		fixPrevCol.add("현장 확인 방법");
		fixPrevCol.add("현장 공지 사항");
		fixPrevCol.add("추가 요청 사항 등 SKT 의견");
	}

	public List<String> getFixNextCol() {
		return fixNextCol;
	}
	
	public void setFixNextCol() {
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public List<CodeModel> getSearchConditionsList() {
		return searchConditionsList;
	}

	public void setSearchConditionsList() {
		this.searchConditionsList = CodeUtil.convertCodeModel(searchConditions);
	}
	
	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public List<CodeModel> getUseYnItems() {
		return useYnItems;
	}

	public void setUseYnItems(List<CodeModel> useYnItems) {
		this.useYnItems = useYnItems;
	}

	public String getTpl_seq() {
		return tpl_seq;
	}

	public void setTpl_seq(String tpl_seq) {
		this.tpl_seq = tpl_seq;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
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

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getTpl_ver() {
		return tpl_ver;
	}

	public void setTpl_ver(String tpl_ver) {
		this.tpl_ver = tpl_ver;
	}

	public int getPkgUseCnt() {
		return pkgUseCnt;
	}

	public void setPkgUseCnt(int pkgUseCnt) {
		this.pkgUseCnt = pkgUseCnt;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getNew_tpl_seq() {
		return new_tpl_seq;
	}

	public void setNew_tpl_seq(String new_tpl_seq) {
		this.new_tpl_seq = new_tpl_seq;
	}

	public String getMaster_file_id() {
		return master_file_id;
	}

	public void setMaster_file_id(String master_file_id) {
		this.master_file_id = master_file_id;
	}

	public AttachFileModel getFile1() {
		return file1;
	}

	public void setFile1(AttachFileModel file1) {
		this.file1 = file1;
	}

	public String getFile_org_name() {
		return file_org_name;
	}

	public void setFile_org_name(String file_org_name) {
		this.file_org_name = file_org_name;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	
	
}
