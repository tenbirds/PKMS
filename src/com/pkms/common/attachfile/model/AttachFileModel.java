package com.pkms.common.attachfile.model;

import java.io.Serializable;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 파일정보 처리를 위한 모델 클래스
 * 
 */
public class AttachFileModel extends CommonsMultipartFile implements Serializable {

	public AttachFileModel(FileItem fileItem) {
		super(fileItem);
		fileMode = true;
	}

	public AttachFileModel() {
		super(new DiskFileItem("", "", false, "", 0, null) {
			/**
			 * Serial Version UID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public long getSize() {
				return 0L;
			}
		});
		fileMode = false;
	}

	private boolean fileMode;

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	
	private String fieldName = "";
	
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	private boolean delete = false;
	
	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	/**
	 * 
	 */
	public String master_file_id = "";

	/**
	 * 첨부파일 아이디
	 */
	public String attach_file_id = "";

	/**
	 * 파일확장자
	 */
	public String file_extension = "";

	/**
	 * 파일크기
	 */
	public long file_size = 0L;

	/**
	 * 파일저장경로
	 */
	public String file_path = "";

	/**
	 * 원파일명
	 */
	public String file_org_name = "";

	/**
	 * 저장파일명
	 */
	public String file_name = "";
	
	/**
	 * iwcs_id_check
	 */
	public String empno;
	
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

	public String getFile_extension() {
		if (fileMode) {
			int index = super.getOriginalFilename().lastIndexOf(".");
			return super.getOriginalFilename().substring(index + 1);
		} else {
			return file_extension;
		}
	}

	public void setFile_extension(String file_extension) {
		this.file_extension = file_extension;
	}

	public long getFile_size() {
		if (fileMode) {
			return super.getSize();
		} else {
			return this.file_size;
		}
	}

	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_org_name() {
		if (fileMode) {
			return super.getOriginalFilename();
		} else {
			return file_org_name;
		}
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

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	
	private String file_type;
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	
	private CommonsMultipartFile file;
	public CommonsMultipartFile getFile() {
		return file;
	}
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

	private String parent_tree_id;


	public String getParent_tree_id() {
		return parent_tree_id;
	}

	public void setParent_tree_id(String parent_tree_id) {
		this.parent_tree_id = parent_tree_id;
	}
	
	private String user_id;
	private String user_nm;


	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	
	
}
