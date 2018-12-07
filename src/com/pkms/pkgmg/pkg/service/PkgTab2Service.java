package com.pkms.pkgmg.pkg.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.pkgmg.pkg.dao.PkgStatusDAO;
import com.pkms.pkgmg.pkg.dao.PkgTab2DAO;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgStatusModel;
import com.pkms.sys.common.model.SysRoadMapModel;
import com.pkms.sys.system.dao.SystemDAO;

/**
 * 첨부파일 관련 Service
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Service("PkgTab2Service")
public class PkgTab2Service implements PkgTab2ServiceIf {

	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;

	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;

	@Resource(name = "PkgTab2DAO")
	private PkgTab2DAO pkgTab2DAO;
	
	@Resource(name = "PkgStatusDAO")
	private PkgStatusDAO pkgStatusDAO;
	
	@Resource(name = "SystemDAO")
	private SystemDAO systemDAO;
	
	@Override
	public PkgModel read(PkgModel pkgModel) throws Exception {
		PkgModel pkgModelData = null;
		if (StringUtils.hasLength(pkgModel.getPkg_seq())) {

			pkgModelData = pkgService.read4Master(pkgModel);

			if (pkgModelData.getMaster_file_id() != null && !"".equals(pkgModelData.getMaster_file_id())) {
				// 첨부 파일 정보 세팅
				fileManageService.read(pkgModelData);
			}
		}

		return pkgModelData;
	}
	
	@Override
	public void update(PkgModel pkgModel) throws Exception {
		if ("".equals(pkgModel.getMaster_file_id())) {
			fileManageService.create(pkgModel, "PKG_");
		} else {
			fileManageService.update(pkgModel, "PKG_");
		}

		pkgTab2DAO.update(pkgModel);
		
		PkgStatusModel pkgStatusModel = new PkgStatusModel();
		pkgStatusModel.setSession_user_id(pkgModel.getSession_user_id());
		pkgStatusModel.setPkg_seq(pkgModel.getPkg_seq());
		pkgStatusModel.setStatus("3");
		pkgStatusModel.setCol1(pkgModel.getCol1());
		pkgStatusModel.setCol2(pkgModel.getCol2());
		pkgStatusModel.setCol3(pkgModel.getCol3());
		pkgStatusModel.setCol4(pkgModel.getCol4());
		pkgStatusModel.setCol5(pkgModel.getCol5());
		pkgStatusModel.setCol6(pkgModel.getCol6());
		pkgStatusModel.setCol7(pkgModel.getCol7());
		pkgStatusModel.setCol8(pkgModel.getCol8());
		pkgStatusModel.setCol9(pkgModel.getCol9());
		pkgStatusModel.setCol10(pkgModel.getCol10());
		pkgStatusModel.setCol11(pkgModel.getCol11());
		pkgStatusModel.setCol12(pkgModel.getCol12());
		pkgStatusModel.setCol13(pkgModel.getCol13());
		pkgStatusModel.setCol14(pkgModel.getCol14());
		pkgStatusModel.setCol15(pkgModel.getCol15());
		pkgStatusModel.setCol16(pkgModel.getCol16());
		pkgStatusModel.setCol17(pkgModel.getCol17());
		pkgStatusModel.setCol18(pkgModel.getCol18());
		pkgStatusModel.setCol19(pkgModel.getCol19());
		pkgStatusModel.setCol20(pkgModel.getCol20());
		pkgStatusModel.setCol21(pkgModel.getCol21());
		pkgStatusModel.setCol22(pkgModel.getCol22());
		pkgStatusModel.setCol23(pkgModel.getCol23());
		pkgStatusModel.setCol24(pkgModel.getCol24());
		pkgStatusModel.setCol25(pkgModel.getCol25());
		pkgStatusModel.setCol26(pkgModel.getCol26());
		pkgStatusModel.setCol27(pkgModel.getCol27());
		pkgStatusModel.setCol28(pkgModel.getCol28());
		pkgStatusModel.setCol29(pkgModel.getCol29());
		pkgStatusModel.setCol30(pkgModel.getCol30());
		pkgStatusModel.setCol31(pkgModel.getCol31());
		pkgStatusModel.setCol32(pkgModel.getCol32());
		pkgStatusModel.setCol33(pkgModel.getCol33());
		pkgStatusModel.setCol34(pkgModel.getCol34());
		pkgStatusModel.setCol35(pkgModel.getCol35());
		pkgStatusModel.setCol36(pkgModel.getCol36());
		pkgStatusModel.setCol37(pkgModel.getCol37());
		pkgStatusModel.setCol38(pkgModel.getCol38());
		pkgStatusModel.setCol39(pkgModel.getCol39());
		pkgStatusModel.setCol40(pkgModel.getCol40());
		pkgStatusModel.setCol41(pkgModel.getCol41());
		pkgStatusModel.setCol42(pkgModel.getCol42());
		pkgStatusModel.setCol43(pkgModel.getCol43());
		int updateCnt = pkgStatusDAO.update(pkgStatusModel);
		int updateCnt_dev =1;
		if("D".equals(pkgModel.getDev_yn_bak()) || "Y".equals(pkgModel.getDev_yn_bak())){
			pkgStatusModel.setStatus("22");
						
			updateCnt = pkgStatusDAO.update(pkgStatusModel);
		}
		

		if(updateCnt != 1 || updateCnt_dev != 1) {
			throw new Exception("error.biz.수정되지 않았습니다. 새로고침 후 다시 시도해보시기 바랍니다.\n지속적으로 동일한 에러가 발생하는 경우 관리자에게 문의하시기 바랍니다.");
		}
	}
	
	@Override
	public PkgModel roadMapUpdate(PkgModel pkgModel) throws Exception {
		SysRoadMapModel srmModel = new SysRoadMapModel();
		String roadmapseq = "";
		String pkgroadmapseq = "";
		
		srmModel.setSystem_seq(pkgModel.getSystem_seq());
		srmModel.setPkg_seq(pkgModel.getPkg_seq());
		srmModel.setReg_user(pkgModel.getSession_user_id());
		srmModel.setSession_user_id(pkgModel.getSession_user_id());
		
		//update
		if(!"".equals(pkgModel.getPkg_road_map_seq_04()) && pkgModel.getPkg_road_map_seq_04() != null){
			srmModel.setCode("04");
			srmModel.setContent(pkgModel.getComment_04());
			srmModel.setStart_date(pkgModel.getStart_date_04());
			srmModel.setEnd_date(pkgModel.getEnd_date_04());
			srmModel.setRoad_map_seq(pkgModel.getRoad_map_seq_04());
			srmModel.setSession_user_id(pkgModel.getSession_user_id());
			systemDAO.roadMapUpdate(srmModel);
			
			srmModel.setPkg_road_map_seq(pkgModel.getPkg_road_map_seq_04());
			srmModel.setTitle(pkgModel.getTitle());
			systemDAO.pkgRoadMapUpdate(srmModel);
		//create
		}else if(!"".equals(pkgModel.getStart_date_04()) && pkgModel.getStart_date_04() != null){
			srmModel.setCode("04");
			roadmapseq = systemDAO.roadMapSeqNext();
			pkgModel.setRoad_map_seq_04(roadmapseq);
			
			srmModel.setRoad_map_seq(roadmapseq);
			srmModel.setContent(pkgModel.getComment_04());
			srmModel.setStart_date(pkgModel.getStart_date_04());
			srmModel.setEnd_date(pkgModel.getEnd_date_04());
			
			systemDAO.createRoadMapPkg(srmModel);
			
			pkgroadmapseq = systemDAO.pkgRoadMapSeqNext();
			pkgModel.setPkg_road_map_seq_04(pkgroadmapseq);
			
			srmModel.setPkg_road_map_seq(pkgroadmapseq);
			srmModel.setTitle(pkgModel.getTitle());
			
			systemDAO.pkgCreateRoadMap(srmModel);
		}
		
		if(!"".equals(pkgModel.getPkg_road_map_seq_07()) && pkgModel.getPkg_road_map_seq_07() != null){
			srmModel.setCode("07");
			srmModel.setContent(pkgModel.getComment_07());
			srmModel.setStart_date(pkgModel.getStart_date_07());
			srmModel.setEnd_date(pkgModel.getEnd_date_07());
			srmModel.setRoad_map_seq(pkgModel.getRoad_map_seq_07());
			srmModel.setSession_user_id(pkgModel.getSession_user_id());
			systemDAO.roadMapUpdate(srmModel);
			
			srmModel.setPkg_road_map_seq(pkgModel.getPkg_road_map_seq_07());
			srmModel.setTitle(pkgModel.getTitle());
			systemDAO.pkgRoadMapUpdate(srmModel);
			
		}else if(!"".equals(pkgModel.getStart_date_07()) && pkgModel.getStart_date_07() != null){
			srmModel.setCode("07");
			roadmapseq = systemDAO.roadMapSeqNext();
			pkgModel.setRoad_map_seq_07(roadmapseq);
			
			srmModel.setRoad_map_seq(roadmapseq);
			srmModel.setContent(pkgModel.getComment_07());
			srmModel.setStart_date(pkgModel.getStart_date_07());
			srmModel.setEnd_date(pkgModel.getEnd_date_07());

			systemDAO.createRoadMapPkg(srmModel);
			
			
			pkgroadmapseq = systemDAO.pkgRoadMapSeqNext();
			pkgModel.setPkg_road_map_seq_07(pkgroadmapseq);

			srmModel.setPkg_road_map_seq(pkgroadmapseq);
			srmModel.setTitle(pkgModel.getTitle());
			
			systemDAO.pkgCreateRoadMap(srmModel);
		}
		
		if(!"".equals(pkgModel.getPkg_road_map_seq_08()) && pkgModel.getPkg_road_map_seq_08() != null){
			srmModel.setCode("08");
			srmModel.setContent(pkgModel.getComment_08());
			srmModel.setStart_date(pkgModel.getStart_date_08());
			srmModel.setEnd_date(pkgModel.getEnd_date_08());
			srmModel.setRoad_map_seq(pkgModel.getRoad_map_seq_08());
			srmModel.setSession_user_id(pkgModel.getSession_user_id());
			systemDAO.roadMapUpdate(srmModel);
			
			srmModel.setPkg_road_map_seq(pkgModel.getPkg_road_map_seq_08());
			srmModel.setTitle(pkgModel.getTitle());
			systemDAO.pkgRoadMapUpdate(srmModel);
			
		}else if(!"".equals(pkgModel.getStart_date_08()) && pkgModel.getStart_date_08() != null){
			srmModel.setCode("08");
			roadmapseq = systemDAO.roadMapSeqNext();
			pkgModel.setRoad_map_seq_08(roadmapseq);
			
			srmModel.setRoad_map_seq(roadmapseq);
			srmModel.setContent(pkgModel.getComment_08());
			srmModel.setStart_date(pkgModel.getStart_date_08());
			srmModel.setEnd_date(pkgModel.getEnd_date_08());
			
			systemDAO.createRoadMapPkg(srmModel);
			
			pkgroadmapseq = systemDAO.pkgRoadMapSeqNext();
			pkgModel.setPkg_road_map_seq_08(pkgroadmapseq);
			
			srmModel.setPkg_road_map_seq(pkgroadmapseq);
			srmModel.setTitle(pkgModel.getTitle());

			systemDAO.pkgCreateRoadMap(srmModel);
		}
		
		return pkgModel;
	}
	
}
