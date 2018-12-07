package com.pkms.pkgmg.pkg.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.pkgmg.pkg.model.PkgDetailModel;
import com.pkms.pkgmg.pkg.model.PkgDetailVariableModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.tempmg.model.TempmgModel;
import com.pkms.tempmg.service.TempmgColumnServiceIf;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ExcelUtil;

/**
 * 보완적용내용 등록/수정 관련 service<br/>
 *  - 엑셀업로드 등
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Service("PkgSupplementService")
public class PkgSupplementService implements PkgSupplementServiceIf {

	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	private String propertyFilePathKey = "Globals.fileStorePath";

	@Resource(name = "TempmgColumnService")
	private TempmgColumnServiceIf tempmgColumnService;

	@Resource(name = "PkgDetailVariableService")
	private PkgDetailVariableServiceIf pkgDetailVariableService;
	
	@Resource(name = "PkgDetailService")
	private PkgDetailServiceIf pkgDetailService;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;

	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;

	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;

	@Override
	public void create(PkgModel pkgModel) throws Exception {
		// 이전에 생성했던 보완적용내역 삭제
		pkgDetailVariableService.delete(pkgModel);

		// 신규 보완적용내역 생성
		// 파일 유효성 체크 시 생성한 객체 read(SessionService.create)
		PkgModel pkgModelData = (PkgModel) sessionService.read("PKG_SUPPLEMENT_PkgModel");
		this.pkg_detail_variable(pkgModel, pkgModelData);
		
		if(!pkgModel.getTpl_seq().equals(pkgModelData.getTpl_seq())) {
			//tpl_seq 업데이트
			pkgModel.setTpl_seq(pkgModelData.getTpl_seq());
			pkgModel.setTpl_ver(pkgModelData.getTpl_ver());
			pkgService.tpl_seq_update(pkgModel);
		} else {
			pkgModel.setTpl_ver(pkgModelData.getTpl_ver());
		}
	}

	/**
	 * 보완적용내역 업로드 및 유효성 검사
	 * @param pkgModel
	 * @throws Exception
	 */
	private void excelValidationRead(PkgModel pkgModel) throws Exception {
		List<PkgDetailModel> pkgDetailModelList = new ArrayList<PkgDetailModel>();
		List<List<PkgDetailVariableModel>> pkgDetailVariableModelList = new ArrayList<List<PkgDetailVariableModel>>();
		List<PkgDetailVariableModel> pkgDetailVariableModelListSub = new ArrayList<PkgDetailVariableModel>();

		String isOk = "O";
		String isNotOk = "X";
		List<String> noList = new ArrayList<String>();
		
		if (!"".equals(pkgModel.getMaster_file_id())) {
			//현재 사용 중인 템플릿 버전 조회 
			TempmgModel tempmgModel = new TempmgModel();
			tempmgModel.setUse_yn("Y");
			tempmgModel.setPosition("");
			List<TempmgModel> tempmgList = tempmgColumnService.readList(tempmgModel);
			int tempmgListSize = tempmgList.size();
			
			// Template Validation
			if (tempmgList == null || tempmgList.size() < 1) {
				throw new Exception("error.biz.사용가능한 템플릿이 없습니다. 관리자에게 문의하세요.");
			}
			
			// 첨부 파일 조회
			fileManageService.read(pkgModel);

			// system file path
			String localFilePath = propertyService.getString(propertyFilePathKey);

			// poi excel read
			AttachFileModel attachFileModel = pkgModel.getFile_excel_upload();
			String org_file_name = attachFileModel.getFile_org_name();
			String sys_file_name = attachFileModel.getFile_name();
			String file_path = attachFileModel.getFile_path();

			String excel_file = localFilePath + File.separator + file_path + File.separator + sys_file_name;

			Workbook wb = null;
			if (org_file_name.matches(".*.xls") || org_file_name.matches(".*.xlss")) {
				FileInputStream fis = new FileInputStream(excel_file);
				POIFSFileSystem filein = new POIFSFileSystem(fis);
				wb = new HSSFWorkbook(filein);
			} else if (org_file_name.matches(".*.xlsx") || org_file_name.matches(".*.xlsxx")) {
				InputStream fis = new FileInputStream(excel_file);
				wb = WorkbookFactory.create(fis);
			} else {
				throw new Exception("error.biz.엑셀 파일만 업로드가 가능합니다.");
			}

			Sheet ws = wb.getSheetAt(0);
			int rows = ws.getPhysicalNumberOfRows();

			Row headRow = ws.getRow(0);
			if(headRow == null) {
				pkgModel.setPkg_detail_validation_success_yn("현재 사용되는 템플릿 양식과 다릅니다. 확인 후 다시 검사하시기 바랍니다.");
				return;
//				throw new Exception("error.biz.현재 사용되는 템플릿 양식과 다릅니다. 확인 후 다시 검사하시기 바랍니다.");
			}
			
			Iterator<?> it = headRow.iterator();
			int colCount = 0;
			Object o = null;
			String headerStr = null;
			TempmgModel checkModel = null;
			
			while(it.hasNext()) {
				o = it.next();
				
				if(o != null) {
					headerStr = ((Cell)o).getStringCellValue();
					checkModel = tempmgList.get(colCount);
					
//					if(!headerStr.equals(checkModel.getTitle())) {
//						pkgModel.setPkg_detail_validation_success_yn("현재 사용되는 템플릿 양식과 다릅니다. 확인 후 다시 검사하시기 바랍니다.(파일명에 이상이 있습니다.)");
//						return;
////						throw new Exception("error.biz.현재 사용되는 템플릿 양식과 다릅니다. 확인 후 다시 검사하시기 바랍니다.");
//					}
					colCount++;
				}
			}
			
			if(colCount != tempmgListSize) {
				pkgModel.setPkg_detail_validation_success_yn("현재 사용되는 템플릿 양식과 다릅니다. 확인 후 다시 검사하시기 바랍니다.(업로드한 파일의 항목수에 이상이 있습니다.)");
				return;
//				throw new Exception("error.biz.현재 사용되는 템플릿 양식과 다릅니다. 확인 후 다시 검사하시기 바랍니다.");
			}
			
			Row row = null;
			String no = null;
			String new_pn_cr = null;
			String tagGubun = null;
			String dataValue = null;
			String importance = null;
			String commercial_validation = null;
			String require_category = null;
			String verification_sector = null;
			String self_validation = null;
			
			PkgDetailVariableModel pkgDetailVariableModel = null;
			
			for (int i = 1; i <= rows; i++) {
				row = ws.getRow(i);
				if (row != null) {
					PkgDetailModel pkgDetailModel = new PkgDetailModel();
					pkgDetailVariableModelListSub = new ArrayList<PkgDetailVariableModel>();
					int pkgDetailVariableIndex = 0;
					for (int j = 0; j < colCount; j++) {
						dataValue = getRowData(row.getCell(j));
						pkgDetailVariableModel = new PkgDetailVariableModel();

						// No Check
						if (j == 0) {
							
							if (dataValue == null) {
								pkgDetailModel.setValidation_result(isNotOk);
								pkgDetailModel.setValidation_result_content("<font color='red'>번호는 필수항목 입니다.</font>");
								pkgModel.setPkg_detail_validation_success_yn("N");
							} else if (row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
								pkgDetailModel.setNo(dataValue);
								pkgDetailModel.setValidation_result(isNotOk);
								pkgDetailModel.setValidation_result_content("<font color='red'>번호는 숫자입력 입니다.</font>");
								pkgModel.setPkg_detail_validation_success_yn("N");
							} else {
								
								// No 중복체크
								boolean isNo = false;
								NO_LIST: for (int k = 0; k < noList.size(); k++) {
									if (dataValue.equals(noList.get(k))) {
										isNo = true;
										continue NO_LIST;
									}
								}

								if (isNo) {
									pkgDetailModel.setNo(dataValue);
									pkgDetailModel.setValidation_result(isNotOk);
									pkgDetailModel.setValidation_result_content("<font color='red'>중복된 번호가 존재합니다.</font>");
									pkgModel.setPkg_detail_validation_success_yn("N");
								} else {
									pkgDetailModel.setNo(dataValue);
									pkgDetailModel.setValidation_result(isOk);
								}

								noList.add(dataValue);
							}

							// New/pn/cr Check
						} else if (j == 1) {
							tagGubun = "".equals(pkgDetailModel.getValidation_result_content()) ? "" : pkgDetailModel.getValidation_result_content() + "<br/>";
							if (dataValue == null) {
								pkgDetailModel.setValidation_result(isNotOk);
								pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "분류는 필수항목 입니다.</font>");
								pkgModel.setPkg_detail_validation_success_yn("N");
							} else {
								new_pn_cr = dataValue.toUpperCase().trim();

								if (!"신규".equals(new_pn_cr) && !"보완".equals(new_pn_cr) && !"개선".equals(new_pn_cr)) {
									pkgDetailModel.setNew_pn_cr_gubun(new_pn_cr);
									pkgDetailModel.setValidation_result(isNotOk);
									pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "분류는 신규/보완/개선 중 선택.</font>");
									pkgModel.setPkg_detail_validation_success_yn("N");
								} else {
									String ok = isNotOk.equals(pkgDetailModel.getValidation_result()) ? isNotOk : isOk;
									pkgDetailModel.setNew_pn_cr_gubun(new_pn_cr);
									pkgDetailModel.setValidation_result(ok);
								}
							}							
						// 나머지 필수 체크 없음
						} else {
							if (dataValue != null && !"".equals(dataValue.trim())) { //값이 있는 경우
								if(dataValue.length() > 1200){ //글자수가 1200글자 이상 일때
									pkgDetailModel.setValidation_result(isNotOk);
									pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "최대 1,200자 까지만 입력 가능.</font>");
								}else{
									if (row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC) { //숫자 형식일 때
										//중요도 체크(CRI/MAJ/MIN)
										if (j == 2) {
											tagGubun = "".equals(pkgDetailModel.getValidation_result_content()) ? "" : pkgDetailModel.getValidation_result_content() + "<br/>";
											importance = dataValue.toUpperCase().trim();
	
											if (!"CRI".equals(importance) && !"MAJ".equals(importance) && !"MIN".equals(importance)) {
												pkgDetailModel.setValidation_result(isNotOk);
												pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "중요도는 CRI/MAJ/MIN 중 선택.</font>");
												pkgModel.setPkg_detail_validation_success_yn("N");
											} else {
												String ok = isNotOk.equals(pkgDetailModel.getValidation_result()) ? isNotOk : isOk;
												pkgDetailModel.setValidation_result(ok);
											}
										}
										
										//상용 검증 결과 체크(OK/NOK/COK/null(미입력))
										if (j == 4) {
											tagGubun = "".equals(pkgDetailModel.getValidation_result_content()) ? "" : pkgDetailModel.getValidation_result_content() + "<br/>";
												pkgDetailModel.setValidation_result(isNotOk);
												//121031 - junhee
												pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "상용 검증 결과는 null(미입력) 입니다..</font>");
												pkgModel.setPkg_detail_validation_success_yn("N");
											
										}
										
										////요구 분류 체크(운용/기술원/공급사자체/null(미입력))
										if (j == 6) {
											tagGubun = "".equals(pkgDetailModel.getValidation_result_content()) ? "" : pkgDetailModel.getValidation_result_content() + "<br/>";
											require_category = dataValue.toUpperCase().trim();
				
											if (!"운용".equals(require_category) && !"기술원".equals(require_category) && !"공급사자체".equals(require_category)) {
												pkgDetailModel.setValidation_result(isNotOk);
												pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "요구 분류 체크는 운용/기술원/공급사자체/null(미입력) 중 선택.</font>");
												pkgModel.setPkg_detail_validation_success_yn("N");
											}
										}
										
										//공급사 검증결과는(OK/NOK/COK)
										if (j == 15) {
											tagGubun = "".equals(pkgDetailModel.getValidation_result_content()) ? "" : pkgDetailModel.getValidation_result_content() + "<br/>";
											self_validation = dataValue.toUpperCase().trim();
	
											if (!"OK".equals(self_validation) && !"NOK".equals(self_validation) && !"COK".equals(self_validation)) {
												pkgDetailModel.setValidation_result(isNotOk);
												pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "공급사 검증결과는 OK/NOK/COK 중 선택.</font>");
												pkgModel.setPkg_detail_validation_success_yn("N");
											} else {
												String ok = isNotOk.equals(pkgDetailModel.getValidation_result()) ? isNotOk : isOk;
												pkgDetailModel.setValidation_result(ok);
											}
										}
										
	                                    Double dValue = new Double(dataValue);
	                                    NumberFormat formatter = new DecimalFormat("0");
	                                    pkgDetailVariableModel.setContent(formatter.format(dValue));
									
									} else { //숫자형식이 아닐때
										
										//중요도 체크(CRI/MAJ/MIN)
										if (j == 2) {
											tagGubun = "".equals(pkgDetailModel.getValidation_result_content()) ? "" : pkgDetailModel.getValidation_result_content() + "<br/>";
											importance = dataValue.toUpperCase().trim();
	
											if (!"CRI".equals(importance) && !"MAJ".equals(importance) && !"MIN".equals(importance)) {
												pkgDetailModel.setValidation_result(isNotOk);
												pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "중요도는 CRI/MAJ/MIN 중 선택.</font>");
												pkgModel.setPkg_detail_validation_success_yn("N");
											} else {
												String ok = isNotOk.equals(pkgDetailModel.getValidation_result()) ? isNotOk : isOk;
												pkgDetailModel.setValidation_result(ok);
											}
										}
										
										//상용 검증 결과 체크(OK/NOK/COK/null(미입력))
										if (j == 4) {
											tagGubun = "".equals(pkgDetailModel.getValidation_result_content()) ? "" : pkgDetailModel.getValidation_result_content() + "<br/>";	
	//										commercial_validation = dataValue.toUpperCase().trim();
												
	//										if (!"OK".equals(commercial_validation) && !"NOK".equals(commercial_validation) && !"COK".equals(commercial_validation)) {
												pkgDetailModel.setValidation_result(isNotOk);
												pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "상용 검증 결과는 null(미입력) 입니다.</font>");
												pkgModel.setPkg_detail_validation_success_yn("N");
	//										}
										}
										
										//요구 분류 체크(운용/기술원/공급사자체/null(미입력))
										if (j == 6) {
											tagGubun = "".equals(pkgDetailModel.getValidation_result_content()) ? "" : pkgDetailModel.getValidation_result_content() + "<br/>";
											require_category = dataValue.toUpperCase().trim();
				
											if (!"운용".equals(require_category) && !"기술원".equals(require_category) && !"공급사자체".equals(require_category)) {
												pkgDetailModel.setValidation_result(isNotOk);
												pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "요구 분류 체크는 운용/기술원/공급사자체/null(미입력) 중 선택.</font>");
												pkgModel.setPkg_detail_validation_success_yn("N");
											}
										}
										
										//공급사 검증결과는(OK/NOK/COK)
										if (j == 15) {
											tagGubun = "".equals(pkgDetailModel.getValidation_result_content()) ? "" : pkgDetailModel.getValidation_result_content() + "<br/>";
											self_validation = dataValue.toUpperCase().trim();
	
											if (!"OK".equals(self_validation) && !"NOK".equals(self_validation) && !"COK".equals(self_validation)) {
												pkgDetailModel.setValidation_result(isNotOk);
												pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "공급사 검증결과는 OK/NOK/COK 중 선택.</font>");
												pkgModel.setPkg_detail_validation_success_yn("N");
											} else {
												String ok = isNotOk.equals(pkgDetailModel.getValidation_result()) ? isNotOk : isOk;
												pkgDetailModel.setValidation_result(ok);
											}
										}
										
										//검증내역 개수 체크(숫자만 입력)
										if (j == 19) {
											tagGubun = "".equals(pkgDetailModel.getValidation_result_content()) ? "" : pkgDetailModel.getValidation_result_content() + "<br/>";
											verification_sector = dataValue.toUpperCase().trim();
												
											if (!isStringDouble(verification_sector)) {
												pkgDetailModel.setValidation_result(isNotOk);
												pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "검증내역 개수는 숫자(정수) / NULL(미입력) 중 선택.</font>");
												pkgModel.setPkg_detail_validation_success_yn("N");
											} else {
												String ok = isNotOk.equals(pkgDetailModel.getValidation_result()) ? isNotOk : isOk;
												pkgDetailModel.setValidation_result(ok);
											}
										}
										
										//개선내역 개수 체크(숫자만 입력)
										if (j == 20) {
											tagGubun = "".equals(pkgDetailModel.getValidation_result_content()) ? "" : pkgDetailModel.getValidation_result_content() + "<br/>";
											verification_sector = dataValue.toUpperCase().trim();
												
											if (!isStringDouble(verification_sector)) {
												pkgDetailModel.setValidation_result(isNotOk);
												pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "개선내역 개수는 숫자(정수) / NULL(미입력) 중 선택.</font>");
												pkgModel.setPkg_detail_validation_success_yn("N");
											} else {
												String ok = isNotOk.equals(pkgDetailModel.getValidation_result()) ? isNotOk : isOk;
												pkgDetailModel.setValidation_result(ok);
											}
										}
										
										pkgDetailVariableModel.setContent(dataValue);
									}
								}
							} else {//값이 없는 경우
								//중요도
								if(j == 2){
									tagGubun = "".equals(pkgDetailModel.getValidation_result_content()) ? "" : pkgDetailModel.getValidation_result_content() + "<br/>";
									if (dataValue == null) {
										pkgDetailModel.setValidation_result(isNotOk);
										pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "중요도는 필수항목 입니다.</font>");
										pkgModel.setPkg_detail_validation_success_yn("N");
									}
								}
								
								//상용 검증 결과
								if(j == 4){
									String ok = isNotOk.equals(pkgDetailModel.getValidation_result()) ? isNotOk : isOk;
									pkgDetailModel.setValidation_result(ok);
								}
								
								//요구 분류 체크
								if(j == 6){
									String ok = isNotOk.equals(pkgDetailModel.getValidation_result()) ? isNotOk : isOk;
									pkgDetailModel.setValidation_result(ok);
								}
								
								//검증분야 개수 체크
//								if(j == 15){
//									pkgDetailModel.setValidation_result(isNotOk);
//									pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "검증분야 개수는 필수항목 입니다.</font>");
//									pkgModel.setPkg_detail_validation_success_yn("N");
//								}
								
								//공급사 검증결과는
								if(j == 15){
									pkgDetailModel.setValidation_result(isNotOk);
									pkgDetailModel.setValidation_result_content("<font color='red'>" + tagGubun + "공급사 검증결과는 필수항목 입니다.</font>");
									pkgModel.setPkg_detail_validation_success_yn("N");
								}
								
								pkgDetailVariableModel.setContent("");
							}
							pkgDetailVariableModelListSub.add(pkgDetailVariableIndex++, pkgDetailVariableModel);
						}
					}
					pkgDetailModelList.add((i - 1), pkgDetailModel);
					pkgDetailVariableModelList.add((i - 1), pkgDetailVariableModelListSub);
				}
			}

			ws = null;
			wb = null;
			
			// 보완적용내역 유효성 검사 결과 Set
			pkgModel.setPkgDetailModelList(pkgDetailModelList);
			pkgModel.setPkgDetailVariableModelList(pkgDetailVariableModelList);

			// 현재 버전 적용
			pkgModel.setTpl_seq(tempmgList.get(0).getTpl_seq());
			pkgModel.setTpl_ver(tempmgList.get(0).getTpl_ver());
			
			// Sesseion Service Set
			sessionService.create("PKG_SUPPLEMENT_PkgModel", pkgModel);
		}
	}
	
	private String getRowData(Cell cell) throws Exception {
		String retStr = null;
		if (cell == null) {
			retStr = null;
		} else {
			if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				retStr = Double.toString(cell.getNumericCellValue());
			} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				retStr = cell.getStringCellValue();
			}
		}
		return retStr;
	}

	@Override
	public void excelUpload(PkgModel pkgModel) throws Exception {
		// 첨부파일 생성
		if (pkgModel.getFile_excel_upload() != null) {
			fileManageService.create(pkgModel, "PKG_");
			
			// 보완적용내역 업로드 및 유효성 검사
			this.excelValidationRead(pkgModel);
		}
	}

	@Override
	public String templateDownload(PkgModel pkgModel) throws Exception {
		TempmgModel tempmgModel = new TempmgModel();
		tempmgModel.setUse_yn("Y");
//		return tempmgColumnService.excelDownload(tempmgModel);
		return tempmgColumnService.newExcelDownload(tempmgModel);
	}

	@Override
	public String excelDownload(PkgModel pkgModel) throws Exception {
		// Template List Get
		TempmgModel tempmgModel = new TempmgModel();
		tempmgModel.setTpl_seq(pkgModel.getTpl_seq());
		
		List<TempmgModel> tempmgList = (List<TempmgModel>) tempmgColumnService.readList(tempmgModel);

		List<String> headerList = new ArrayList<String>();
		
		for(TempmgModel headerModel : tempmgList) {
			headerList.add(headerModel.getTitle());
		}
		
		List<List<String>> excelDataList = read4excelDownload(pkgModel);
		
		//파일 생성 후 다운로드할 파일명
		String downloadFileName = ExcelUtil.write("PKMS_보완적용내역", propertyService.getString("Globals.fileStorePath"), headerList, excelDataList);
		
		return downloadFileName;
	}

	/**
	 * 다운로드 용 데이터로 변환
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	private List<List<String>> read4excelDownload(PkgModel pkgModel) throws Exception {
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> colList;
		
		// 보완적용내역 조회-detail 내용 조회
		List<PkgDetailModel> pkgDetailModelList = pkgDetailService.readList(pkgModel);
		
		// 보완적용내역 조회-detail_variable 내용 조회
		List<PkgDetailVariableModel> pkgDetailVariableModelList = null;

		for(PkgDetailModel pkgDetailModel : pkgDetailModelList) {
			colList = new ArrayList<String>();
			
			colList.add(String.valueOf(pkgDetailModel.getNo()));
			colList.add(pkgDetailModel.getNew_pn_cr_gubun());
			
			// pkg_detail_seq 값에 해당하는 pkg_detail_variable 목록 조회
			pkgDetailVariableModelList = pkgDetailService.variable_readList(pkgDetailModel);
			
			for(PkgDetailVariableModel pkgDetailVariableModel : pkgDetailVariableModelList) {
				colList.add(pkgDetailVariableModel.getContent());
			}
			rowList.add(colList);
		}

		return rowList;
	}

	/**
	 * 보완적용내역 등록
	 * @param pkgModel
	 * @param pkgModelData
	 * @throws Exception
	 */
	private void pkg_detail_variable(PkgModel pkgModel, PkgModel pkgModelData) throws Exception {
		for (int i = 0; i < pkgModelData.getPkgDetailVariableModelList().size(); i++) {
			PkgDetailModel pkgDetailModel = pkgModelData.getPkgDetailModelList().get(i);
			List<PkgDetailVariableModel> pkgDetailVariableModelList = pkgModelData.getPkgDetailVariableModelList().get(i);

			// pkg_detail set
			pkgDetailModel.setPkg_seq(pkgModel.getPkg_seq());
			pkgDetailModel.setSession_user_id(pkgModel.getSession_user_id());

			// pkg_detail Create
			String pkg_detail_seq = pkgDetailService.create(pkgDetailModel);

			// pkg_detail_valiable info
			for (int j = 0; j < pkgDetailVariableModelList.size(); j++) {
				PkgDetailVariableModel pkgDetailVariableModel = pkgDetailVariableModelList.get(j);

				// pkg_detail_valiable set
				pkgDetailVariableModel.setPkg_detail_seq(pkg_detail_seq);
				pkgDetailVariableModel.setOrd(String.valueOf(j));
				pkgDetailVariableModel.setSession_user_id(pkgModel.getSession_user_id());

				// pkg_detail_valiable Create
				pkgDetailVariableService.create(pkgDetailVariableModel);

			}
		}
	}
	public static boolean isStringDouble(String s) {
	    try {
	        Double.parseDouble(s);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	  }
}
