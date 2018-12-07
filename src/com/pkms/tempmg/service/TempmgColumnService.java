package com.pkms.tempmg.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pkms.common.attachfile.dao.AttachFileDAO;
import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.tempmg.dao.TempmgColumnDAO;
import com.pkms.tempmg.model.TempmgModel;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ExcelUtil;

/**
 * 
 * 
 * 템플릿 항목별 처리 service
 * 
 * @author : 009
 * @Date : 2012. 4. 20
 * 
 */
@Service("TempmgColumnService")
public class TempmgColumnService implements TempmgColumnServiceIf {

	@Resource(name = "TempmgColumnDAO")
	private TempmgColumnDAO tempmgColumnDAO;

	@Resource(name = "TempmgService")
	private TempmgServiceIf tempmgService;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;	
	
	private String propertyFilePathKey = "Globals.fileStorePath";
	
	@Override
	public TempmgModel create(TempmgModel tempmgModel) throws Exception {
		List<TempmgModel> tempmgModelList = new ArrayList<TempmgModel>();
		
		/*
		 * clone 주의
		 */
		if(StringUtils.hasLength(tempmgModel.getTpl_seq())) {//추가 등록
			TempmgModel readModel;
			
			// 기존 데이터 조회
			readModel = (TempmgModel) tempmgModel.clone();
			readModel.setPosition(readModel.PREV);
			List<TempmgModel> prevModelList = tempmgColumnDAO.readList(readModel);
			
			tempmgModel.setPosition(tempmgModel.VARIABLE);
			List<TempmgModel> fixedModelList = tempmgColumnDAO.readList(tempmgModel);
			fixedModelList.add(tempmgModel); //가변 마지막에 추가

			readModel = (TempmgModel) tempmgModel.clone();
			readModel.setPosition(readModel.NEXT);
			List<TempmgModel> nextModelList = tempmgColumnDAO.readList(readModel);

			// insert 데이터 세팅
			tempmgModelList.addAll(prevModelList);
			tempmgModelList.addAll(fixedModelList);
			tempmgModelList.addAll(nextModelList);
			
			// 기존 데이터 삭제
			TempmgModel deleteModel = new TempmgModel();
			deleteModel.setTpl_seq(tempmgModel.getTpl_seq());
			deleteModel.setOrd(null);
			tempmgColumnDAO.delete(tempmgModel);
		} else {//최초 등록 시
			
			//앞부분
			tempmgModel.setPosition(tempmgModel.PREV);
			_setColList(tempmgModelList, tempmgModel);
			
			//가변 추가
			TempmgModel fixedModel = (TempmgModel) tempmgModel.clone();

			fixedModel.setPosition(fixedModel.VARIABLE);
			tempmgModelList.add(fixedModel);

			//뒷부분
			tempmgModel.setPosition(tempmgModel.NEXT);
			_setColList(tempmgModelList, tempmgModel);
		}
		
		//첨부 파일 처리
		fileManageService.create(tempmgModel, "TEMPLATE_");
		
		// 전체 등록
		String tpl_seq = _create(tempmgModelList);
		tempmgModel.setTpl_seq(tpl_seq);
		
		return tempmgService.read(tempmgModel);
	}

	private void _setColList(List<TempmgModel> tempmgModelList, TempmgModel tempmgModel) throws Exception {
		List<String> colList = null;
		
		if(tempmgModel.PREV.equals(tempmgModel.getPosition())) {
			colList = tempmgModel.getFixPrevCol();
		} else if(tempmgModel.NEXT.equals(tempmgModel.getPosition())) {
			colList = tempmgModel.getFixNextCol();
		}
		
		TempmgModel addModel;
		
		/*
		 * clone 주의
		 */
		for(String col : colList) {
			addModel = (TempmgModel) tempmgModel.clone();
			
			addModel.setTitle(col);
			
			tempmgModelList.add(addModel);
		}
	}
	
	private String _create(List<TempmgModel> tempmgModelList) throws Exception {
		String tpl_seq = null;
		int i = 1;
		for(TempmgModel tempmgModel : tempmgModelList) {
			tempmgModel.setOrd(String.valueOf(i));
			if(StringUtils.hasLength(tempmgModel.getTpl_seq())) {//추가 시
				if(i == 1) {
					tpl_seq = tempmgModel.getTpl_seq();
				}
				if(!StringUtils.hasLength(tempmgModel.getSession_user_id())) {
					tempmgModel.setSession_user_id(tempmgModel.getReg_user());
				}
				
				// N
				// 재조회
				tempmgColumnDAO.createByTpl_seq(tempmgModel);
			} else {// 최초 등록 시
				tempmgModel.setMaster_file_id(tempmgModel.getFile1().master_file_id);
				
				if(i == 1) {
					tpl_seq = tempmgColumnDAO.create(tempmgModel);
				} else {
					tempmgModel.setTpl_seq(tpl_seq);
					tempmgColumnDAO.createByTpl_seq(tempmgModel);
				}
			}
			i++;
		}
		return tpl_seq;
	}
	
	@Override
	public TempmgModel read(TempmgModel fModel) throws Exception {
		TempmgModel rModel = tempmgColumnDAO.read(fModel);

		if(rModel == null) {
			rModel = fModel;
		}
		
		//첨부 파일 정보 세팅
		fileManageService.read(rModel);

		return rModel;
	}

	@Override
	public List<TempmgModel> readList(TempmgModel tempmgModel) throws Exception {

		/*
		 * DAO에서 정보 목록 조회
		 */
		List<TempmgModel> resultList = tempmgColumnDAO.readList(tempmgModel);

		return resultList;
	}

	@Override
	public void update(TempmgModel tempmgModel) throws Exception {
		//첨부 파일 정보 수정
		TempmgModel rModel = tempmgColumnDAO.read(tempmgModel);
		tempmgModel.setMaster_file_id(rModel.getMaster_file_id());
		fileManageService.update(tempmgModel, "TEMPLATE_");
		
		tempmgColumnDAO.update(tempmgModel);
	}

	@Override
	public void delete(TempmgModel tempmgModel) throws Exception {
		//첨부파일 삭제
		TempmgModel rModel = tempmgColumnDAO.read(tempmgModel);
		tempmgModel.setMaster_file_id(rModel.getMaster_file_id());
		fileManageService.delete(tempmgModel);
		
		tempmgColumnDAO.delete(tempmgModel);
	}

	@Override
	public String excelDownload(TempmgModel tempmgModel) throws Exception {
		String version = "unknow";
		tempmgModel.setPosition("");
		List<TempmgModel> tempmgList = this.readList(tempmgModel);
		
		for(TempmgModel temp : tempmgList) {
			version = temp.getTpl_ver();
			break;
		}
		
		List<String> headerList = new ArrayList<String>();
		
		for(TempmgModel headerModel : tempmgList) {
			headerList.add(headerModel.getTitle());
		}
		
		//파일 생성 후 다운로드할 파일명
		return ExcelUtil.write4Template("PKMS_보완적용내역_템플릿_" + version, propertyService.getString(propertyFilePathKey), headerList);
	}
	
	@Override
	public String newExcelDownload(TempmgModel tempmgModel) throws Exception {
		String version = "unknow";
		String file_path = "";
		String file_name = "";
		String file_org_name = "";
		
		tempmgModel.setPosition("");
		List<TempmgModel> tempmgList = this.readList(tempmgModel);
		
		for(TempmgModel temp : tempmgList) {
			version = temp.getTpl_ver();
			file_path = temp.getFile_path();
			file_name = temp.getFile_name();
			file_org_name = temp.getFile_org_name();
			break;
		}
		
//		file_name = "PKMS_보완적용내역_템플릿_" + version + ".xls";
		
		return file_name+"|"+file_org_name+"|"+file_path;
	}
}
