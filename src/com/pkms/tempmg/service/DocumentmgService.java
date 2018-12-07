package com.pkms.tempmg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.sys.system.model.SystemFileModel;
import com.pkms.tempmg.dao.DocumentmgDao;
import com.pkms.tempmg.model.DocumentmgModel;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ExcelUtil;

@Service("DocumentmgService")
public class DocumentmgService implements DocumentmgServiceIf{
		
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "DocumentmgDao")
	protected DocumentmgDao documentmgDao;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf attachFileService;
	
	
	@Override
	public List<?> readList(DocumentmgModel documentmgmodel) throws Exception {
		
		List<?> resultList = documentmgDao.readList(documentmgmodel);
		
		for (int i = 0; i < resultList.size(); i++) {
			
			System.out.println(resultList.get(i));
		}
		
		int totalCount = documentmgDao.readTotalCount(documentmgmodel);
		if(resultList == null && resultList.isEmpty()) {
			totalCount = 0;
		}
		documentmgmodel.setTotalCount(totalCount);
		
		return resultList;
	}
	
	
	@Override
	public List<?> codeList(DocumentmgModel documentmgmodel) throws Exception {
		List<?> resultList = documentmgDao.codeList(documentmgmodel);
		return resultList;
	}
	
	
	@Override
	public DocumentmgModel readone(DocumentmgModel documentmgmodel) throws Exception {
		return documentmgDao.readone(documentmgmodel);
	}
	
	
	
	public DocumentmgModel insert(DocumentmgModel documentmgmodel) throws Exception{
		DocumentmgModel verifySeq = documentmgDao.insert(documentmgmodel);
		return documentmgmodel;
	}
	
	
	public void update(DocumentmgModel documentmgmodel) throws Exception{
		documentmgDao.update(documentmgmodel);
	}
	
	public void delete(DocumentmgModel documentmgmodel) throws Exception{
		documentmgDao.delete(documentmgmodel);
	}
	
	public void deleteList(DocumentmgModel documentmgmodel) throws Exception{
		documentmgDao.deleteList(documentmgmodel);
	}
	
	
	@Override
	public String excelDownload(DocumentmgModel documentmgmodel) throws Exception {
		//데이터
		documentmgmodel.setPaging(false);
		List<?> readList = documentmgDao.readList(documentmgmodel);
		
		//Excel 헤더
		String[] headers = new String[]{"CHK_SEQ", "CHK_TYPE", "CHK_TYPE_NAME", "STATUS", "TITLE", "CHK_CONTENT", "REG_USER","REG_USER_NAME", "REG_DATE", "UPDATE_USER", "UPDATE_USER_NAME", "UPDATE_DATE", "ANSWER_TYPE", "POSITION"};
		
		//Excel 데이터 추출
		@SuppressWarnings("unchecked")
		List<List<String>> excelDataList = ExcelUtil.extractExcelData((List<Object>) readList, headers);

		//파일 생성 후 다운로드할 파일명
		String downloadFileName = ExcelUtil.write(documentmgmodel.EXCEL_FILE_NAME, propertyService.getString("Globals.fileStorePath"),
				new String[]{"번호","사용분류코드","사용분류","상태(사용/미사용)","제목(내용)","설명","등록ID","등록자", "등록일", "수정ID", "수정자", "수정일", "처리결과", "가변구분(S, F, E)"}, 
				excelDataList);
		
		return downloadFileName;
	}
	
	
	
	
	@Override
	public int docfileIdx(DocumentmgModel documentmgmodel) throws Exception {
		return documentmgDao.docfileIdx(documentmgmodel);
	}
	
	@Override
	public String doc_file_add(SystemFileModel systemfilemodel,  String prefix) throws Exception {
//	public String doc_file_add(DocumentmgModel documentmgmodel,  String prefix) throws Exception {
		return attachFileService.doc_file_add(systemfilemodel, prefix);
	}
	
	@Override
	public String doc_file_del(DocumentmgModel documentmgmodel,String prefix) throws Exception {
		return attachFileService.doc_file_del(documentmgmodel, prefix);
	}	
	
	
	
	
}
