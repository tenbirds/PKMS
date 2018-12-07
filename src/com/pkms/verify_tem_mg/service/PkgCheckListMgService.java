package com.pkms.verify_tem_mg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.verify_tem_mg.dao.PkgCheckListMgDao;
import com.pkms.verify_tem_mg.model.PkgCheckListManagerModel;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ExcelUtil;

@Service("PkgCheckListMgService")
public class PkgCheckListMgService implements PkgCheckListMgServiceIf{
	
	@Resource(name = "PkgCheckListMgDao")
	private PkgCheckListMgDao pkgCheckListMgDao;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	
	@Override
	public List<?> readList(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception {
		
		List<?> resultList = pkgCheckListMgDao.readList(pkgCheckListManagerModel);
		
		int totalCount = pkgCheckListMgDao.readTotalCount(pkgCheckListManagerModel);
		pkgCheckListManagerModel.setTotalCount(totalCount);
		
		return resultList;
	}
	
	
	@Override
	public List<?> codeList(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception {
		List<?> resultList = pkgCheckListMgDao.codeList(pkgCheckListManagerModel);
		return resultList;
	}
	
	
	@Override
	public PkgCheckListManagerModel readone(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception {
		return pkgCheckListMgDao.readone(pkgCheckListManagerModel);
	}
	
	
	
	public PkgCheckListManagerModel insert(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception{
		PkgCheckListManagerModel verifySeq = pkgCheckListMgDao.insert(pkgCheckListManagerModel);
		return pkgCheckListManagerModel;
	}
	
	
	public void update(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception{
		pkgCheckListMgDao.update(pkgCheckListManagerModel);
	}
	
	public void delete(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception{
		pkgCheckListMgDao.delete(pkgCheckListManagerModel);
	}
	
	
	@Override
	public String excelDownload(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception {
		//데이터
		pkgCheckListManagerModel.setPaging(false);
		List<?> readList = pkgCheckListMgDao.readList(pkgCheckListManagerModel);
		
		//Excel 헤더
		String[] headers = new String[]{"CHK_SEQ", "CHK_TYPE", "CHK_TYPE_NAME", "STATUS", "TITLE", "CHK_CONTENT", "REG_USER","REG_USER_NAME", "REG_DATE", "UPDATE_USER", "UPDATE_USER_NAME", "UPDATE_DATE", "ANSWER_TYPE", "POSITION"};
		
		//Excel 데이터 추출
		@SuppressWarnings("unchecked")
		List<List<String>> excelDataList = ExcelUtil.extractExcelData((List<Object>) readList, headers);

		//파일 생성 후 다운로드할 파일명
		String downloadFileName = ExcelUtil.write(pkgCheckListManagerModel.EXCEL_FILE_NAME, propertyService.getString("Globals.fileStorePath"),
				new String[]{"번호","사용분류코드","사용분류","상태(사용/미사용)","제목(내용)","설명","등록ID","등록자", "등록일", "수정ID", "수정자", "수정일", "처리결과", "가변구분(S, F, E)"}, 
				excelDataList);
		
		return downloadFileName;
	}
	
	
	
}
