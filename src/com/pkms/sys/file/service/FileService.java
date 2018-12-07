package com.pkms.sys.file.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.common.mail.service.MailServiceIf;
import com.pkms.sys.file.dao.FileDao;
import com.pkms.sys.file.model.FileModel;
import com.pkms.common.mail.model.MailModel;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ExcelUtil;

@Service("FileService")
public class FileService implements FileServiceIf{
	
	@Resource(name = "FileDao")
	private FileDao fileDao;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;	

	@Resource(name = "MailService")
	private MailServiceIf mailService;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Override
	public List<?> readList(FileModel fileModel) throws Exception {
		return fileDao.readList(fileModel);
	}
	
	@Override
	public List<FileModel> nameList(FileModel fileModel) throws Exception {
		return fileDao.nameList(fileModel);
	}
	
	@Override
	public List<FileModel> gubunList(FileModel fileModel) throws Exception {
		return fileDao.gubunList(fileModel);
	}
	
	@Override
	public void mailSend(FileModel fileModel) throws Exception {
		MailModel mailModel = new MailModel();
		mailModel.setMsgSubj("[PKMS]"+fileModel.getTitle());		
		mailModel.setFrom(fileModel.getSession_user_email());
		
		String content = fileModel.getContent().replace("\n", "<br>").replace(" ", "&nbsp;");
		mailModel.setMsgText(content);
		
		//운용 담당자 유무
		String op_yn = "N";
		for(String charge_gubun : fileModel.getCharge_gubuns()){
			if("OP".equals(charge_gubun)){
				op_yn = "Y";
			}
		}
		
		List<FileModel> mailInfo = new ArrayList<FileModel>();
		
		if("N".equals(op_yn)){
			//운용 미포함
			mailInfo = fileDao.mailInfo(fileModel);
		}else{
			//운용 포함
			mailInfo = fileDao.mailInfo_Op(fileModel);
		}
		mailModel.setTos(this.getEmail(mailInfo));
		mailModel.setTosInfo(this.getInfos(mailInfo));

		mailService.create4Multi(mailModel);
	}
	
	private String[] getEmail(List<FileModel> mailInfo) throws Exception {
		String[] rets = new String[mailInfo.size()];
		int i=0;
		for(FileModel fm : mailInfo){
			rets[i] = fm.getUser_email();
			i++;
		}
		return rets;
	}
	
	private String[] getInfos(List<FileModel> mailInfo) throws Exception {
		String[] rets = new String[mailInfo.size() + 2];
		int i=0;
		rets[0] = "<table style='border: 1px solid black; border-collapse: collapse;' border=1><tr><th align = 'center'>이름</th><th align = 'center'>소속</th><th align = 'center'>이메일</th></tr>";
		i++;
		
		for(FileModel fm : mailInfo){
			rets[i] = "<tr><td>"+fm.getUser_name()+"</td><td>"+fm.getUser_sosok()+"</td><td>"+fm.getUser_email()+"</td></tr>";
			i++;
		}
		
		rets[i] = "</table>";
		return rets;
	}
	
	@Override
	public void confirmUpdate(FileModel fileModel) throws Exception {
		fileDao.confirmUpdate(fileModel);
	}
	
	@Override
	public String fileExcelDownload(FileModel fileModel) throws Exception {
		fileModel.setPaging(false);
		List<?> readList = this.readList(fileModel);
		
		String[] headers = new String[]{"GROUP1_NAME","GROUP2_NAME","GROUP3_NAME","SYSTEM_NAME","TREE_NAME","FILE_ORG_NAME","REG_DATE","REG_USER","SYSTEM_USER_NAME"};

		//Excel 데이터 추출
		@SuppressWarnings("unchecked")
		List<List<String>> excelDataList = ExcelUtil.extractExcelData((List<Object>) readList, headers);
		
		String downloadFileName = ExcelUtil.write("DATA_TOTAL_SearchList", propertyService.getString("Globals.fileStorePath"), new String[]{"대분류","중분류","소분류","시스템명","자료구분","파일명","등록일","등록자","대표자"}, excelDataList);
		
		return downloadFileName;
	}
}
