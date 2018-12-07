package com.pkms.tempmg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.service.PkgServiceIf;
import com.pkms.tempmg.dao.TempmgDAO;
import com.pkms.tempmg.model.TempmgModel;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ExcelUtil;

/**
 * 
 * 
 * 템플릿 service
 * 
 * @author : 009
 * @Date : 2012. 4. 20
 * 
 */
@Service("TempmgService")
public class TempmgService implements TempmgServiceIf {

	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;
	
	@Resource(name = "TempmgDAO")
	private TempmgDAO tempmgDAO;

	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;

	@Override
	public TempmgModel create(TempmgModel tempmgModel) throws Exception {
		tempmgModel.setUse_yn("N");
		String new_tpl_seq = tempmgDAO.create(tempmgModel);
//		List<?> tempmgModelList = tempmgColumnService.readList(tempmgModel);
//		String tpl_seq = null;
//		TempmgModel readModel;
		
		// create는 TempmgColumn 컴포넌트의 롤로 보고 select-insert문을 사용하지 않음
//		for(int i = 0; i < tempmgModelList.size(); i++) {
//			readModel = (TempmgModel) tempmgModelList.get(i);
//			
//			readModel.setUse_yn("N");
//			readModel.setTpl_seq(tpl_seq);
//			
//			tempmgColumnService.create(readModel);
//			if(tpl_seq == null) {
//				tpl_seq = readModel.getTpl_seq();
//				tempmgModel.setTpl_seq(tpl_seq);
//			}
//		}
		tempmgModel.setTpl_seq(new_tpl_seq);
		return this.read(tempmgModel);
	}
	
	@Override
	public TempmgModel read(TempmgModel fModel) throws Exception {
		TempmgModel rModel = tempmgDAO.read(fModel);
		
		if(rModel == null) {
			rModel = fModel;
		} else {
			//pkg검증요청에서 해당 템플릿버전의 사용된 개수
			if(StringUtils.hasLength(fModel.getTpl_seq())) {
				PkgModel pkgModel = new PkgModel();
				pkgModel.setTpl_seq(fModel.getTpl_seq());
				
				pkgService.readTotalCountTemplate(pkgModel);
				
				rModel.setPkgUseCnt(pkgModel.getTotalCountTemplate());
			}
		}

		return rModel;
	}

	@Override
	public List<TempmgModel> readList(TempmgModel tempmgModel) throws Exception {

		/*
		 * DAO에서 정보 목록 조회
		 */
		List<TempmgModel> resultList = tempmgDAO.readList(tempmgModel);

		/*
		 * 목록 전체 개수 조회
		 */
		int totalCount = tempmgDAO.readTotalCount(tempmgModel);
		tempmgModel.setTotalCount(totalCount);

		return resultList;
	}

	@Override
	public void update(TempmgModel tempmgModel) throws Exception {
		tempmgDAO.update(tempmgModel);
		
		if("Y".equals(tempmgModel.getUse_yn())) {
			tempmgModel.setUse_yn("N");
			tempmgDAO.updateOther(tempmgModel);
		}
	}

	@Override
	public String excelDownload(TempmgModel tempmgModel) throws Exception {
		//데이터
		tempmgModel.setPaging(false);
		List<?> readList = this.readList(tempmgModel);		
		//Excel 헤더
		String[] headers = new String[]{"tpl_ver", "use_yn","CNT" ,"reg_date"};
		
		//Excel 데이터 추출
		List<List<String>> excelDataList = ExcelUtil.extractExcelData((List<Object>) readList, headers);

		//파일 생성 후 다운로드할 파일명
		String downloadFileName = ExcelUtil.write(tempmgModel.EXCEL_FILE_NAME, propertyService.getString("Globals.fileStorePath"), new String[]{"템플릿 버전", "사용여부","가변 항목 개수", "등록일"}, excelDataList);
		
		return downloadFileName;
	}


}
