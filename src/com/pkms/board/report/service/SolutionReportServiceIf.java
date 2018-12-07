package com.pkms.board.report.service;

import com.pkms.board.report.model.SolutionReportModel;
import com.pkms.board.report.model.SolutionReportUserModel;
import com.pkms.sys.system.model.SystemUserModel;

import java.util.List;

public interface SolutionReportServiceIf {
	
	public List<?> readList(SolutionReportModel SRModel) throws Exception;
	
	public SolutionReportModel read(SolutionReportModel SRModel) throws Exception;
	
	public List<?> commentList(SolutionReportModel SRModel) throws Exception;
	
	public List<SolutionReportUserModel> srUserList(SolutionReportUserModel SRUserModel) throws Exception;
	public List<SolutionReportUserModel> srSosokList(SolutionReportUserModel SRUserModel) throws Exception;
	public List<SolutionReportUserModel> readSosokList(SolutionReportUserModel SRUserModel) throws Exception;
	
	public void create(SolutionReportModel SRModel) throws Exception;
	public void update(SolutionReportModel SRModel) throws Exception;
	public void delete(SolutionReportModel SRModel) throws Exception;
	public void complete(SolutionReportModel SRModel) throws Exception;
	/*
	 * Comment create, delete
	 */
	public void commentCreate(SolutionReportModel SRModel) throws Exception;
	public void commentDelete(SolutionReportModel SRModel) throws Exception;
	
	public void userYes(SolutionReportModel SRModel) throws Exception;
	public void sosokYes(SolutionReportModel SRModel) throws Exception;
	
	public String[] gettoInfos(List<SystemUserModel> systemUserList, List<SolutionReportUserModel> sosokMailList) throws Exception;
	
	public String readVuYn(SolutionReportModel SRModel) throws Exception;
	
	public List<SolutionReportUserModel> readMailList(SolutionReportUserModel SRUserModel) throws Exception;
	
	public void sendSms(SolutionReportModel SRModel, String user_id) throws Exception;
	public String[] cleanId(String [] user_id) throws Exception;
}
