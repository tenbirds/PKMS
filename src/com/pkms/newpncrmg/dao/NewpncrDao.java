package com.pkms.newpncrmg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.newpncrmg.model.NewpncrModel;
import com.pkms.sys.common.model.SysRoadMapModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("NewpncrDao")
public class NewpncrDao  extends IbatisAbstractDAO{

	public String create(NewpncrModel newpncrModel) {
		return (String) create("NewpncrDao.create", newpncrModel);
	}

	public NewpncrModel read(NewpncrModel newpncrModel) {
		return (NewpncrModel) read("NewpncrDao.read", newpncrModel);
	}

	public void update(NewpncrModel newpncrModel) {
		update("NewpncrDao.update", newpncrModel);
	}

	public void delete(NewpncrModel newpncrModel) {
		delete("NewpncrDao.delete", newpncrModel);
	}

	public List<?> readList(NewpncrModel newpncrModel) {
		return readList("NewpncrDao.readList", newpncrModel);
	}

	public int readTotalCount(NewpncrModel newpncrModel) {
		return (Integer) getSqlMapClientTemplate().queryForObject("NewpncrDao.readTotalCount", newpncrModel);
	}

	public void stateInfo_update(NewpncrModel newpncrModel) {
		update("NewpncrDao.stateInfo_update", newpncrModel);
	}

	public NewpncrModel bpComment_read(NewpncrModel newpncrModel) {
		return (NewpncrModel) read("NewpncrDao.bpComment_read", newpncrModel);
	}

	/**
	 * 답글 추가 0909 - ksy
	 */
	public void createRepl(NewpncrModel newpncrModel) {
		create("NewpncrDao.createRepl", newpncrModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<NewpncrModel> readListRepl(NewpncrModel newpncrModel) throws Exception{
		return (List<NewpncrModel>) readList("NewpncrDao.readListRepl", newpncrModel);
	}
	
	public void updateRepl(NewpncrModel newpncrModel) {
		update("NewpncrDao.updateRepl", newpncrModel);
	}

	public List<SysRoadMapModel> readListChart(NewpncrModel newpncrModel) {
		return (List<SysRoadMapModel>) readList("NewpncrDao.readListChart", newpncrModel);
	}

	public SysRoadMapModel readChart(SysRoadMapModel sysRoadMapModel) {
		return (SysRoadMapModel)read("NewpncrDao.chartRead", sysRoadMapModel);
	}

	public void createRoadMapMapping(SysRoadMapModel sysRoadMapModel) {
		create("NewpncrDao.createRoadMapMapping", sysRoadMapModel);
	}

	public void updateRoadMapMapping(SysRoadMapModel sysRoadMapModel) {
		update("NewpncrDao.updateRoadMapMapping", sysRoadMapModel);
	}

	public List<String> chartMappingSystemSeqList(NewpncrModel newpncrModel) {
		
		return (List<String>) readList("NewpncrDao.chartMappingSystemSeqList", newpncrModel);
	}
	
	public List<SysRoadMapModel> systemList(SysRoadMapModel sysRoadMapModel){
		
		return (List<SysRoadMapModel>) readList("NewpncrDao.systemList", sysRoadMapModel);
	}

	public void deleteRoadMapMapping(NewpncrModel newpncrModel) {
		update("NewpncrDao.deleteRoadMapMapping", newpncrModel);
	}
	
}
