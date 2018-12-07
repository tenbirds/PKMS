package com.pkms.sys.system.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pkms.common.attachfile.model.AttachFileMasterKey;
import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.model.AbstractModel;

public class SystemFileModel extends AbstractModel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
    
	private String id;         	// 부모
    private String pId;        	// 자식
    private String name;       	// 트리이름
    private String open;       	// 트리 OPEN 여부, true/false
    private String isParent;   	// 부모 여부, true/false
    private String web;     	// 링크 URL
    private String type;		//트리 구분 , 우선 SYSTEM만 적용.
    private String use_yn;		//사용 여부
    private String reg_user;	// 등록자
    private String reg_date;	//등록일
    private String update_user; //수정자
    private String update_date;	//수정일
    
    private String drag;
    private String drop;
    
    private String master_file_id;
    private String attach_file_id;
    private String file_extension;
    private long file_size;
    private String parent_tree_id;
    private String file_path;
    private String file_org_name;
    private String file_name;
    
    
    
	/**
	 * 작성 : 조주현
	 * 내용
	 * - 검증 항목 관리를 위해 아래 컬럼 추가
	 */        
    
    private String eng_name;
    private int maxlistsize;	// 첨부할수 있는 파일 개수
    private String status;		// 해당항목 사용유무
    private String required;	// 필수 여부
    private String prefix; //첨부하는 파일 컬럼- 저장할때 구분 이름
    private String lastfilenum;
    private int attach_file_id_size;
    private String other_seq;
    
    
    private String pkgTitle;
	
	public String getPkgTitle() {
		return pkgTitle;
	}

	public void setPkgTitle(String pkgTitle) {
		this.pkgTitle = pkgTitle;
	}
    
    
	public int getAttach_file_id_size() {
		return attach_file_id_size;
	}
	public void setAttach_file_id_size(int attach_file_id_size) {
		this.attach_file_id_size = attach_file_id_size;
	}
	public int getMaxlistsize() {
		return maxlistsize;
	}
	public void setMaxlistsize(int maxlistsize) {
		this.maxlistsize = maxlistsize;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getLastfilenum() {
		return lastfilenum;
	}
	public void setLastfilenum(String lastfilenum) {
		this.lastfilenum = lastfilenum;
	}









	private List<CommonsMultipartFile> files;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getDrag() {
		return drag;
	}
	public void setDrag(String drag) {
		this.drag = drag;
	}
	public String getDrop() {
		return drop;
	}
	public void setDrop(String drop) {
		this.drop = drop;
	}
	public String getMaster_file_id() {
		return master_file_id;
	}
	public void setMaster_file_id(String master_file_id) {
		this.master_file_id = master_file_id;
	}
	public String getAttach_file_id() {
		return attach_file_id;
	}
	public void setAttach_file_id(String attach_file_id) {
		this.attach_file_id = attach_file_id;
	}
	public List<CommonsMultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<CommonsMultipartFile> files) {
		this.files = files;
	}
	public String getParent_tree_id() {
		return parent_tree_id;
	}
	public void setParent_tree_id(String parent_tree_id) {
		this.parent_tree_id = parent_tree_id;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getFile_extension() {
		return file_extension;
	}
	public void setFile_extension(String file_extension) {
		this.file_extension = file_extension;
	}
	public long getFile_size() {
		return file_size;
	}
	public void setFile_size(long file_size) {
		this.file_size = file_size;
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
	public String getOther_seq() {
		return other_seq;
	}
	public void setOther_seq(String other_seq) {
		this.other_seq = other_seq;
	}
	public String getEng_name() {
		return eng_name;
	}
	public void setEng_name(String eng_name) {
		this.eng_name = eng_name;
	}

    
    
}
