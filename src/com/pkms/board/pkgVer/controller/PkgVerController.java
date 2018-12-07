package com.pkms.board.pkgVer.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import com.pkms.common.util.ResultUtil;

import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.board.pkgVer.model.PkgVerModel;
import com.pkms.board.pkgVer.service.PkgVerServiceIf;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.equipment.model.EquipmentUserModel;
import com.pkms.sys.system.dao.SystemDAO;
import com.pkms.sys.system.model.SystemUserModel.SYSTEM_USER_CHARGE_GUBUN;
import com.pkms.sys.system.service.SystemServiceIf;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.DateUtil;

@Controller
public class PkgVerController {
	
	@Resource(name = "SystemDAO")
	private SystemDAO systemDAO;
	
	@Resource(name = "PkgVerService")
	private PkgVerServiceIf pkgVerService;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;
	
	@Resource(name = "SystemService")
	private SystemServiceIf systemService;
	
	@RequestMapping(value = "/board/pkgVer/PkgVer_ReadList.do")
	public String readList(PkgVerModel pkgVerModel, Model model) throws Exception {
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(pkgVerModel);
		
		//기본 검색 기간 설정
	/*	if(!StringUtils.hasLength(pkgVerModel.getSearch_reg_date_start()) || !StringUtils.hasLength(pkgVerModel.getSearch_reg_date_end())  ){
			pkgVerModel.setSearch_reg_date_start(DateUtil.formatDateByMonth(DateUtil.format(), -1));
			pkgVerModel.setSearch_reg_date_end(DateUtil.dateFormat());
		}*/
	
		TreeMap<String, ArrayList<PkgVerModel>> pkgVerList = pkgVerService.readList(pkgVerModel);
		
		//List<PkgVerModel> pkgVerList = pkgVerService.readList(pkgVerModel);
		paginationInfo.setTotalRecordCount(pkgVerModel.getTotalCount());
		
		model.addAttribute("PkgVerModel",pkgVerModel);
		model.addAttribute("pkgVerList", pkgVerList);
		model.addAttribute("paginationInfo", paginationInfo);
		
	/*	SysModel sysModel = new SysModel();
		systemService.create(sysModel);
		
		List<SysModel> readSystemSeqList= systemDAO.readSystemSeq(sysModel);
		
		for(int i = 0; i < readSystemSeqList.size(); i++){
			PkgVerModel pkgVerSysSeqModel = new PkgVerModel();
			pkgVerSysSeqModel.setSystem_seq(readSystemSeqList.get(i).getSystem_seq());
			pkgVerService.create(pkgVerSysSeqModel);
		}
		*/
		return "/board/pkgVer/PkgVer_ReadList";
}
	
	@RequestMapping(value = "/board/pkgVer/PkgVer_Read.do")
	public String read(PkgVerModel pkgVerModel, Model model) throws Exception {
		PkgVerModel pkgVerModelData = new PkgVerModel();
		
		if(StringUtils.hasLength(pkgVerModel.getSystem_seq())){
			pkgVerModelData = pkgVerService.read(pkgVerModel);
		}
		
		pkgVerModelData.setSearch_reg_date_start(pkgVerModel.getSearch_reg_date_start());
		pkgVerModelData.setSearch_reg_date_end(pkgVerModel.getSearch_reg_date_end());
		pkgVerModelData.setSearch_system_seq(pkgVerModel.getSearch_system_seq());
		pkgVerModelData.setSearch_system_name(pkgVerModel.getSearch_system_name());
		pkgVerModelData.setPageIndex(pkgVerModel.getPageIndex());
		
		model.addAttribute("PkgVerModel", pkgVerModelData);
	
		return "/board/pkgVer/PkgVer_Read";
	}
	
	@RequestMapping(value = "/board/pkgVer/PkgVer_Create.do")
	public String create(PkgVerModel pkgVerModel, Model model) throws Exception {
		pkgVerService.create(pkgVerModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/pkgVer/PkgVer_Update.do")
	public String update(PkgVerModel pkgVerModel, Model model) throws Exception {		
		pkgVerService.update(pkgVerModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/pkgVer/PkgVer_Delete.do")
	public String delete(PkgVerModel pkgVerModel, Model model) throws Exception {		
		pkgVerService.delete(pkgVerModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/pkgVer/PkgVer_Ajax_User.do")
	public String read_user(PkgVerModel pkgVerModel, Model model) throws Exception {		
		SysModel sysModel = new SysModel();
		sysModel.setSystem_seq(pkgVerModel.getSystem_seq());

		sysModel = systemService.readUsersAppliedToSystem(sysModel);
		String sys_skt_user ="";
		String sys_bp_user ="";
		String skt_phone="";
		String bp_name="";
		String bp_phone="";
	 
		int cnt_skt =0;
		int cnt_bp =0;
		for(int i = 0; i < sysModel.getSystemUserList().size(); i++){
			if(SYSTEM_USER_CHARGE_GUBUN.BU.equals(sysModel.getSystemUserList().get(i).getCharge_gubun())){
				if(cnt_bp==0){
					sys_bp_user = sysModel.getSystemUserList().get(i).getUser_name();
					bp_phone = sysModel.getSystemUserList().get(i).getUser_phone();
					bp_name = sysModel.getSystemUserList().get(i).getBp_name();
				
				}
				else if(cnt_bp>0){
					sys_bp_user = sys_bp_user +","+ sysModel.getSystemUserList().get(i).getUser_name();
					bp_phone = bp_phone +","+ sysModel.getSystemUserList().get(i).getUser_phone();
					bp_name = bp_name +","+ sysModel.getSystemUserList().get(i).getBp_name();
			
				}
				cnt_bp++;
			}else{
				if(cnt_skt==0){
					sys_skt_user = sysModel.getSystemUserList().get(i).getUser_name();
					skt_phone = sysModel.getSystemUserList().get(i).getUser_phone();
					
				}
				else if(cnt_skt>0){
					sys_skt_user = sys_skt_user +","+ sysModel.getSystemUserList().get(i).getUser_name();
					skt_phone = skt_phone +","+ sysModel.getSystemUserList().get(i).getUser_phone();
					
				}
				cnt_skt++;
			}	
		}

		Collection<List<EquipmentUserModel>> col = sysModel.getEquipmentUserMap().values();
		
		for(List<EquipmentUserModel> user:col){
			for(int i = 0; i < user.size(); i++){					
				sys_skt_user = sys_skt_user + "," + user.get(i).getUser_name();
				skt_phone = skt_phone + "," + user.get(i).getUser_phone();
			}	
			
		}
		
		String[] skt_users = sys_skt_user.split(",");
		String[] bp_users = sys_bp_user.split(",");
		String[] bpName = bp_name.split(",");
		String[] bpPhone = bp_phone.split(",");
		String[] sktPhone = skt_phone.split(",");

		ArrayList<String> skt_users_list = new ArrayList<String>();
		ArrayList<String> bp_users_list = new ArrayList<String>();
		ArrayList<String> bpName_list = new ArrayList<String>();
		ArrayList<String> bpPhone_list = new ArrayList<String>();
		ArrayList<String> sktPhone_list = new ArrayList<String>();
		
		for(String i : skt_users){ // 수 만큼
			if(skt_users_list.contains(i)) continue; //리스트안에 중복자 제거
			if(!"".equals(i)){
				skt_users_list.add(i);//제거된 값 입력
			}
		}
		skt_users = (String[])skt_users_list.toArray(new String[skt_users_list.size()]); //Array 값 -> String 으로 변환
		
		for(String i : bp_users){ 
			if(bp_users_list.contains(i)) continue; 
			if(!"".equals(i)){
				bp_users_list.add(i);
			}
		}
		bp_users = (String[])bp_users_list.toArray(new String[bp_users_list.size()]); 
		
		for(String i : bpName){ 
			if(bpName_list.contains(i)) continue; 
			if(!"".equals(i)){
				bpName_list.add(i);
			}
		}
		bpName = (String[])bpName_list.toArray(new String[bpName_list.size()]); 
		
		for(String i : bpPhone){ 
			if(bpPhone_list.contains(i)) continue; 
			if(!"".equals(i)){
				bpPhone_list.add(i);
			}
		}
		bpPhone = (String[])bpPhone_list.toArray(new String[bpPhone_list.size()]); 
		
		for(String i : sktPhone){ 
			if(sktPhone_list.contains(i)) continue;
			if(!"".equals(i)){
				sktPhone_list.add(i);
			}
		}
		sktPhone = (String[])sktPhone_list.toArray(new String[sktPhone_list.size()]); 
		
		int cnt = 0;
		
		for(int i = 0; i < skt_users.length; i++){
			if(!"".equals(skt_users[i]) && skt_users[i] != null){
				if(cnt == 0){
					pkgVerModel.setSys_skt_user(skt_users[i]);
				} else{
					pkgVerModel.setSys_skt_user(pkgVerModel.getSys_skt_user()+", "+skt_users[i]);
				}
				cnt++;
			}
		}
		
		cnt = 0;	
		for(int i = 0; i < bp_users.length; i++){
			if(!"".equals(bp_users[i]) && bp_users[i] != null){
				if(cnt==0){
					pkgVerModel.setSys_bp_user(bp_users[i]);
				} else{
					pkgVerModel.setSys_bp_user(pkgVerModel.getSys_bp_user()+", "+bp_users[i]);
				}
				cnt++;
			}
		}
		
		cnt = 0;
		for(int i = 0; i < bpName.length; i++){
			if(!"".equals(bpName[i]) && bpName[i] != null){
				if(cnt==0){
					pkgVerModel.setBp_name(bpName[i]);
				} else{
					pkgVerModel.setBp_name(pkgVerModel.getBp_name()+", " +bpName[i]);
				}
				cnt++;
			}
		}
			
		cnt = 0;
		for(int i = 0; i < bpPhone.length; i++){
			if(!"".equals(bpPhone[i]) && bpPhone[i] != null){
				if(cnt==0){
					pkgVerModel.setBp_phone(bpPhone[i]);
				} else{
					pkgVerModel.setBp_phone(pkgVerModel.getBp_phone()+", "+bpPhone[i]);
					
				}
				cnt++;
			}
		}
		
		cnt = 0;
		for(int i = 0; i < sktPhone.length; i++){
			if(!"".equals(sktPhone[i]) && sktPhone[i] != null){
				if(cnt==0){
					pkgVerModel.setSkt_phone(sktPhone[i]);
				} else{
					pkgVerModel.setSkt_phone(pkgVerModel.getSkt_phone()+", "+sktPhone[i]);
				}
				cnt++;
			}
		}
	
		return ResultUtil.handleSuccessResultParam(model, pkgVerModel.getSys_skt_user(),pkgVerModel.getSys_bp_user(),pkgVerModel.getBp_name(),
				pkgVerModel.getBp_phone(), pkgVerModel.getSkt_phone());
	}
	
	
}