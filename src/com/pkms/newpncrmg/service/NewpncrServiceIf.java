package com.pkms.newpncrmg.service;

import java.util.List;

import com.pkms.newpncrmg.model.NewpncrModel;
import com.pkms.sys.common.model.SysRoadMapModel;


public interface NewpncrServiceIf {

	public void create(NewpncrModel newpncrModel) throws Exception;

	public NewpncrModel read(NewpncrModel newpncrModel) throws Exception;

	public List<?> readList(NewpncrModel newpncrModel) throws Exception;

	public void update(NewpncrModel newpncrModel) throws Exception;

	public void delete(NewpncrModel newpncrModel) throws Exception;

	/**
	 * 목록 엑셀다운로드
	 * @param newpncrModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String excelDownload(NewpncrModel newpncrModel) throws Exception;
	
	
	/**
	 * 답글 추가 0909 - ksy
	 */
	public void createRepl(NewpncrModel newpncrModel) throws Exception;
	
	public List<NewpncrModel> readListRepl(NewpncrModel newpncrModel) throws Exception;
	
	public void updateRepl(NewpncrModel newpncrModel) throws Exception;

	public List<SysRoadMapModel> readListChart(NewpncrModel newpncrModel);

	public List<List<SysRoadMapModel>> readListSubChart(NewpncrModel newpncrModel);

	public SysRoadMapModel readChart(SysRoadMapModel sysRoadMapModel);

	public void createRoadMapMapping(NewpncrModel newpncrModel,	SysRoadMapModel sysRoadMapModel);

	public List<List<SysRoadMapModel>> readListChartDetail(NewpncrModel newpncrModel);

	public List<String> systemSeqs(NewpncrModel newpncrModel);

	public List<SysRoadMapModel> systemList(SysRoadMapModel sysRoadMapModel);

	public void deleteRoadMapMapping(NewpncrModel newpncrModel);
}
