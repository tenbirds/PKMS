package com.pkms.common.workSystem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.common.workSystem.model.WorkSystemModel;

import com.wings.dao.IbatisAbstractDAO;

@Repository("WorkSystemDao")
public class WorkSystemDao extends IbatisAbstractDAO {

	//iwcs-update
	public void create_pkms_main(WorkSystemModel workSystemModel) {
		create("WorkSystemDao.create_pkms_main", workSystemModel);
	}
	
	//iwcs-update
	public void create_pkms_main2(WorkSystemModel workSystemModel) {
		create("WorkSystemDao.create_pkms_main2", workSystemModel);
	}
		
	//iwcs-update
	public void create_pkms_sub(WorkSystemModel workSystemModel) {
		create("WorkSystemDao.create_pkms_sub", workSystemModel);
	}
	
	//iwcs-update
	public void create_pkms_target(WorkSystemModel workSystemModel) {
		create("WorkSystemDao.create_pkms_target", workSystemModel);
	}
	
	public void create_work_equipment(WorkSystemModel workSystemModel) {
		create("WorkSystemDao.create_work_equipment", workSystemModel);
	}

	//iwcs-update
	public void update_pkms_main_del(WorkSystemModel workSystemModel) {
		update("WorkSystemDao.update_pkms_main_del", workSystemModel);
	}
	
	public void delete_Equipment(WorkSystemModel workSystemModel) {
		delete("WorkSystemDao.delete_Equipment", workSystemModel);
	}
	
	//// seq max값 가져오는 쿼리 추가
	public WorkSystemModel read_SeqMax_Main(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.read_SeqMax_Main", workSystemModel);
	}
	
	public WorkSystemModel read_SeqMax_Tango(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.read_SeqMax_Tango", workSystemModel);
	}
	
	//// seq max값 가져오는 쿼리 추가
	public WorkSystemModel read_SeqM_Tg_Sys(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.read_SeqM_Tg_Sys", workSystemModel);
	}
	
	//master_seq 값 가져오는 쿼리 추가
	public WorkSystemModel read_SeqMax_Sub(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.read_SeqMax_Sub", workSystemModel);
	}
	
	public WorkSystemModel read_SeqMax_File(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.read_SeqMax_File", workSystemModel);
	}
	
	public List<WorkSystemModel> read_Seq_S(WorkSystemModel workSystemModel) {
		return (List<WorkSystemModel>) readList("WorkSystemDao.read_Seq_S", workSystemModel);
	}
	
	public List<WorkSystemModel> read_Equipment(WorkSystemModel workSystemModel) {
		return (List<WorkSystemModel>) readList("WorkSystemDao.read_Equipment", workSystemModel);
	}
	
	public WorkSystemModel read_Sys_User_Info(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.read_Sys_User_Info", workSystemModel);
	}
	
	//iwcs-update
	public WorkSystemModel read_Work_Info(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.read_Work_Info", workSystemModel);
	}
	
	public WorkSystemModel readJOB_BUNYA(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.readJOB_BUNYA", workSystemModel);
	}

	public WorkSystemModel readJOB_GUBUN(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.readJOB_GUBUN", workSystemModel);
	}
	
	public WorkSystemModel readJOB_GUBUN1(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.readJOB_GUBUN1", workSystemModel);
	}
	
	public WorkSystemModel readIns_Data(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.readIns_Data", workSystemModel);
	}
	
	public WorkSystemModel readIns_Data_Pkg21(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.readIns_Data_Pkg21", workSystemModel);
	}
	
	public WorkSystemModel readEq_Pkg21(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.readEq_Pkg21", workSystemModel);
	}
	
	public List<WorkSystemModel> readTeamWorkDate(WorkSystemModel workSystemModel) {
		return (List<WorkSystemModel>) readList("WorkSystemDao.readTeamWorkDate", workSystemModel);
	}
	
	public List<WorkSystemModel> readTJ(WorkSystemModel workSystemModel) {
		return (List<WorkSystemModel>) readList("WorkSystemDao.readTJ", workSystemModel);
	}
	
	public List<WorkSystemModel> readTJacc(WorkSystemModel workSystemModel) {
		return (List<WorkSystemModel>) readList("WorkSystemDao.readTJacc", workSystemModel);
	}
	
	public List<WorkSystemModel> sub_Team(WorkSystemModel workSystemModel) {
		return (List<WorkSystemModel>) readList("WorkSystemDao.sub_Team", workSystemModel);
	}
	
	public List<WorkSystemModel> readFileTango(WorkSystemModel workSystemModel) {
		return (List<WorkSystemModel>) readList("WorkSystemDao.readFileTango", workSystemModel);
	}
	
	public List<WorkSystemModel> readEqAcc(WorkSystemModel workSystemModel) {
		return (List<WorkSystemModel>) readList("WorkSystemDao.readEqAcc", workSystemModel);
	}
	
	public List<WorkSystemModel> readFileList(WorkSystemModel workSystemModel) {
		return (List<WorkSystemModel>) readList("WorkSystemDao.readFileList", workSystemModel);
	}
	
	public void create_pkms_linkFile(WorkSystemModel workSystemModel) {
		create("WorkSystemDao.create_pkms_linkFile", workSystemModel);
	}
	
	//master_seq 값 가져오는 쿼리 추가
	public WorkSystemModel read_IwcsViewSeq(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.read_IwcsViewSeq", workSystemModel);
	}
	
	public WorkSystemModel read_TeamCode(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.read_TeamCode", workSystemModel);
	}
	
	public WorkSystemModel read_TangoSeq(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.read_TangoSeq", workSystemModel);
	}
	
	public WorkSystemModel read_MainSeq(WorkSystemModel workSystemModel){
		return (WorkSystemModel) read("WorkSystemDao.read_MainSeq", workSystemModel);
	}
	
	public List<WorkSystemModel> noCreateList(WorkSystemModel workSystemModel) {
		return (List<WorkSystemModel>) readList("WorkSystemDao.noCreateList", workSystemModel);
	}
	
	public List<WorkSystemModel> read21TeamWorkDate(WorkSystemModel workSystemModel) {
		return (List<WorkSystemModel>) readList("WorkSystemDao.read21TeamWorkDate", workSystemModel);
	}
	
	public void create_21work_equipment(WorkSystemModel workSystemModel) {
		create("WorkSystemDao.create_21work_equipment", workSystemModel);
	}
}
