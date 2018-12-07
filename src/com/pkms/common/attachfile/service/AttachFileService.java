package com.pkms.common.attachfile.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pkms.common.attachfile.dao.AttachFileDAO;
import com.pkms.common.attachfile.model.AttachFileMasterKey;
import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.service.PkgEquipmentServiceIf;
import com.pkms.pkgmg.pkg21.dao.Pkg21DAO;
import com.pkms.pkgmg.pkg21.model.Pkg21FileModel;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.sys.system.model.SystemFileModel;
import com.pkms.tempmg.dao.DocumentmgDao;
import com.pkms.tempmg.model.DocumentmgModel;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ObjectUtil;
import com.wings.util.WingsStringUtil;

/**
 * 파일정보의 관리를 위한 구현 클래스
 * 
 */
@Service("AttachFileService")
public class AttachFileService implements AttachFileServiceIf {

	static Logger logger = Logger.getLogger(AttachFileService.class);

	@Resource(name = "AttachFileDAO")
	private AttachFileDAO attachFileDAO;
	
	@Resource(name = "DocumentmgDao")
	private DocumentmgDao documentmgDao;

	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name="Pkg21DAO")
	private Pkg21DAO pkg21DAO;
	
	@Resource(name = "PkgEquipmentService")
	private PkgEquipmentServiceIf pkgEquipmentService;
	
	private String propertyFilePathKey = "Globals.fileStorePath";
	
	private int bufferSize = 2048;
	
	public static MessageDigest md;
	
	@Override
	public void create(Object object, String prefix) throws Exception {

		List<AttachFileModel> fileList = new ArrayList<AttachFileModel>();

		String master_file_id = attachFileDAO.readNextStringId();

		handlePrivateField(object, master_file_id, fileList, prefix);

		if (fileList.size() > 0) {
			// 파일 로컬 생성
			createLocalFile(fileList);

			// 파일 정보 DB 생성
			// fileManageDAO.createAll(fileList);
			for (AttachFileModel attachFileModel : fileList) {
				attachFileDAO.create(attachFileModel);
			}
		}

	}

	@Override
	public void read(Object object) throws Exception {

		String master_file_id = getFileMasterId(object);

		// 첨부 파일 정보 조회
		AttachFileModel attachFileModel = new AttachFileModel();
		attachFileModel.setMaster_file_id(master_file_id);
		List<?> fileList = attachFileDAO.readList(attachFileModel);

		// 사용자 모델에 첨부파일 모델 세팅
		for (Object fileModel : fileList) {
			setAttachFileModel(object, (AttachFileModel) fileModel);
		}

	}
	
//	@Override
//	public void readList(final List<?> objects) throws Exception {
//		for (Object object : objects) {
//			read(object);
//		}
//	}

	@Override
	public void update(Object object, String prefix) throws Exception {

		String master_file_id = getFileMasterId(object);

		List<AttachFileModel> deleteFileList = new ArrayList<AttachFileModel>();
		List<AttachFileModel> createFileList = new ArrayList<AttachFileModel>();

		for (Field field : object.getClass().getDeclaredFields()) {

			if (field.getType().isInstance(new AttachFileModel())) {

				field.setAccessible(true);

				Object fileModel = ObjectUtil.getObjectFieldValue(object, field.getName());

				if (fileModel != null) {

					AttachFileModel attachFileModel = (AttachFileModel) fileModel;
					
					attachFileModel.setMaster_file_id(master_file_id);
					attachFileModel.setAttach_file_id(field.getName());

					if (attachFileModel.isDelete() || attachFileModel.getFile_size() > 0) {
						deleteFileList.add(attachFileModel);
					}

					if (attachFileModel.getFile_size() > 0) {
						attachFileModel.setFile_name(prefix + field.getName() + "_" + WingsStringUtil.getTimeStamp());
						attachFileModel.setFile_path(WingsStringUtil.getTimeStamp().substring(0, 8));
						createFileList.add(attachFileModel);
					}
				}
			}
		}
		/**
		 * 현재 구조에서 위의 파일 삭제 목록 작성 불가능하여 따로 삭제 목록을 받음
		 */

		String[] deleteList =  getDeleteFileList(object);
		
		if(deleteList != null) {
			for(String id : deleteList) {
				AttachFileModel deleteAttachFileModel = new AttachFileModel();
				deleteAttachFileModel.setMaster_file_id(master_file_id);
				deleteAttachFileModel.setAttach_file_id(id);
				deleteAttachFileModel = attachFileDAO.read(deleteAttachFileModel);
				deleteFileList.add(deleteAttachFileModel);
			}
		}
		
		/**
		 * 먼저 로컬에 있는 파일을 삭제를 하고 
		 * 테이블에 있는 파일 정보를 삭제 합니다 
		 * 
		 * 신규 파일정보를 테이블에 생성을 하고 
		 * 로컬 파일을 생성 합니다 
		 */
		// 로컬에서 삭제
		try {
			deleteLocalFile(deleteFileList);
		} catch (Exception ex) {
			/*
			 * 로컬 파일은 삭제에 실패 하여도 로직에 영향을 주지 않는다. 가비지로 남는 파일은 무시.
			 */
		}
		
		for (AttachFileModel attachFileModel : deleteFileList) {
			// DB에서 삭제
			attachFileDAO.delete(attachFileModel);
		}

		for (AttachFileModel attachFileModel : createFileList) {
			// DB에 생성
			attachFileDAO.create(attachFileModel);
		}
	
		// 파일 로컬 생성
		createLocalFile(createFileList);

	}

	@Override
	public void delete(Object object) throws Exception {

		String master_file_id = getFileMasterId(object);
		AttachFileModel attachFileModel = new AttachFileModel();
		attachFileModel.setMaster_file_id(master_file_id);

		List<?> fileList = attachFileDAO.readList(attachFileModel);


		// 로컬에서 삭제
		try {
			deleteLocalFile(fileList);
			// DB에서 삭제
			attachFileDAO.deleteAll(attachFileModel);
		} catch (Exception ex) {
			/*
			 * 로컬 파일은 삭제에 실패 하여도 로직에 영향을 주지 않는다. 가비지로 남는 파일은 무시.
			 */
		}

	}

	private String getFileMasterId(Object object) throws Exception {
		for (Field field : object.getClass().getDeclaredFields()) {
			if (field.getAnnotation(AttachFileMasterKey.class) != null) {
				field.setAccessible(true);
				return (String) ObjectUtil.getObjectFieldValue(object, field.getName());
			}
		}
		return null;
	}

	public String[] getDeleteFileList(Object object) throws Exception {

		String[] deleteList = null;

		for (Field field : object.getClass().getDeclaredFields()) {
			if(field.getName().equals("deleteList") && StringUtils.isNotEmpty((String)ObjectUtil.getObjectFieldValue(object, field.getName())) ) {
				deleteList = ((String)ObjectUtil.getObjectFieldValue(object, field.getName())).split(",");
			}
		}
		return deleteList;
	}
	
	private void handlePrivateField(Object object, String master_file_id, List<AttachFileModel> fileList, String prefix) throws Exception {

		for (Field field : object.getClass().getDeclaredFields()) {

			if (field.getType().isInstance(new AttachFileModel())) {

				field.setAccessible(true);

				Object fileModel = ObjectUtil.getObjectFieldValue(object, field.getName());

				if (fileModel != null) {

					AttachFileModel attachFileModel = (AttachFileModel) fileModel;

					if (attachFileModel.getFile_size() > 0) {

						attachFileModel.setMaster_file_id(master_file_id);
						attachFileModel.setAttach_file_id(field.getName());
						attachFileModel.setFile_name(prefix + field.getName() + "_" + WingsStringUtil.getTimeStamp());
						attachFileModel.setFile_path(WingsStringUtil.getTimeStamp().substring(0, 8));
						fileList.add(attachFileModel);
					}
				}
			}
			if (field.getAnnotation(AttachFileMasterKey.class) != null) {

				field.setAccessible(true);
				field.set(object, master_file_id);
			}
		}
	}

	private void setAttachFileModel(Object object, AttachFileModel model) throws Exception {

		for (Field field : object.getClass().getDeclaredFields()) {
			if (field.getType().isInstance(model) && field.getName().equals(model.getAttach_file_id())) {
				field.setAccessible(true);
				field.set(object, model);
			}
		}
	}

	private void createLocalFile(List<AttachFileModel> fileList) throws Exception {

		InputStream inputStream = null;
		OutputStream outputStream = null;
		int bytesRead = 0;
		byte[] buffer = null;
		
		String localFilePath = getFilePath(fileList);
		logger.debug("=========================================MASTE_FILE_ID_STRING================================" + localFilePath);
		try {
			File cFile = new File(localFilePath);
			if (!cFile.isDirectory()) {
				boolean _flag = cFile.mkdir();
				if (!_flag) {
					throw new IOException("Directory creation Failed ");
				}
			}
		} catch (FileNotFoundException e) {
			throw new Exception("error.sys.파일 생성 중 에러가 발생했습니다.\n에러가 지속되는 경우 관리자에게 문의하시기 바랍니다.");
		} catch (IOException e) {
			throw new Exception("error.sys.파일 생성 중 에러가 발생했습니다.\n에러가 지속되는 경우 관리자에게 문의하시기 바랍니다.");
		} catch (Exception e) {
			throw new Exception("error.sys.파일 생성 중 에러가 발생했습니다.\n에러가 지속되는 경우 관리자에게 문의하시기 바랍니다.");
		} finally {
		}

		for (AttachFileModel attachFileModel : fileList) {
			 //첨부파일 유효성 검사
			String fileExtension = attachFileModel.getFile_org_name().substring(attachFileModel.getFile_org_name().lastIndexOf('.') + 1);
			if(!checkFile(fileExtension)){
				if(fileList.size() > 1){
					throw new Exception("error.biz.등록 할 수 없는 파일이 포함 되어 있습니다.");
			    }else{
			    	throw new Exception("error.biz.등록 할 수 없는 파일 입니다.");
			    }
			}else{
			try {
				
				if(!"tree".equals(attachFileModel.getFile_type())){
					inputStream = attachFileModel.getInputStream();
				}else{
					inputStream = attachFileModel.getFile().getInputStream();
				}
				
				outputStream = new FileOutputStream(localFilePath + attachFileModel.getFile_name());

				bytesRead = 0;
				buffer = new byte[bufferSize];

				while ((bytesRead = inputStream.read(buffer, 0, bufferSize)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
			} catch (FileNotFoundException e) {
				throw new Exception("error.sys.파일 생성 중 에러가 발생했습니다.\n에러가 지속되는 경우 관리자에게 문의하시기 바랍니다.");
			} catch (IOException e) {
				throw new Exception("error.sys.파일 생성 중 에러가 발생했습니다.\n에러가 지속되는 경우 관리자에게 문의하시기 바랍니다.");
			} catch (Exception e) {
				throw new Exception("error.sys.파일 생성 중 에러가 발생했습니다.\n에러가 지속되는 경우 관리자에게 문의하시기 바랍니다.");
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (Exception ex) {
					}
				}
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (Exception ex) {
					}
				}
				inputStream = null;
				outputStream = null;
				bytesRead = 0;
				buffer = null;
			}
			}
		}
	}

	private boolean checkFile(String fileExtension) throws Exception{
		String checkData[] = {"xlsx","xlsm","xlsb","xls","mht","mhtml","xltx","xltm","xlt",
							  "csv","prn","dif","slk","xlam","xla","xps","docx","docm","doc",
							  "dotx","dotm","dot","rtf","wps","ppt","pptx","pptm","png",
							  "potx","potm","pot","thmx","ppsx","ppsm","pps","ppam","ppa","tif",
							  "wmf","emf","jpg","gif","bmp","pdf","txt","hwp","zip",
							  "xlss","xlsxx","docc","docxx","pptt","pptxx"};

		for(int i=0; i<checkData.length; i++){
			boolean checkIgnore = checkData[i].equalsIgnoreCase(fileExtension);	//대소문자 구분없이 등록가능
			if(checkIgnore){
					return true;
				}	
		}
		return false;
	}
	 
	private String getFilePath(List<?> fileList) {
		
		String path =  propertyService.getString(propertyFilePathKey);
		
		for (Object attachFileModel : fileList) {
			path = propertyService.getString(propertyFilePathKey) + ((AttachFileModel) attachFileModel).getFile_path() + File.separator;
		}
		
		return path;
	}

	private void deleteLocalFile(List<?> fileList) throws Exception {
		
		String localFilePath = getFilePath(fileList);
		/**
		 * 파일 정보를 읽어 오기 위한 변수 선언 
		 */
		AttachFileModel attachFileModel_info = null;// new AttachFileModel();
		List<?>  model_Info_List = null;//
		for (Object attachFileModel : fileList) {
			
			/*
			 * 파일 정보를 읽어 옵니다 
			 */
			attachFileModel_info = new  AttachFileModel();
			model_Info_List = attachFileDAO.deleteList((AttachFileModel)attachFileModel);
			attachFileModel_info = (AttachFileModel)model_Info_List.get(0);
			File file = new File(localFilePath+attachFileModel_info.getFile_name());
			/*
			 * 파일이 있을 경우 삭제 
			 */
			if(file.isFile()){
				if (!file.delete()) {
					throw new Exception("파일 삭제 실패");
				}
			}
			
		}
	}

	@Override
	public void copy(Object object) throws Exception {
		String master_file_id = getFileMasterId(object);
		
		if (master_file_id != null && !"".equals(master_file_id)) {
			// 첨부 파일 정보 조회
			AttachFileModel attachFileModel = new AttachFileModel();
			attachFileModel.setMaster_file_id(master_file_id);
			List<AttachFileModel> fileList = attachFileDAO.readList(attachFileModel);
			
			if (fileList.size() > 0) {
				String new_master_file_id = attachFileDAO.readNextStringId();
				
				handlePrivateField4Copy(object, new_master_file_id, fileList);
				
				// 파일 로컬 생성
				createLocalFile4Copy(fileList);
				
				
				// 파일 정보 DB 생성
				for (AttachFileModel cAttachFileModel : fileList) {
					attachFileDAO.create(cAttachFileModel);
				}
			}
		}
	}

	private void handlePrivateField4Copy(Object object, String master_file_id, List<AttachFileModel> fileList) throws Exception {
		for (AttachFileModel attachFileModel : fileList) {
			attachFileModel.setMaster_file_id(master_file_id);
			attachFileModel.setFile_name("COPY_" + attachFileModel.getFile_name());
//			attachFileModel.setFile_path(WingsStringUtil.getTimeStamp().substring(0, 8));
		}

		for (Field field : object.getClass().getDeclaredFields()) {
			if (field.getAnnotation(AttachFileMasterKey.class) != null) {

				field.setAccessible(true);
				field.set(object, master_file_id);
				break;
			}
		}
	}


	private void createLocalFile4Copy(List<AttachFileModel> fileList) throws Exception {

		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		FileChannel fcIn = null;
		FileChannel fcOut = null;
		long size = 0;
		String orgFileName = null;

//		String localFilePath = propertyService.getString(propertyFilePathKey);
		String localFilePath = getFilePath(fileList);
		
		try {
			File dir = new File(localFilePath);

			if (!dir.isDirectory()) {
				boolean _flag = dir.mkdir();
				if (!_flag) {
					throw new IOException("Directory creation Failed ");
				}
			}
		} catch (FileNotFoundException e) {
			throw new Exception("error.sys.파일 생성 중 에러가 발생했습니다.\n에러가 지속되는 경우 관리자에게 문의하시기 바랍니다.");
		} catch (IOException e) {
			throw new Exception("error.sys.파일 생성 중 에러가 발생했습니다.\n에러가 지속되는 경우 관리자에게 문의하시기 바랍니다.");
		} catch (Exception e) {
			throw new Exception("error.sys.파일 생성 중 에러가 발생했습니다.\n에러가 지속되는 경우 관리자에게 문의하시기 바랍니다.");
		} finally {
		}

		for (AttachFileModel attachFileModel : fileList) {

			try {
				//IN
				orgFileName = attachFileModel.getFile_name();
				if(orgFileName.startsWith("COPY_")) {
					orgFileName = orgFileName.substring(5, orgFileName.length());
				}
				inputStream = new FileInputStream(localFilePath + orgFileName);
				fcIn = inputStream.getChannel();
				
				outputStream = new FileOutputStream(localFilePath + attachFileModel.getFile_name());
				fcOut = outputStream.getChannel();
				
				//Transfer
				size = fcIn.size();
				fcIn.transferTo(0, size, fcOut);
				
			} catch (FileNotFoundException e) {
				continue;
			} catch (IOException e) {
				throw new Exception("error.sys.파일 생성 중 에러가 발생했습니다.\n에러가 지속되는 경우 관리자에게 문의하시기 바랍니다.");
			} catch (Exception e) {
				throw new Exception("error.sys.파일 생성 중 에러가 발생했습니다.\n에러가 지속되는 경우 관리자에게 문의하시기 바랍니다.");
			} finally {
				orgFileName = null;
				size = 0;
				
				if (fcOut != null) {
					try {
						fcOut.close();
					} catch (Exception ex) {
					}
				}
				if (fcIn != null) {
					try {
						fcIn.close();
					} catch (Exception ex) {
					}
				}
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (Exception ex) {
					}
				}
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (Exception ex) {
					}
				}
			}
		}
	}
	
	@Override
	public boolean readId(AttachFileModel attachFileModel) throws Exception {
		List<AttachFileModel> idList = attachFileDAO.idreadList(attachFileModel);
		String readId = "";
		for(AttachFileModel fileModel : idList){
			readId = this.sha512(fileModel.getEmpno());
			if(readId.equals(attachFileModel.getEmpno())){
				return true;
			}
		}
		return false;
	}
	
	private String sha512(String userPassword) throws Exception {
		String tempPassword = "";
		
		try {
            //SHA-256알고리즘 사용
            if(md==null) md = MessageDigest.getInstance("SHA-512");

            //문자열로 받아들임
            md.update(userPassword.getBytes());
            byte[] mb = md.digest();

            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));

                while (s.length() < 2) {
                    s = "0" + s;

                }
                s = s.substring(s.length() - 2);
                tempPassword += s;

            }
            return tempPassword;

        } catch (Exception e) {
        	throw new Exception("error.biz.암호화 변환 에러입니다.");
        }
	}

	
	@Override
	public void tree_file_add(SystemFileModel systemFileModel, String prefix) throws Exception {

		MultipartFile file;
		
		List<AttachFileModel> createFileList = new ArrayList<AttachFileModel>();
		
		int idx = attachFileDAO.fileIdx(systemFileModel);

		String attach_file_id = "";
		
		for(int i = 0; i < systemFileModel.getFiles().size() ; i++ ){
			
			file = systemFileModel.getFiles().get(i);
			attach_file_id = "attachFile"+idx;
			if(file.getSize() > 0){
				AttachFileModel attachFileModel = new AttachFileModel();
				attachFileModel.setMaster_file_id(systemFileModel.getMaster_file_id());
				attachFileModel.setAttach_file_id(attach_file_id);
				attachFileModel.setFile_name(prefix + attach_file_id+ "_" + WingsStringUtil.getTimeStamp());
				attachFileModel.setFile_path(WingsStringUtil.getTimeStamp().substring(0, 8));
				attachFileModel.setFile_org_name(file.getOriginalFilename());
				attachFileModel.setFile_size(file.getSize());
				attachFileModel.setFile_type("tree");
				attachFileModel.setFile((CommonsMultipartFile) file);
				attachFileModel.setParent_tree_id(systemFileModel.getParent_tree_id());
				int index = file.getOriginalFilename().lastIndexOf(".");
				attachFileModel.setUser_id(systemFileModel.getSession_user_id());
				attachFileModel.setUser_nm(systemFileModel.getSession_user_name());
				
				attachFileModel.setFile_extension(file.getOriginalFilename().substring(index + 1));
				
				createFileList.add(attachFileModel);
				idx++;
			}
			
		}
		
		createLocalFile(createFileList);
		
		for (AttachFileModel attachFileModel : createFileList) {
			attachFileDAO.create(attachFileModel);
		}
		
	}
	
	@Override
	public void tree_file_delete(SystemFileModel systemFileModel) throws Exception {
		
		List<AttachFileModel> deleteFileList = new ArrayList<AttachFileModel>();
		
		AttachFileModel attachFileModel = new AttachFileModel();
		attachFileModel.setMaster_file_id(systemFileModel.getMaster_file_id());
		attachFileModel.setAttach_file_id(systemFileModel.getAttach_file_id());
		attachFileModel.setFile_path(systemFileModel.getFile_path());
		deleteFileList.add(attachFileModel);
		
		deleteLocalFile(deleteFileList);
		
		for (AttachFileModel atfm : deleteFileList) {
			attachFileDAO.delete(atfm);
		}
		
	}

	@Override
	public int tree_file_move(SystemFileModel systemFileModel) {
		return attachFileDAO.tree_file_move(systemFileModel);
	}
	
	@Override
	public int tree_file_update(SystemFileModel systemFileModel) {
		return attachFileDAO.tree_file_update(systemFileModel);
	}


	
	@Override
	public String new_file_add(SystemFileModel systemFileModel, String prefix) throws Exception {
/*
	PKG 2.1 Version
	에서 부터 현황 화면에서 drag and drop 로
	파일 업로드를 하기때문에 로직 추가
*/	
		
		MultipartFile file;
		List<AttachFileModel> createFileList = new ArrayList<AttachFileModel>();
		String master_file_id = "";
		int idx = 1;
		
		if(systemFileModel.getMaster_file_id() != null && (systemFileModel.getMaster_file_id()).length() != 0) {
			master_file_id = systemFileModel.getMaster_file_id();
			systemFileModel.setAttach_file_id(prefix);
			systemFileModel.setAttach_file_id_size(prefix.length()+1);//substr 할경우 0부터 이므로 +1
			idx = attachFileDAO.newfileIdx(systemFileModel);
		}else {
			master_file_id = attachFileDAO.readNextStringId();
		}

		
		String attach_file_id = "";
		for(int i = 0; i < systemFileModel.getFiles().size() ; i++ ){
			
			file = systemFileModel.getFiles().get(i);
			attach_file_id = prefix + idx;
			
			logger.debug("====파일 검증 및 setting START====");
			if("svtResult".equals(prefix) || "dvtResult".equals(prefix) || "cvtResult".equals(prefix)){	
				MultipartFile vt_file;
				vt_file = file;			
				File vtFile = convert(vt_file);
				readExcelFile(vtFile, master_file_id);
			}
			
			if(prefix.length() > 16){
				String work_gubun = prefix.substring(0,2);
				String prefix_name = prefix.substring(2,17);
				logger.debug(prefix_name);
				if("EquipmentAttach".equals(prefix_name)){
					MultipartFile vt_file;
					vt_file = file;			
					File vtFile = convert(vt_file);
					equipmentExcelCreate(vtFile, master_file_id,work_gubun);
				}
			}
			
			logger.debug("====파일 검증 및 setting end====");
			String originalFilename = file.getOriginalFilename();
			
			/**
			 * 20181119 eryoon
			 * originalFilename 변경
			 * AS-IS: originalFilename = file.getOriginalFilename();
			 * TO-BE: originalFilename = 요구사항명세서+패키지명+YYYYMMDD+실명.확장자;
			 */
			String pkgTitle = systemFileModel.getPkgTitle();
			String sysUserNm = systemFileModel.getSession_user_name();
			String timeStemp = WingsStringUtil.getTimeStamp().substring(0, 8);
			String fileExt = "";
			String filePrefix = "";
			
			if(file.getSize() > 0){
				AttachFileModel attachFileModel = new AttachFileModel();
				attachFileModel.setMaster_file_id(master_file_id);
				attachFileModel.setAttach_file_id(attach_file_id);
				
				/**
				 * 20181119 eryoon
				 */
				int index = file.getOriginalFilename().lastIndexOf(".");
				fileExt = file.getOriginalFilename().substring(index + 1);
/*				
				logger.debug("prefix: " + prefix);
				logger.debug("master_file_id: " + master_file_id);
				logger.debug("attach_file_id: " + attach_file_id);
				logger.debug("originalFilename: " + file.getOriginalFilename());
				logger.debug("sysUserNm: " + sysUserNm);
				logger.debug("pkgTitle: " + pkgTitle);
*/				
				/**
				 * 20181119 eryoon
				 * cvtReqstate
				 * cvtSupstate
				 * cvtResult
				 * cvtItchklist
				 * cvtProcedure
				 * cvtChklist
				 * cvtBillComAttach
				 * cvtVolComAttach
				 * cvtNotiComAttach
				 */
				if ("svtReqstate".equals(prefix) 
						|| "svtSpec".equals(prefix) 
						|| "svtReview".equals(prefix) 
						|| "svtSupstate".equals(prefix) 
						|| "svtResult".equals(prefix) 
						|| "svtStaticresult".equals(prefix)
						|| "svtItchklist".equals(prefix)
						|| "svtBinary".equals(prefix) 
						|| "svtScript".equals(prefix) 
						|| "svtEtcadd".equals(prefix)
						
						|| "secuValidattach".equals(prefix)
						
						|| "cvtReqstate".equals(prefix)
						|| "cvtSupstate".equals(prefix)
						|| "cvtResult".equals(prefix)
						|| "cvtItchklist".equals(prefix)
						|| "cvtProcedure".equals(prefix)
						|| "cvtChklist".equals(prefix)
						|| "cvtBillComAttach".equals(prefix)
						|| "cvtVolComAttach".equals(prefix)
						|| "cvtNotiComAttach".equals(prefix)
						|| "cvtPComAttach".equals(prefix) 
						|| "firstDayattach".equals(prefix)
						
						|| "expAttach".equals(prefix)
						
						|| "firstFinalattach".equals(prefix)
						
						|| "dvtReqstate".equals(prefix)
						|| "dvtSupstate".equals(prefix)
						|| "dvtResult".equals(prefix)
						|| "dvtEtc".equals(prefix)
						) {
					
					if ("svtReqstate".equals(prefix) || "cvtReqstate".equals(prefix) || "dvtReqstate".equals(prefix)) {
						filePrefix = "요구사항명세서";
					}else if ("svtSpec".equals(prefix)) {
						filePrefix = "규격서";
					}else if ("svtReview".equals(prefix)) {
						filePrefix = "설계Review결과서";
					}else if("svtSupstate".equals(prefix) || "cvtSupstate".equals(prefix) || "dvtSupstate".equals(prefix)) {
						filePrefix = "보완내역서";
					}else if("svtResult".equals(prefix)) {
						filePrefix = "SVT검증결과서";
					}else if("svtStaticresult".equals(prefix)) {
						filePrefix = "정적분석수행결과서";
					}else if("svtItchklist".equals(prefix) || "cvtItchklist".equals(prefix)) {
						filePrefix = "IT요소기술CheckList";
					}else if("svtBinary".equals(prefix)) {
						filePrefix = "PKGBinary";
					}else if("svtScript".equals(prefix)) {
						filePrefix = "PKG적용Script";
					}else if("svtEtcadd".equals(prefix) || "dvtEtc".equals(prefix)) {
						filePrefix = "기타첨부";
					}else if("secuValidattach".equals(prefix)) {
						filePrefix = "보안검증결과";
					/*}else if ("cvtReqstate".equals(prefix)) {
						filePrefix = "요구사항명세서";*/
					/*}else if ("cvtSupstate".equals(prefix)) {
						filePrefix = "보완내역서";*/
					}else if ("cvtResult".equals(prefix)) {
						filePrefix = "CVT검증결과서";
					/*}else if ("cvtItchklist".equals(prefix)) {
						filePrefix = "IT요소기술CheckList";*/
					}else if ("cvtProcedure".equals(prefix)) {
						filePrefix = "적용및원복절차서";
					}else if ("cvtChklist".equals(prefix)) {
						filePrefix = "점검절차서";
					}else if ("cvtBillComAttach".equals(prefix)) {
						filePrefix = "과금검증";
					}else if ("cvtVolComAttach".equals(prefix)) {
						filePrefix = "용량검증";
					}else if ("cvtNotiComAttach".equals(prefix)) {
						filePrefix = "상용적용시유의사항";
					}else if ("cvtPComAttach".equals(prefix)) {
						filePrefix = "CVT검증계획";
					}else if ("firstDayattach".equals(prefix)) {
						filePrefix = "초도적용당일결과";
					}else if ("expAttach".equals(prefix)) {
						filePrefix = "확대적용결과";
					}else if ("firstFinalattach".equals(prefix)) {
						filePrefix = "초도적용최종결과";
					/*}else if ("dvtReqstate".equals(prefix)) {
						filePrefix = "요구사항명세서";*/
					/*}else if ("dvtSupstate".equals(prefix)) {
						filePrefix = "보완내역서";*/
					}else if ("dvtResult".equals(prefix)) {
						filePrefix = "DVT검증결과서";
					}/*else if ("dvtEtc".equals(prefix)) {
						filePrefix = "기타첨부";
					}*/
					
					
					
					originalFilename = filePrefix+"_"+pkgTitle+"_"+timeStemp+"_"+sysUserNm+idx+"."+fileExt;
				}else{
					originalFilename = file.getOriginalFilename();
				}
				
				attachFileModel.setFile_name("PKG21_" +  attach_file_id+ "_" + WingsStringUtil.getTimeStamp());
				attachFileModel.setFile_path(WingsStringUtil.getTimeStamp().substring(0, 8));
				//20181119 eryoon
				attachFileModel.setFile_org_name(originalFilename/*file.getOriginalFilename()*/);
				attachFileModel.setFile_size(file.getSize());
				attachFileModel.setFile_type("tree");
				attachFileModel.setFile((CommonsMultipartFile) file);
				attachFileModel.setParent_tree_id(systemFileModel.getParent_tree_id());
				
				attachFileModel.setUser_id(systemFileModel.getSession_user_id());
				attachFileModel.setUser_nm(systemFileModel.getSession_user_name());
				
				attachFileModel.setFile_extension(file.getOriginalFilename().substring(index + 1));	//파일 확장자
				
				createFileList.add(attachFileModel);
				idx++;
			}
			
		}

			createLocalFile(createFileList); // 파일생성
			
		for (AttachFileModel attachFileModel : createFileList) {
			attachFileDAO.create(attachFileModel); //DB 입력
		}
		
		return master_file_id;
	}
	
	
	
	@Override
	public String new_file_del(SystemFileModel systemFileModel)  throws Exception {
		
		String result = "";
		
		if(systemFileModel.getAttach_file_id() != null && systemFileModel.getAttach_file_id().length() !=0) {
			
		AttachFileModel attachFileModel = new AttachFileModel();
		
		attachFileModel.setMaster_file_id(systemFileModel.getMaster_file_id());
		attachFileModel.setAttach_file_id(systemFileModel.getAttach_file_id());
		attachFileModel.setFile_path(systemFileModel.getFile_path());

		String localFilePath = propertyService.getString(propertyFilePathKey) + systemFileModel.getFile_path() + File.separator;
		
		File file = new File(localFilePath+systemFileModel.getFile_name());
		if(file.isFile()){ 
			if (!file.delete()) { // 파일 삭제
				result = "error";
				throw new Exception("파일 삭제 실패");
				
			}
		}
		
		attachFileDAO.delete(attachFileModel); // DB 에서 삭제
		
		
		}else { // 저장이 아닌 페이지전환 될때 

		logger.debug("---------------------------------------------------------------------");
		logger.debug("----------------------저장하지 않고 나올경우----------------------------");
		logger.debug("---------------------------------------------------------------------");
			AttachFileModel findDelListModel = new AttachFileModel();
			findDelListModel.setMaster_file_id(systemFileModel.getMaster_file_id());
			
			List<AttachFileModel> deleteFileList = new ArrayList<AttachFileModel>();
			List<AttachFileModel> fileList = attachFileDAO.readList(findDelListModel);
			
			for (AttachFileModel delFlist : fileList) {	
				AttachFileModel attachFileModel = new AttachFileModel();
				attachFileModel.setMaster_file_id(delFlist.getMaster_file_id());
				attachFileModel.setAttach_file_id(delFlist.getAttach_file_id());
				attachFileModel.setFile_path(delFlist.getFile_path());
				deleteFileList.add(attachFileModel);
			}
			
			deleteLocalFile(deleteFileList);
			
			for (AttachFileModel atfm : deleteFileList) {
				attachFileDAO.delete(atfm);
			}
		
		}//end else
		
		
		
		return result;
	}
	
	
	
	
	
	
	


	
	@Override
	public String doc_file_add(SystemFileModel systemfilemodel, String prefix) throws Exception {
		
		MultipartFile file;
		List<AttachFileModel> createFileList = new ArrayList<AttachFileModel>();
		List<DocumentmgModel> documentmgList = new ArrayList<DocumentmgModel>();
		
		String doc_seq = "";
		int idx = 1;
		String inputType = "insert";
		SystemFileModel idxserch = null;		
		if(systemfilemodel.getOther_seq() != null && (systemfilemodel.getOther_seq()).length() != 0) {
			if(systemfilemodel.getOther_seq().equals("new")) {
				doc_seq = Integer.toString(attachFileDAO.docfileIdx(idxserch));
			}else {
				doc_seq = systemfilemodel.getOther_seq();
				
				idx = attachFileDAO.docfileIdx(idxserch);
				inputType= "update";
			}
		}else {
			doc_seq = Integer.toString(attachFileDAO.docfileIdx(idxserch));
		}

		
		String attach_file_id = "";
		for(int i = 0; i < systemfilemodel.getFiles().size() ; i++ ){
			
			file = systemfilemodel.getFiles().get(i);
			attach_file_id = prefix + idx;
			
			if(file.getSize() > 0){
				AttachFileModel attachFileModel = new AttachFileModel();
				attachFileModel.setFile_name("DOCTEMP_" +  attach_file_id+ "_" + WingsStringUtil.getTimeStamp());
				attachFileModel.setFile_path(WingsStringUtil.getTimeStamp().substring(0, 8));
				attachFileModel.setFile_org_name(file.getOriginalFilename());
				attachFileModel.setFile_size(file.getSize());
				attachFileModel.setFile_type("tree");
				attachFileModel.setFile((CommonsMultipartFile) file);
				int index = file.getOriginalFilename().lastIndexOf(".");
				attachFileModel.setFile_extension(file.getOriginalFilename().substring(index + 1));

				createFileList.add(attachFileModel); 
				
				DocumentmgModel docModelt = new DocumentmgModel();

				docModelt.setDoc_seq(doc_seq);
				docModelt.setDoc_name("document_uploadFile_name");
//				docModelt.setDoc_version(documentmgModel.getDoc_version());
				docModelt.setFile_name("DOCTEMP_" +  attach_file_id+ "_" + WingsStringUtil.getTimeStamp());
				docModelt.setFile_path(WingsStringUtil.getTimeStamp().substring(0, 8));
				docModelt.setFile_size(file.getSize());
				docModelt.setFile_org_name(file.getOriginalFilename());
				docModelt.setReg_user_id(systemfilemodel.getSession_user_id());
				docModelt.setUpdate_user_id(systemfilemodel.getSession_user_id());
				docModelt.setFile_extension(file.getOriginalFilename().substring(index + 1));
				
				documentmgList.add(docModelt);

				idx++;
			}
		}

			createLocalFile(createFileList); // 파일생성
			
		for (DocumentmgModel docModel : documentmgList) {
			try {
				if(inputType == "insert" ) {
					documentmgDao.create(docModel); //DB 입력
				}else {
					documentmgDao.filedelete(docModel); //DB 입력
				}
				
			} catch (Exception e) {
				logger.debug(e);
			}
		}
		
		return doc_seq;
	}
	
	
	
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public String doc_file_del(DocumentmgModel documentmgModel, String prefix)  throws Exception {
		
		String result = "";
		
//		if(documentmgModel.getDoc_seq() != null && (documentmgModel.getDoc_seq()).length() != 0) {
		if(documentmgModel.getDoc_seq() != null && prefix =="One") {
		DocumentmgModel docModelt = new DocumentmgModel();
		
		docModelt.setDoc_seq(documentmgModel.getDoc_seq());
		docModelt.setFile_path(documentmgModel.getFile_path());

		String localFilePath = propertyService.getString(propertyFilePathKey) + documentmgModel.getFile_path() + File.separator;
		
		File file = new File(localFilePath+documentmgModel.getFile_name());
		if(file.isFile()){ 
			if (!file.delete()) { // 파일 삭제
				result = "error";
				throw new Exception("파일 삭제 실패");
				
			}
		}
		
	
		DocumentmgModel docModelt2 = new DocumentmgModel();
		docModelt2.setDoc_seq(documentmgModel.getDoc_seq());
		docModelt2.setUpdate_user_id(documentmgModel.getSession_user_id());
		
		documentmgDao.filedelete(docModelt2); // DB 에서 삭제
		
		
		}else if(prefix =="List") { // 리스트 화면에서 체크박스 삭제
			
			String doc_seq[] =documentmgModel.getChk_doc_seq();
			for (int i = 0; i < doc_seq.length; i++) {
				DocumentmgModel docModelt2 = new DocumentmgModel();
				docModelt2.setDoc_seq(doc_seq[i]);
				documentmgDao.delete(docModelt2);
			}
			
			
		}else{ // 저장이 아닌 페이지전환 될때 

		logger.debug("---------------------------------------------------------------------");
		logger.debug("----------------------저장하지 않고 나올경우----------------------------");
		logger.debug("---------------------------------------------------------------------");
			DocumentmgModel findDelListModel = new DocumentmgModel();
			findDelListModel.setDoc_seq(documentmgModel.getDoc_seq());
			
			List<DocumentmgModel> deleteFileList = new ArrayList<DocumentmgModel>();
			List<DocumentmgModel> fileList = (List<DocumentmgModel>) documentmgDao.readList(findDelListModel);
			
			for (DocumentmgModel delFlist : fileList) {	
				DocumentmgModel docModelt = new DocumentmgModel();
				docModelt.setDoc_seq(documentmgModel.getDoc_seq());
				docModelt.setFile_path(documentmgModel.getFile_path());
				deleteFileList.add(docModelt);
			}
			
			deleteLocalFile(deleteFileList);
			
			for (DocumentmgModel atfm : deleteFileList) {
				documentmgDao.delete(atfm);
			}
		
		}//end else
		
		
		
		return result;
	}
	
	private void readExcelFile(File file, String master_file_id) throws Exception{
		List<Pkg21FileModel> list = new ArrayList<Pkg21FileModel>();
		Pkg21FileModel newModel = new Pkg21FileModel();
		newModel.setTest_item("NEW");
		
		Pkg21FileModel opModel = new Pkg21FileModel();
		opModel.setTest_item("OP");
		
		Pkg21FileModel pnModel = new Pkg21FileModel();
		pnModel.setTest_item("PN");
		
		Pkg21FileModel crModel = new Pkg21FileModel();
		crModel.setTest_item("CR");
		
		Pkg21FileModel selfModel = new Pkg21FileModel();
		selfModel.setTest_item("SELF");
		
		Pkg21FileModel regModel = new Pkg21FileModel();
		regModel.setTest_item("REG");
		
		Pkg21FileModel abnModel = new Pkg21FileModel();
		abnModel.setTest_item("ABN");
		
		Pkg21FileModel rmModel = new Pkg21FileModel();
		rmModel.setTest_item("RM");
		
		
		
		OPCPackage opcPackage = OPCPackage.openOrCreate((File) file);
		
		//		OPCPackage.open(new File("D:\\abc.xlsx")); 
		
		//FileInputStream fis=new FileInputStream("D:\\abc.xlsx");
		
		XSSFWorkbook workbook=new XSSFWorkbook(opcPackage);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator(); 
		DecimalFormat df = new DecimalFormat();
		 
		int rowindex=0;
		
		int columnindex=0;
		
		//시트 수 (첫번째에만 존재하므로 0을 준다)
		
		//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
		
		XSSFSheet sheet=workbook.getSheetAt(0);
		
		//행의 수
		
		int rows=sheet.getPhysicalNumberOfRows();
		
		for(rowindex=1;rowindex<rows;rowindex++){
		
		    //행을읽는다
		
		    XSSFRow row=sheet.getRow(rowindex);
		
		    if(row !=null){
		
		        //셀의 수
		
//		        int cells=row.getPhysicalNumberOfCells();
		        int cells=row.getLastCellNum();
		
		        for(columnindex=0;columnindex<=cells;columnindex++){
		
		            //셀값을 읽는다
		
		            XSSFCell cell=row.getCell(columnindex);
		
		            String value="";
		
		            //셀이 빈값일경우를 위한 널체크
		            
		            if(cell==null){
		            	continue;
		            }else{
		
		                //타입별로 내용 읽기
		
		                switch (cell.getCellType()){
		
		                case XSSFCell.CELL_TYPE_FORMULA:
		
		                    value=cell.getCellFormula();
		                    
		                    if(!"".equals(cell.toString())){
		                    	if(evaluator.evaluateFormulaCell(cell)==XSSFCell.CELL_TYPE_NUMERIC){
		                    		double fddata = cell.getNumericCellValue();
		                    		value = df.format(fddata);
		                    	}else if(evaluator.evaluateFormulaCell(cell)==XSSFCell.CELL_TYPE_STRING){
		                    		value = cell.getStringCellValue();
		                    	}else if(evaluator.evaluateFormulaCell(cell)==XSSFCell.CELL_TYPE_BOOLEAN){
		                    		boolean fbdata = cell.getBooleanCellValue();
		                    		value = String.valueOf(fbdata);
		                    	}
		                    }
		                    
		                    break;
		
		                case XSSFCell.CELL_TYPE_NUMERIC:
		
		                    value=cell.getNumericCellValue()+"";
		
		                    break;
		
		                case XSSFCell.CELL_TYPE_STRING:
		
		                    value=cell.getStringCellValue()+"";
		
		                    break;
		
		                case XSSFCell.CELL_TYPE_BLANK:
		
		                    value=cell.getBooleanCellValue()+"";
		
		                    break;
		
		                case XSSFCell.CELL_TYPE_ERROR:
		
		                    value=cell.getErrorCellValue()+"";
		
		                    break;
		
		                }
		
		            }

		            //SVT
		            if(rowindex==18 && columnindex==7){
		            	newModel.setSvt_cnt(value);
		            }else if(rowindex==18 && columnindex==8){
		            	newModel.setSvt_ok(value);
		            }else if(rowindex==18 && columnindex==9){
		            	newModel.setSvt_nok(value);
		            }else if(rowindex==18 && columnindex==10){
		            	newModel.setSvt_cok(value);
		            }else if(rowindex==18 && columnindex==11){
		            	newModel.setSvt_pok(value);
		            }else if(rowindex==19 && columnindex==7){
		            	opModel.setSvt_cnt(value);
		            }else if(rowindex==19 && columnindex==8){
		            	opModel.setSvt_ok(value);
		            }else if(rowindex==19 && columnindex==9){
		            	opModel.setSvt_nok(value);
		            }else if(rowindex==19 && columnindex==10){
		            	opModel.setSvt_cok(value);
		            }else if(rowindex==19 && columnindex==11){
		            	opModel.setSvt_pok(value);
		            }else if(rowindex==20 && columnindex==7){
		            	pnModel.setSvt_cnt(value);
		            }else if(rowindex==20 && columnindex==8){
		            	pnModel.setSvt_ok(value);
		            }else if(rowindex==20 && columnindex==9){
		            	pnModel.setSvt_nok(value);
		            }else if(rowindex==20 && columnindex==10){
		            	pnModel.setSvt_cok(value);
		            }else if(rowindex==20 && columnindex==11){
		            	pnModel.setSvt_pok(value);
		            }else if(rowindex==21 && columnindex==7){
		            	crModel.setSvt_cnt(value);
		            }else if(rowindex==21 && columnindex==8){
		            	crModel.setSvt_ok(value);
		            }else if(rowindex==21 && columnindex==9){
		            	crModel.setSvt_nok(value);
		            }else if(rowindex==21 && columnindex==10){
		            	crModel.setSvt_cok(value);
		            }else if(rowindex==21 && columnindex==11){
		            	crModel.setSvt_pok(value);
		            }else if(rowindex==22 && columnindex==7){
		            	selfModel.setSvt_cnt(value);
		            }else if(rowindex==22 && columnindex==8){
		            	selfModel.setSvt_ok(value);
		            }else if(rowindex==22 && columnindex==9){
		            	selfModel.setSvt_nok(value);
		            }else if(rowindex==22 && columnindex==10){
		            	selfModel.setSvt_cok(value);
		            }else if(rowindex==22 && columnindex==11){
		            	selfModel.setSvt_pok(value);
		            }else if(rowindex==23 && columnindex==7){
		            	regModel.setSvt_cnt(value);
		            }else if(rowindex==23 && columnindex==8){
		            	regModel.setSvt_ok(value);
		            }else if(rowindex==23 && columnindex==9){
		            	regModel.setSvt_nok(value);
		            }else if(rowindex==23 && columnindex==10){
		            	regModel.setSvt_cok(value);
		            }else if(rowindex==23 && columnindex==11){
		            	regModel.setSvt_pok(value);
		            }else if(rowindex==24 && columnindex==7){
		            	abnModel.setSvt_cnt(value);
		            }else if(rowindex==24 && columnindex==8){
		            	abnModel.setSvt_ok(value);
		            }else if(rowindex==24 && columnindex==9){
		            	abnModel.setSvt_nok(value);
		            }else if(rowindex==24 && columnindex==10){
		            	abnModel.setSvt_cok(value);
		            }else if(rowindex==24 && columnindex==11){
		            	abnModel.setSvt_pok(value);
		            }else if(rowindex==25 && columnindex==7){
		            	rmModel.setSvt_cnt(value);
		            }else if(rowindex==25 && columnindex==8){
		            	rmModel.setSvt_ok(value);
		            }else if(rowindex==25 && columnindex==9){
		            	rmModel.setSvt_nok(value);
		            }else if(rowindex==25 && columnindex==10){
		            	rmModel.setSvt_cok(value);
		            }else if(rowindex==25 && columnindex==11){
		            	rmModel.setSvt_pok(value);
		            //DVT
		            }else if(rowindex==29 && columnindex==7){
		            	newModel.setDvt_cnt(value);
		            }else if(rowindex==29 && columnindex==8){
		            	newModel.setDvt_ok(value);
		            }else if(rowindex==29 && columnindex==9){
		            	newModel.setDvt_nok(value);
		            }else if(rowindex==29 && columnindex==10){
		            	newModel.setDvt_cok(value);
		            }else if(rowindex==29 && columnindex==11){
		            	newModel.setDvt_pok(value);
		            }else if(rowindex==30 && columnindex==7){
		            	opModel.setDvt_cnt(value);
		            }else if(rowindex==30 && columnindex==8){
		            	opModel.setDvt_ok(value);
		            }else if(rowindex==30 && columnindex==9){
		            	opModel.setDvt_nok(value);
		            }else if(rowindex==30 && columnindex==10){
		            	opModel.setDvt_cok(value);
		            }else if(rowindex==30 && columnindex==11){
		            	opModel.setDvt_pok(value);
		            }else if(rowindex==31 && columnindex==7){
		            	pnModel.setDvt_cnt(value);
		            }else if(rowindex==31 && columnindex==8){
		            	pnModel.setDvt_ok(value);
		            }else if(rowindex==31 && columnindex==9){
		            	pnModel.setDvt_nok(value);
		            }else if(rowindex==31 && columnindex==10){
		            	pnModel.setDvt_cok(value);
		            }else if(rowindex==31 && columnindex==11){
		            	pnModel.setDvt_pok(value);
		            }else if(rowindex==32 && columnindex==7){
		            	crModel.setDvt_cnt(value);
		            }else if(rowindex==32 && columnindex==8){
		            	crModel.setDvt_ok(value);
		            }else if(rowindex==32 && columnindex==9){
		            	crModel.setDvt_nok(value);
		            }else if(rowindex==32 && columnindex==10){
		            	crModel.setDvt_cok(value);
		            }else if(rowindex==32 && columnindex==11){
		            	crModel.setDvt_pok(value);
		            }else if(rowindex==33 && columnindex==7){
		            	selfModel.setDvt_cnt(value);
		            }else if(rowindex==33 && columnindex==8){
		            	selfModel.setDvt_ok(value);
		            }else if(rowindex==33 && columnindex==9){
		            	selfModel.setDvt_nok(value);
		            }else if(rowindex==33 && columnindex==10){
		            	selfModel.setDvt_cok(value);
		            }else if(rowindex==33 && columnindex==11){
		            	selfModel.setDvt_pok(value);
		            }else if(rowindex==34 && columnindex==7){
		            	regModel.setDvt_cnt(value);
		            }else if(rowindex==34 && columnindex==8){
		            	regModel.setDvt_ok(value);
		            }else if(rowindex==34 && columnindex==9){
		            	regModel.setDvt_nok(value);
		            }else if(rowindex==34 && columnindex==10){
		            	regModel.setDvt_cok(value);
		            }else if(rowindex==34 && columnindex==11){
		            	regModel.setDvt_pok(value);
		            }else if(rowindex==35 && columnindex==7){
		            	abnModel.setDvt_cnt(value);
		            }else if(rowindex==35 && columnindex==8){
		            	abnModel.setDvt_ok(value);
		            }else if(rowindex==35 && columnindex==9){
		            	abnModel.setDvt_nok(value);
		            }else if(rowindex==35 && columnindex==10){
		            	abnModel.setDvt_cok(value);
		            }else if(rowindex==35 && columnindex==11){
		            	abnModel.setDvt_pok(value);
		            }else if(rowindex==36 && columnindex==7){
		            	rmModel.setDvt_cnt(value);
		            }else if(rowindex==36 && columnindex==8){
		            	rmModel.setDvt_ok(value);
		            }else if(rowindex==36 && columnindex==9){
		            	rmModel.setDvt_nok(value);
		            }else if(rowindex==36 && columnindex==10){
		            	rmModel.setDvt_cok(value);
		            }else if(rowindex==36 && columnindex==11){
		            	rmModel.setDvt_pok(value);
		            //CVT	
		            }else if(rowindex==40 && columnindex==7){
		            	newModel.setCvt_cnt(value);
		            }else if(rowindex==40 && columnindex==8){
		            	newModel.setCvt_ok(value);
		            }else if(rowindex==40 && columnindex==9){
		            	newModel.setCvt_nok(value);
		            }else if(rowindex==40 && columnindex==10){
		            	newModel.setCvt_cok(value);
		            }else if(rowindex==40 && columnindex==11){
		            	newModel.setCvt_pok(value);
		            }else if(rowindex==41 && columnindex==7){
		            	opModel.setCvt_cnt(value);
		            }else if(rowindex==41 && columnindex==8){
		            	opModel.setCvt_ok(value);
		            }else if(rowindex==41 && columnindex==9){
		            	opModel.setCvt_nok(value);
		            }else if(rowindex==41 && columnindex==10){
		            	opModel.setCvt_cok(value);
		            }else if(rowindex==41 && columnindex==11){
		            	opModel.setCvt_pok(value);
		            }else if(rowindex==42 && columnindex==7){
		            	pnModel.setCvt_cnt(value);
		            }else if(rowindex==42 && columnindex==8){
		            	pnModel.setCvt_ok(value);
		            }else if(rowindex==42 && columnindex==9){
		            	pnModel.setCvt_nok(value);
		            }else if(rowindex==42 && columnindex==10){
		            	pnModel.setCvt_cok(value);
		            }else if(rowindex==42 && columnindex==11){
		            	pnModel.setCvt_pok(value);
		            }else if(rowindex==43 && columnindex==7){
		            	crModel.setCvt_cnt(value);
		            }else if(rowindex==43 && columnindex==8){
		            	crModel.setCvt_ok(value);
		            }else if(rowindex==43 && columnindex==9){
		            	crModel.setCvt_nok(value);
		            }else if(rowindex==43 && columnindex==10){
		            	crModel.setCvt_cok(value);
		            }else if(rowindex==43 && columnindex==11){
		            	crModel.setCvt_pok(value);
		            }else if(rowindex==44 && columnindex==7){
		            	selfModel.setCvt_cnt(value);
		            }else if(rowindex==44 && columnindex==8){
		            	selfModel.setCvt_ok(value);
		            }else if(rowindex==44 && columnindex==9){
		            	selfModel.setCvt_nok(value);
		            }else if(rowindex==44 && columnindex==10){
		            	selfModel.setCvt_cok(value);
		            }else if(rowindex==44 && columnindex==11){
		            	selfModel.setCvt_pok(value);
		            }else if(rowindex==45 && columnindex==7){
		            	regModel.setCvt_cnt(value);
		            }else if(rowindex==45 && columnindex==8){
		            	regModel.setCvt_ok(value);
		            }else if(rowindex==45 && columnindex==9){
		            	regModel.setCvt_nok(value);
		            }else if(rowindex==45 && columnindex==10){
		            	regModel.setCvt_cok(value);
		            }else if(rowindex==45 && columnindex==11){
		            	regModel.setCvt_pok(value);
		            }else if(rowindex==46 && columnindex==7){
		            	abnModel.setCvt_cnt(value);
		            }else if(rowindex==46 && columnindex==8){
		            	abnModel.setCvt_ok(value);
		            }else if(rowindex==46 && columnindex==9){
		            	abnModel.setCvt_nok(value);
		            }else if(rowindex==46 && columnindex==10){
		            	abnModel.setCvt_cok(value);
		            }else if(rowindex==46 && columnindex==11){
		            	abnModel.setCvt_pok(value);
		            }else if(rowindex==47 && columnindex==7){
		            	rmModel.setCvt_cnt(value);
		            }else if(rowindex==47 && columnindex==8){
		            	rmModel.setCvt_ok(value);
		            }else if(rowindex==47 && columnindex==9){
		            	rmModel.setCvt_nok(value);
		            }else if(rowindex==47 && columnindex==10){
		            	rmModel.setCvt_cok(value);
		            }else if(rowindex==47 && columnindex==11){
		            	rmModel.setCvt_pok(value);
		            }
		        }
		    }
		}
		newModel.setMaster_file_id(master_file_id);
		newModel.setOrd("1");
		opModel.setMaster_file_id(master_file_id);
		opModel.setOrd("2");
		pnModel.setMaster_file_id(master_file_id);
		pnModel.setOrd("3");
		crModel.setMaster_file_id(master_file_id);
		crModel.setOrd("4");
		selfModel.setMaster_file_id(master_file_id);
		selfModel.setOrd("5");
		regModel.setMaster_file_id(master_file_id);
		regModel.setOrd("6");
		abnModel.setMaster_file_id(master_file_id);
		abnModel.setOrd("7");
		rmModel.setMaster_file_id(master_file_id);
		rmModel.setOrd("8");
		
		list.add(newModel);
		list.add(opModel);
		list.add(pnModel);
		list.add(crModel);
		list.add(selfModel);
		list.add(regModel);
		list.add(abnModel);
		list.add(rmModel);
		
		//delete
		Pkg21FileModel delModel = new Pkg21FileModel();
		delModel.setMaster_file_id(master_file_id);
		pkg21DAO.pkg_result_delete(delModel);
		
		logger.debug(list.size());
		//create
		for(Pkg21FileModel pkg21 : list){
			logger.debug("========data========");
			logger.debug(pkg21.getMaster_file_id());
			logger.debug(pkg21.getTest_item());
			logger.debug(pkg21.getSvt_cnt());
			logger.debug(pkg21.getSvt_ok());
			logger.debug(pkg21.getSvt_nok());
			logger.debug(pkg21.getSvt_cok());
			logger.debug(pkg21.getSvt_pok());
			logger.debug(pkg21.getDvt_cnt());
			logger.debug(pkg21.getDvt_ok());
			logger.debug(pkg21.getDvt_nok());
			logger.debug(pkg21.getDvt_cok());
			logger.debug(pkg21.getDvt_pok());
			logger.debug(pkg21.getCvt_cnt());
			logger.debug(pkg21.getCvt_ok());
			logger.debug(pkg21.getCvt_nok());
			logger.debug(pkg21.getCvt_cok());
			logger.debug(pkg21.getCvt_pok());
			logger.debug("========data========");
			pkg21DAO.pkg_result_create(pkg21);
		}
//		return list;
	}
	
	
	public File convert(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile();
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}
	
	private void equipmentExcelCreate(File file, String master_file_id, String work_gubun) throws Exception{
		PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
		Pkg21Model pkg21 = new Pkg21Model();
		pkg21.setMaster_file_id(master_file_id);
		pkgEquipmentModel.setWork_gubun(work_gubun);
		pkgEquipmentModel.setPkg_seq(pkg21DAO.getPkgSeq(pkg21));
		PkgEquipmentModel pEModel = new PkgEquipmentModel();
		pEModel = pkgEquipmentService.readOrd(pkgEquipmentModel);
		pkgEquipmentModel.setOrd(pEModel.getOrd());
		
		OPCPackage opcPackage = OPCPackage.openOrCreate((File) file);
		
		//		OPCPackage.open(new File("D:\\abc.xlsx")); 
		
		//FileInputStream fis=new FileInputStream("D:\\abc.xlsx");
		
		XSSFWorkbook workbook=new XSSFWorkbook(opcPackage);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator(); 
		DecimalFormat df = new DecimalFormat();
		 
		int rowindex=0;
		
		int columnindex=0;
		
		//시트 수 (첫번째에만 존재하므로 0을 준다)
		
		//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
		
		XSSFSheet sheet=workbook.getSheetAt(0);
		
		//행의 수
		
		int rows=sheet.getPhysicalNumberOfRows();
		
		for(rowindex=1;rowindex<rows;rowindex++){
		
		    //행을읽는다
		
		    XSSFRow row=sheet.getRow(rowindex);
		
		    if(row !=null){
		
		        //셀의 수
		
//		        int cells=row.getPhysicalNumberOfCells();
		        int cells=row.getLastCellNum();
		
		        for(columnindex=0;columnindex<=cells;columnindex++){
		
		            //셀값을 읽는다
		
		            XSSFCell cell=row.getCell(columnindex);
		
		            String value="";
		
		            //셀이 빈값일경우를 위한 널체크
		            
		            if(cell==null){
		            	continue;
		            }else{
		
		                //타입별로 내용 읽기
		
		                switch (cell.getCellType()){
		
		                case XSSFCell.CELL_TYPE_FORMULA:
		
		                    value=cell.getCellFormula();
		                    
		                    if(!"".equals(cell.toString())){
		                    	if(evaluator.evaluateFormulaCell(cell)==XSSFCell.CELL_TYPE_NUMERIC){
		                    		double fddata = cell.getNumericCellValue();
		                    		value = df.format(fddata);
		                    	}else if(evaluator.evaluateFormulaCell(cell)==XSSFCell.CELL_TYPE_STRING){
		                    		value = cell.getStringCellValue();
		                    	}else if(evaluator.evaluateFormulaCell(cell)==XSSFCell.CELL_TYPE_BOOLEAN){
		                    		boolean fbdata = cell.getBooleanCellValue();
		                    		value = String.valueOf(fbdata);
		                    	}
		                    }
		                    
		                    break;
		
		                case XSSFCell.CELL_TYPE_NUMERIC:
		
		                    value=(int)cell.getNumericCellValue()+"";
		
		                    break;
		
		                case XSSFCell.CELL_TYPE_STRING:
		
		                    value=cell.getStringCellValue()+"";
		
		                    break;
		
		                case XSSFCell.CELL_TYPE_BLANK:
		
		                    value=cell.getBooleanCellValue()+"";
		
		                    break;
		
		                case XSSFCell.CELL_TYPE_ERROR:
		
		                    value=cell.getErrorCellValue()+"";
		
		                    break;
		
		                }
		
		            }
		            
		            if(columnindex == 0){
		            	pkgEquipmentModel.setSystem_nm(value);
		            }else if(columnindex == 1){
		            	if("false".equals(value)){
		            		pkgEquipmentModel.setCuid("");
		            	}else{
		            		pkgEquipmentModel.setCuid(value);
		            	}
		            }else if(columnindex == 2){
		            	if("false".equals(value)){
		            		pkgEquipmentModel.setEqp_id("");
		            	}else{
		            		pkgEquipmentModel.setEqp_id(value);
		            	}
		            }else if(columnindex == 3){
		            	if("false".equals(value)){
		            		pkgEquipmentModel.setSisul_name("");
		            	}else{
		            		pkgEquipmentModel.setSisul_name(value);
		            	}
		            }
		        }
		        
		        pkgEquipmentService.createAccess(pkgEquipmentModel);
		    }
		}
	}
}
