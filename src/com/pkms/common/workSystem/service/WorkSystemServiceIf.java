package com.pkms.common.workSystem.service;

import java.util.List;

import com.pkms.common.workSystem.model.WorkSystemModel;

public interface WorkSystemServiceIf {
	
	public void create_pkms_main(WorkSystemModel workSystemModel);
	
	public void create_pkms_sub(WorkSystemModel workSystemModel);
	public void create_pkms_sub_acc(WorkSystemModel workSystemModel);
	
	public void create_work_equipment(WorkSystemModel workSystemModel);
	
	public void update_pkms_main_del(WorkSystemModel workSystemModel);
	
	public void delete_Equipment(WorkSystemModel workSystemModel);
	
	// seq max값 가져오는 쿼리 추가
	public WorkSystemModel read_SeqMax_Main(WorkSystemModel workSystemModel);
	
	public WorkSystemModel read_SeqMax_Tango(WorkSystemModel workSystemModel);

	public WorkSystemModel read_SeqMax_Sub(WorkSystemModel workSystemModel);
	
	public List<WorkSystemModel> read_Seq_S(WorkSystemModel workSystemModel);
	
	public List<WorkSystemModel> read_Equipment(WorkSystemModel workSystemModel);
	
	public WorkSystemModel read_Sys_User_Info(WorkSystemModel workSystemModel);
	
	public WorkSystemModel read_Work_Info(WorkSystemModel workSystemModel);
	
	//WORKMANAGE_WORKKIND@DBM1_LINK 참조하여 작업분야 출력 쿼리추가 0422 강수연
	public WorkSystemModel readJOB_BUNYA(WorkSystemModel workSystemModel);
	
	//WORKMANAGE_WORKKIND@DBM1_LINK 참조하여 작업구분1 출력 쿼리추가 0422 강수연
	public WorkSystemModel readJOB_GUBUN(WorkSystemModel workSystemModel);
	
	//WORKMANAGE_WORKKIND@DBM1_LINK 참조하여 작업구분2 출력 쿼리추가 0422 강수연
	public WorkSystemModel readJOB_GUBUN1(WorkSystemModel workSystemModel);
	
	public WorkSystemModel readIns_Data(WorkSystemModel workSystemModel);
	
	public List<WorkSystemModel> readTeamWorkDate(WorkSystemModel workSystemModel);
	
	public List<WorkSystemModel> readTJ(WorkSystemModel workSystemModel);
	public List<WorkSystemModel> readTJacc(WorkSystemModel workSystemModel);
	public List<WorkSystemModel> sub_Team(WorkSystemModel workSystemModel);
	
	public WorkSystemModel read_IwcsViewSeq(WorkSystemModel workSystemModel);
	
	public WorkSystemModel read_TeamCode(WorkSystemModel workSystemModel);
	public WorkSystemModel read_TangoSeq(WorkSystemModel workSystemModel);
	public WorkSystemModel read_MainSeq(WorkSystemModel workSystemModel);
	public List<WorkSystemModel> noCreateList(WorkSystemModel workSystemModel);
	public List<WorkSystemModel> read21TeamWorkDate(WorkSystemModel workSystemModel);
	public void create_21work_equipment(WorkSystemModel workSystemModel);
	public WorkSystemModel readIns_Data_Pkg21(WorkSystemModel workSystemModel);
	public WorkSystemModel readEq_Pkg21(WorkSystemModel workSystemModel);
	public List<WorkSystemModel> readFileTango(WorkSystemModel workSystemModel);
	public List<WorkSystemModel> readEqAcc(WorkSystemModel workSystemModel);
	
}
