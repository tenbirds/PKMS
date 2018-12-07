package com.pkms.sys.system.service;

import java.util.List;

import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.sys.common.model.SysRoadMapMappingModel;
import com.pkms.sys.common.model.SysRoadMapModel;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.system.model.SystemFileModel;
import com.pkms.sys.system.model.SystemUserModel;

public interface SystemServiceIf {

	public SysModel create(SysModel sysModel) throws Exception;

	public SysModel read(SysModel sysModel) throws Exception;

	/**
	 * 시스템에 적용되어 있는 사용자 목록을 조회하여 SysModel의 list 객체에 담아 리턴.
	 * 
	 * @param sysModel
	 * @return
	 * @throws Exception
	 */
	public SysModel readUsersAppliedToSystem(SysModel sysModel) throws Exception;

	public List<?> readList(SysModel sysModel) throws Exception;

	public List<SysModel> readList4User(SysModel sysModel) throws Exception;
	/**
	 * 사용자에게 적용된 시스템 목록 조회
	 * 
	 * @param sysModel
	 * @return
	 * @throws Exception
	 */
	public List<?> readListAppliedToUser(SystemUserModel systemUserModel) throws Exception;

	public void update(SysModel sysModel) throws Exception;

	public void delete(SysModel sysModel) throws Exception;
	
	public List<SysModel> readSystemSeq(SysModel sysModel) throws Exception;

	public void roadMapcreate(SysRoadMapModel sysRoadMapModel) throws Exception;

	public List<SysRoadMapModel> roadMapList(SysModel sysModel);

	public SysRoadMapModel roadMapDetail(SysRoadMapModel sysRoadMapModel);

	public void roadMapUpdate(SysRoadMapModel sysRoadMapModel) throws Exception;

	public List<SysRoadMapMappingModel> roadMapMappingList(SysRoadMapModel sysRoadMapModel);

	public void roadMapMappingDelete(SysRoadMapMappingModel sysRoadMapMappingModel);

	public List<SysRoadMapModel> pkgRoadMapList(SysRoadMapModel sysRoadMapModel);
	
	public SysModel pkg_equipment_user(SysModel sysModel) throws Exception;
	
	public List<SystemFileModel> systemFileReadList(SystemFileModel systemFileModel) throws Exception;

	public void tree_file_add(SystemFileModel systemFileModel) throws Exception;
	
//	public void file_add(SystemFileModel systemFileModel) throws Exception;
	
	public void tree_file_delete(SystemFileModel systemFileModel) throws Exception;

	public List<SystemFileModel> tree_file_list(SystemFileModel systemFileModel);

	public int tree_file_move(SystemFileModel systemFileModel);

	public int tree_file_update(SystemFileModel systemFileModel);

	public List<SystemFileModel> systemeListReadList(SystemFileModel systemFileModel) throws Exception;

	public boolean pkg_tree_list_add(SystemFileModel systemFileModel) throws Exception;

	public int pkg_tree_list_idx(SystemFileModel systemFileModel) throws Exception;

	public boolean pkg_tree_list_update(SystemFileModel systemFileModel) throws Exception;

	public boolean pkg_tree_list_delete(SystemFileModel systemFileModel) throws Exception;

	public String new_file_add(SystemFileModel systemFileModel  ,String prefix) throws Exception;
	
	public String new_file_del(SystemFileModel systemFileModel) throws Exception;
	
//	public int engNameCheck(SystemFileModel systemFileModel) throws Exception;
	
}
