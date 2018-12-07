package com.pkms.batch.sktdb.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.batch.sktdb.model.SktDbScheduleModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("SktDbScheduleDao")
public class SktDbScheduleDao extends IbatisAbstractDAO {

	/**
	 * ONS 부서 데이터 정보 목록을 DB에서 조회
	 * 
	 * @param 
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readONSDeptList() throws Exception {
		return readList("SktDbScheduleDao.readONSDeptList", null);
	}
	
	/**
	 * ONS 인사 데이터 정보 목록을 DB에서 조회
	 * 
	 * @param 
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readONSPersonList() throws Exception {
		return readList("SktDbScheduleDao.readONSPersonList", null);
	}
	
	/**
	 * ONS 부서 데이터를 DB에 생성
	 * 
	 * @param DeptModel
	 *            - 등록 정보 모델
	 * @return 
	 * @throws Exception
	 */
	public void onsDeptCreate() throws Exception {
		create("SktDbScheduleDao.createONSDeptList", null);
	}
	
	/**
	 * ONS 부서 데이터를 DB에서 삭제
	 * 
	 * @param 
  	 * @return
	 * @throws Exception
	 */
	public void onsDeptDelete() throws Exception {
		delete("SktDbScheduleDao.deleteONSDeptList", null);
	}
	
	/**
	 * ONS 인사 데이터를 DB에 생성
	 * 
	 * @param PersonModel
	 *            - 등록 정보 모델
	 * @return 
	 * @throws Exception
	 */
	public void onsPersonCreate() throws Exception {
		create("SktDbScheduleDao.createONSPersonList", null);
	}
	
	/**
	 * ONS 인사 데이터를 DB에서 삭제
	 * 
	 * @param 
  	 * @return
	 * @throws Exception
	 */
	public void onsPersonDelete() throws Exception {
		delete("SktDbScheduleDao.deleteONSPersonList", null);
	}
	
	/**
	 * 인사 정보 변경 시 system 담당자에서 퇴사자 처리
	 * 
	 * @param 
  	 * @return
	 * @throws Exception
	 */
	public void deleteSYSUserList() throws Exception {
		delete("SktDbScheduleDao.deleteSYSUserList", null);
	}
	
	/**
	 * 인사 정보 변경 시 equipment 담당자에서 퇴사자 처리
	 * 
	 * @param 
  	 * @return
	 * @throws Exception
	 */
	public void deleteEQUserList() throws Exception {
		delete("SktDbScheduleDao.deleteEQUserList", null);
	}
	
	
}
