package com.pkms.pkgmg.pkg.service;

import java.util.List;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.sys.system.model.SystemUserModel;

/**
 * PKG Main Service<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
public interface PkgServiceIf {
	
	/**
	 * PKG 상세 조회
	 * 
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel read(PkgModel pkgModel) throws Exception;

	/**
	 * PKG master 조회
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel read4Master(PkgModel pkgModel) throws Exception;
	
	/**
	 * PKG 목록 조회
	 * 
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public List<PkgModel> readList(PkgModel pkgModel) throws Exception;

	/**
	 * PKG 이력 목록
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public List<PkgModel> readListHistory(PkgModel pkgModel) throws Exception;

	/**
	 * Pkg에 적용된 템플릿 개수 조회
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void readTotalCountTemplate(PkgModel pkgModel) throws Exception;

	/**
	 * 검증요청
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void update(PkgModel pkgModel) throws Exception;

	/**
	 * PKG현황 상태변경 Update
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void status_update(PkgModel pkgModel) throws Exception;
	
	/**
	 * PKG긴급등록 상태변경 Update
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void urgency_update(PkgModel pkgModel) throws Exception;
	
	/**
	 * 템플릿 seq 수정
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void tpl_seq_update(PkgModel model) throws Exception;
	
	/**
	 * 검증접수/완료 시 검증 정보 업데이트
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void verify_update(PkgModel pkgModel) throws Exception;
	
	/**
	 * PKG 삭제
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void delete(PkgModel pkgModel) throws Exception;

	/**
	 * 검색 조건 세팅
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel setSearchCondition(PkgModel pkgModel) throws Exception;
	
	/**
	 * 승인자 조회
	 * @param pkgModel
	 * @param isWithEquipmentUser
	 * @throws Exception
	 */
	public void readUser(PkgModel pkgModel, boolean isWithEquipmentUser, boolean isAll) throws Exception;
	
	/**
	 * 목록 엑셀다운로드
	 * 
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public String excelDownload(PkgModel pkgModel) throws Exception;
	
	/**
	 * 검증진도율
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel popupProgressRead(PkgModel pkgModel) throws Exception;
	
	public PkgModel popupProgressRead_Dev(PkgModel pkgModel) throws Exception;
	
	/**
	 * 프린트 할 내용
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public List<PkgModel> printRead_Time(PkgModel pkgModel) throws Exception;
	public List<PkgModel> printRead_EQ(PkgModel pkgModel) throws Exception;
	public List<PkgModel> printRead_PnCr(PkgModel pkgModel) throws Exception;
	
	/**
	 * 작업건수 관리
	 */
	public List<PkgModel> workCntList(PkgModel pkgModel) throws Exception;
	public List<PkgModel> workPkgList(PkgModel pkgModel) throws Exception;
	public List<PkgModel> workLimitList(PkgModel pkgModel) throws Exception;
	
	public List<PkgModel> helloList(PkgModel pkgModel) throws Exception;
	public List<PkgModel> helloList2(PkgModel pkgModel) throws Exception;
	
	public void workLimitSave(PkgModel pkgModel) throws Exception;
	public void sendSms(PkgModel pkgModel, String phone) throws Exception;
	public String [] cleanStr(String [] str) throws Exception;
}
