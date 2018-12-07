package com.pkms.board.report.dao;

import org.springframework.stereotype.Repository;

import com.wings.dao.IbatisAbstractDAO;
import com.pkms.board.report.model.SolutionReportModel;
import com.pkms.board.report.model.SolutionReportUserModel;

import java.util.List;

@Repository("SolutionReportDao")
public class SolutionReportDao extends IbatisAbstractDAO{
	
	public List<?> readList(SolutionReportModel SRModel) {
		return readList("SolutionReportDAO.readList", SRModel);
	}
	
	public int readTotalCount(SolutionReportModel SRModel) {
		return (Integer) getSqlMapClientTemplate().queryForObject("SolutionReportDAO.readTotalCount", SRModel);
	}

	public SolutionReportModel read(SolutionReportModel SRModel) {
		return (SolutionReportModel) read("SolutionReportDAO.read",SRModel);
	}
	
	public List<?> commentList(SolutionReportModel SRModel) {
		return readList("SolutionReportDAO.commentList", SRModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<SolutionReportUserModel> srUserList(SolutionReportUserModel SRUserModel) throws Exception {
		return (List<SolutionReportUserModel>) readList("SolutionReportDAO.srUserList", SRUserModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<SolutionReportUserModel> srSosokList(SolutionReportUserModel SRUserModel) throws Exception {
		return (List<SolutionReportUserModel>) readList("SolutionReportDAO.srSosokList", SRUserModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<SolutionReportUserModel> readSosokList(SolutionReportUserModel SRUserModel) throws Exception {
		return (List<SolutionReportUserModel>) readList("SolutionReportDAO.readSosokList", SRUserModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<SolutionReportUserModel> srSosokMailList(SolutionReportUserModel SRUserModel) throws Exception {
		return (List<SolutionReportUserModel>) readList("SolutionReportDAO.srSosokMailList", SRUserModel);
	}
	
	public String readSeq() {
		return (String) getSqlMapClientTemplate().queryForObject("SolutionReportDAO.readSeq");
	}
	
	public void create(SolutionReportModel SRModel){
		create("SolutionReportDAO.create", SRModel);
	}
	
	public void update(SolutionReportModel SRModel){
		update("SolutionReportDAO.update", SRModel);
	}
	
	public void delete(SolutionReportModel SRModel){
		update("SolutionReportDAO.delete", SRModel);
	}
	
	public void complete(SolutionReportModel SRModel){
		update("SolutionReportDAO.complete", SRModel);
	}
	
	public void userCreate(SolutionReportUserModel SRUserModel){
		create("SolutionReportDAO.userCreate", SRUserModel);
	}
	
	public void sosokCreate(SolutionReportUserModel SRUserModel){
		create("SolutionReportDAO.sosokCreate", SRUserModel);
	}
	
	public void userUpdate(SolutionReportUserModel SRUserModel){
		update("SolutionReportDAO.userUpdate", SRUserModel);
	}
	
	public void sosokUpdate(SolutionReportUserModel SRUserModel){
		update("SolutionReportDAO.sosokUpdate", SRUserModel);
	}
	
	public void userDelete(SolutionReportUserModel SRUserModel){
		delete("SolutionReportDAO.userDelete", SRUserModel);
	}
	public void sosokDelete(SolutionReportUserModel SRUserModel){
		delete("SolutionReportDAO.sosokDelete", SRUserModel);
	}
	
	/*
	 * Comment create, delete
	 */	
	public void commentCreate(SolutionReportModel SRModel){
		create("SolutionReportDAO.commentCreate", SRModel);
	}
	
	public void commentDelete(SolutionReportModel SRModel){
		update("SolutionReportDAO.commentDelete", SRModel);
	}
	
	public void userYes(SolutionReportModel SRModel){
		update("SolutionReportDAO.userYes", SRModel);
	}
	
	public void sosokYes(SolutionReportModel SRModel){
		update("SolutionReportDAO.sosokYes", SRModel);
	}
	
	public String readVuYn(SolutionReportModel SRModel) {
		return (String) getSqlMapClientTemplate().queryForObject("SolutionReportDAO.readVuYn", SRModel);
	}
	
	public String readTel(SolutionReportModel SRModel) {
		return (String) getSqlMapClientTemplate().queryForObject("SolutionReportDAO.readTel", SRModel);
	}
}
