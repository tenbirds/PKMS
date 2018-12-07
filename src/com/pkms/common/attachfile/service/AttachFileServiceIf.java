package com.pkms.common.attachfile.service;

import java.util.List;

import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.sys.system.model.SystemFileModel;
import com.pkms.tempmg.model.DocumentmgModel;


/**
 * 파일정보의 관리를 위한 서비스 인터페이스
 * 
 */
public interface AttachFileServiceIf {

	/**
	 * 해당 모델에 대한 첨부 파일을 생성.
	 * 
	 * @param object
	 * @throws Exception
	 */
	public void create(Object object, String prefix) throws Exception;

	/**
	 * 해당 모델에 대한 첨부 파일을 조회하여 해당 모델에 세팅
	 * 
	 * @param object
	 * @throws Exception
	 */
	public void read(Object object) throws Exception;

	/**
	 * 해당 모델들에 대한 첨부 파일들을 조회하여 해당 모델들에 세팅
	 * 
	 * @param object
	 * @throws Exception
	 */
//	public void readList(List<?> objects) throws Exception;

	/**
	 * 해당 모델에 대한 첨부 파일을 수정
	 * 
	 * @param object
	 * @throws Exception
	 */
	public void update(Object object, String prefix) throws Exception;

	/**
	 * 해당 모델에 대한 첨부 파일을 삭제
	 * 
	 * @param object
	 * @throws Exception
	 */
	public void delete(Object object) throws Exception;

	/**
	 * 기 등록된 첨부파일 전체를 복사(새로운 master_file_id 생성)
	 * @param object
	 * @param prefix
	 * @throws Exception
	 */
	public void copy(Object object) throws Exception;

	public boolean readId(AttachFileModel attachFileModel) throws Exception;

	public void tree_file_add(SystemFileModel systemFileModel, String prefix) throws Exception;
	public void tree_file_delete(SystemFileModel systemFileModel) throws Exception;

	public int tree_file_move(SystemFileModel systemFileModel);

	public int tree_file_update(SystemFileModel systemFileModel);
	
	public String new_file_add(SystemFileModel systemFileModel, String prefix) throws Exception;
	
	public String new_file_del(SystemFileModel systemFileModel) throws Exception;

//	public String doc_file_add(DocumentmgModel documentmgModel, String prefix) throws Exception;
	public String doc_file_add(SystemFileModel systemfilemodel, String prefix) throws Exception;
	
	
	public String doc_file_del(DocumentmgModel documentmgModel,String prefix) throws Exception;
}
