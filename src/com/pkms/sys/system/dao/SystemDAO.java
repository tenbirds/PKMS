package com.pkms.sys.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.sys.common.model.SysRoadMapMappingModel;
import com.pkms.sys.common.model.SysRoadMapModel;
import com.pkms.sys.system.model.SystemFileModel;
import com.pkms.sys.common.model.SysModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * 
 * 시스템 정보를 위한 DAO<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Repository("SystemDAO")
public class SystemDAO extends IbatisAbstractDAO {

	/**
	 * 시스템 정보를 DB에 생성
	 * 
	 * @param SysModel
	 *            - 등록 정보 모델
	 * @return String - 등록 결과
	 * @throws Exception
	 */
	public String create(SysModel model) throws Exception {
		return (String) create("SystemDAO.create", model);
	}

	/**
	 * 시스템 정보를 DB에 수정
	 * 
	 * @param SysModel
	 *            - 수정 정보 모델
	 * @exception Exception
	 */
	public void update(SysModel model) throws Exception {
		update("SystemDAO.update", model);
	}

	/**
	 * 시스템 정보를 DB에서 삭제
	 * 
	 * @param SysModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete(SysModel model) throws Exception {
		delete("SystemDAO.delete", model);
	}

	/**
	 * 시스템 정보 정보를 DB에서 조회
	 * 
	 * @param SysModel
	 *            - 조회 대상 모델
	 * @return SysModel - 조회 결과 모델
	 * @throws Exception
	 */
	public SysModel read(SysModel model) throws Exception {
		return (SysModel) read("SystemDAO.read", model);
	}

	/**
	 * 시스템 정보 정보 목록을 DB에서 조회
	 * 
	 * @param SysModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readList(SysModel model) throws Exception {
		return readList("SystemDAO.readList", model);
	}

	/**
	 * 해당 사용자가 담당으로 지정되어 있는 시스템 목록 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<SysModel> readList4User(SysModel model) throws Exception {
		return (List<SysModel>) readList("SystemDAO.readList4User", model);
	}
	
	public String readNextSeq() {
		return (String) getSqlMapClientTemplate().queryForObject("SystemDAO.NextSeq");
	}
	
	public List<SysModel> readSystemSeq(SysModel sysmodel) throws Exception{
		return (List<SysModel>) readList("SystemDAO.readSystemSeq", sysmodel);
	}

	public void roadMapCreate(SysRoadMapModel sysRoadMapModel) {
		create("SystemDAO.createRoadMap", sysRoadMapModel);
	}
	
	public void createRoadMapPkg(SysRoadMapModel sysRoadMapModel) {
		create("SystemDAO.createRoadMapPkg", sysRoadMapModel);
	}
	
	public void pkgCreateRoadMap(SysRoadMapModel sysRoadMapModel) {
		create("SystemDAO.pkgCreateRoadMap", sysRoadMapModel);
	}

	public List<SysRoadMapModel> roadMapList(SysModel sysModel) {
		return (List<SysRoadMapModel>) readList("SystemDAO.roadMapList", sysModel);
	}

	public SysRoadMapModel roadMapDetail(SysRoadMapModel sysRoadMapModel) {
		return (SysRoadMapModel) read("SystemDAO.roadMapDetail",sysRoadMapModel);
	}

	public void roadMapUpdate(SysRoadMapModel sysRoadMapModel) {
		update("SystemDAO.updateRoadMap", sysRoadMapModel);
	}
	
	public void pkgRoadMapUpdate(SysRoadMapModel sysRoadMapModel) {
		update("SystemDAO.updatePkgRoadMap", sysRoadMapModel);
	}

	public List<SysRoadMapMappingModel> roadMapMappingList(SysRoadMapModel sysRoadMapModel) { 
		return (List<SysRoadMapMappingModel>) readList("SystemDAO.roadMapMappingList", sysRoadMapModel);
	}

	public void roadMapMappingDelete(SysRoadMapMappingModel sysRoadMapMappingModel) {
		update("SystemDAO.roadMapMappingDelete",sysRoadMapMappingModel);
	}
	
	public String roadMapSeqNext() throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("SystemDAO.roadMapSeqNext");
	}
	
	public String pkgRoadMapSeqNext() throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("SystemDAO.roadMapSeqNext");
	}
	
	public SysRoadMapModel readPkgEquipmentDate(SysRoadMapModel sysRoadMapModel) throws Exception {
		return (SysRoadMapModel) read("SystemDAO.readPkgEquipmentDate", sysRoadMapModel);
	}
	
	public List<SysRoadMapModel> readPkgRoadMapSeqList(SysRoadMapModel sysRoadMapModel) throws Exception {
		return (List<SysRoadMapModel>) readList("SystemDAO.readPkgRoadMapSeqList", sysRoadMapModel);
	}
	
	public void deleteRoadMap(SysRoadMapModel sysRoadMapModel) throws Exception {
		delete("SystemDAO.deleteRoadMap", sysRoadMapModel);
	}
	
	public void pkgDeleteRoadMap(SysRoadMapModel sysRoadMapModel) throws Exception {
		delete("SystemDAO.pkgDeleteRoadMap", sysRoadMapModel);
	}
	
	public List<SysRoadMapModel> pkgRoadMapList(SysRoadMapModel sysRoadMapModel) { 
		return (List<SysRoadMapModel>) readList("SystemDAO.pkgRoadMapList", sysRoadMapModel);
	}
	
	public List<SysRoadMapModel> pkgRoadMaps(SysRoadMapModel sysRoadMapModel) { 
		return (List<SysRoadMapModel>) readList("SystemDAO.pkgRoadMaps", sysRoadMapModel);
	}
	
	
	public List<SystemFileModel> systemFileReadList(SystemFileModel systemFileModel){
		return (List<SystemFileModel>) readList("SystemDAO.systemFileReadList",systemFileModel);
	}

	public List<SystemFileModel> systemeListReadList(SystemFileModel systemFileModel){
		return (List<SystemFileModel>) readList("SystemDAO.systemeListReadList",systemFileModel);
	}
	
	
	public void pkg_tree_list_add(SystemFileModel systemFileModel) {
		 create("SystemDAO.pkg_tree_list_add", systemFileModel);		
	}
	
	
	public void pkg_tree_list_update(SystemFileModel systemFileModel) {
			update("SystemDAO.pkg_tree_list_update", systemFileModel);
	}
	
	public int pkg_tree_list_idx(SystemFileModel systemFileModel) {
		return (Integer) read("SystemDAO.pkg_tree_list_idx",systemFileModel );
	}

	public void pkg_tree_list_delete(SystemFileModel systemFileModel) {
		delete("SystemDAO.pkg_tree_list_delete", systemFileModel);
	}
	
	public List<SystemFileModel> tree_file_list(SystemFileModel systemFileModel) {
		return (List<SystemFileModel>) readList("SystemDAO.tree_file_list", systemFileModel);
	}

}
