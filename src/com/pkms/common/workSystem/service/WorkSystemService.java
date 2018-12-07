package com.pkms.common.workSystem.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.workSystem.dao.WorkSystemDao;
import com.pkms.common.workSystem.model.WorkSystemModel;

@Service("WorkSystemService")
public class WorkSystemService implements WorkSystemServiceIf {

	@Resource(name = "WorkSystemDao")
	private WorkSystemDao workSystemDao;
	
	//pkms_main 연동
	public void create_pkms_main(WorkSystemModel workSystemModel) {
		workSystemDao.create_pkms_main(workSystemModel);
		workSystemDao.create_pkms_main2(workSystemModel);
		
		WorkSystemModel fileModel = new WorkSystemModel();
		
		int seq = 0;
		fileModel = workSystemDao.read_SeqMax_File(fileModel);
		seq = fileModel.getSeq();
				
		//찾기
		fileModel.setMaster_file_id(workSystemModel.getMaster_file_id());
		List<WorkSystemModel>fileList = workSystemDao.readFileList(fileModel);
		
		//고정값 세팅 pkms_main - seq
		fileModel.setPkms_seq(workSystemModel.getSeq());
		
		//입력
		if((fileList != null) && (fileList.size() > 0)){
			for(WorkSystemModel file : fileList){
				seq++;
				fileModel.setSeq(seq);
				fileModel.setFile_name(file.getFile_name());
				fileModel.setFile_org_name(file.getFile_org_name());
				fileModel.setFile_path(file.getFile_path());
				
				workSystemDao.create_pkms_linkFile(fileModel);
			}
		}
	}
	
	public void create_pkms_sub(WorkSystemModel workSystemModel) {
		workSystemDao.create_pkms_sub(workSystemModel);
		
		WorkSystemModel read_SeqM_Tg_Sys = new WorkSystemModel();
		
		read_SeqM_Tg_Sys = workSystemDao.read_SeqM_Tg_Sys(read_SeqM_Tg_Sys);
		String[] equipment_name = null;
		int equipment_cnt = read_SeqM_Tg_Sys.getSeq()+1;
		equipment_name = workSystemModel.getFull_system_name().split(";");
		for(String full_system_name : equipment_name){
			if(!full_system_name.equals("") && full_system_name != null){
				workSystemModel.setSeq(equipment_cnt);
				workSystemModel.setFull_system_name(full_system_name);
				workSystemDao.create_pkms_target(workSystemModel);
				equipment_cnt++;
			}
		}
	}
	
	public void create_pkms_sub_acc(WorkSystemModel workSystemModel) {
		workSystemDao.create_pkms_sub(workSystemModel);
	}
	
	public void create_work_equipment(WorkSystemModel workSystemModel) {
		workSystemDao.create_work_equipment(workSystemModel);
	}
	
	public void update_pkms_main_del(WorkSystemModel workSystemModel) {
		workSystemDao.update_pkms_main_del(workSystemModel);
	}
	
	public void delete_Equipment(WorkSystemModel workSystemModel) {
		workSystemDao.delete_Equipment(workSystemModel);
	}

	@Override
	public WorkSystemModel read_SeqMax_Main(WorkSystemModel workSystemModel) {	
		return workSystemDao.read_SeqMax_Main(workSystemModel);
	}
	
	@Override
	public WorkSystemModel read_SeqMax_Tango(WorkSystemModel workSystemModel) {	
		return workSystemDao.read_SeqMax_Tango(workSystemModel);
	}

	@Override
	public WorkSystemModel read_SeqMax_Sub(WorkSystemModel workSystemModel) {
		return workSystemDao.read_SeqMax_Sub(workSystemModel);
	}
		
	@Override
	public List<WorkSystemModel> read_Seq_S(WorkSystemModel workSystemModel) {
		return workSystemDao.read_Seq_S(workSystemModel);
	}
	
	@Override
	public List<WorkSystemModel> read_Equipment(WorkSystemModel workSystemModel) {
		return workSystemDao.read_Equipment(workSystemModel);
	}
	
	@Override
	public WorkSystemModel read_Sys_User_Info(WorkSystemModel workSystemModel) {
		
		return workSystemDao.read_Sys_User_Info(workSystemModel);
	}
	
	@Override
	public WorkSystemModel read_Work_Info(WorkSystemModel workSystemModel) {
		return workSystemDao.read_Work_Info(workSystemModel);
	}
	
	@Override
	public WorkSystemModel readJOB_BUNYA(WorkSystemModel workSystemModel) {
		return workSystemDao.readJOB_BUNYA(workSystemModel);
	}
	
	@Override
	public WorkSystemModel readJOB_GUBUN(WorkSystemModel workSystemModel) {
		return workSystemDao.readJOB_GUBUN(workSystemModel);
	}
	
	@Override
	public WorkSystemModel readJOB_GUBUN1(WorkSystemModel workSystemModel) {
		return workSystemDao.readJOB_GUBUN1(workSystemModel);
	}

	@Override
	public WorkSystemModel readIns_Data(WorkSystemModel workSystemModel) {
		return workSystemDao.readIns_Data(workSystemModel);
	}
	
	@Override
	public WorkSystemModel readIns_Data_Pkg21(WorkSystemModel workSystemModel) {
		return workSystemDao.readIns_Data_Pkg21(workSystemModel);
	}
	
	@Override
	public WorkSystemModel readEq_Pkg21(WorkSystemModel workSystemModel) {
		return workSystemDao.readEq_Pkg21(workSystemModel);
	}
	
	@Override
	public List<WorkSystemModel> readTeamWorkDate(WorkSystemModel workSystemModel) {
		return workSystemDao.readTeamWorkDate(workSystemModel);
	}
	
	@Override
	public List<WorkSystemModel> readTJ(WorkSystemModel workSystemModel) {
		return  workSystemDao.readTJ(workSystemModel);
	}
	
	@Override
	public List<WorkSystemModel> readTJacc(WorkSystemModel workSystemModel) {
		return  workSystemDao.readTJacc(workSystemModel);
	}
	
	@Override
	public List<WorkSystemModel> sub_Team(WorkSystemModel workSystemModel) {
		return  workSystemDao.sub_Team(workSystemModel);
	}
	
	@Override
	public List<WorkSystemModel> readFileTango(WorkSystemModel workSystemModel) {
		return  workSystemDao.readFileTango(workSystemModel);
	}
	
	@Override
	public List<WorkSystemModel> readEqAcc(WorkSystemModel workSystemModel) {
		return  workSystemDao.readEqAcc(workSystemModel);
	}
	
	@Override
	public WorkSystemModel read_IwcsViewSeq(WorkSystemModel workSystemModel) {
		return workSystemDao.read_IwcsViewSeq(workSystemModel);
	}
	
	@Override
	public WorkSystemModel read_TeamCode(WorkSystemModel workSystemModel) {
		return workSystemDao.read_TeamCode(workSystemModel);
	}
	
	@Override
	public WorkSystemModel read_TangoSeq(WorkSystemModel workSystemModel) {
		return workSystemDao.read_TangoSeq(workSystemModel);
	}
	
	@Override
	public WorkSystemModel read_MainSeq(WorkSystemModel workSystemModel) {
		return workSystemDao.read_MainSeq(workSystemModel);
	}
	
	@Override
	public List<WorkSystemModel> noCreateList(WorkSystemModel workSystemModel) {
		return workSystemDao.noCreateList(workSystemModel);
	}
	
	@Override
	public List<WorkSystemModel> read21TeamWorkDate(WorkSystemModel workSystemModel) {
		return workSystemDao.read21TeamWorkDate(workSystemModel);
	}
	
	@Override
	public void create_21work_equipment(WorkSystemModel workSystemModel) {
		workSystemDao.create_21work_equipment(workSystemModel);
	}
}
