package com.pkms.sys.group3.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.sys.common.model.SysModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * 
 * 시스템 소분류 정보를 위한 DAO<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Repository("Group3DAO")
public class Group3DAO extends IbatisAbstractDAO {

	/**
	 * 시스템 소분류 정보를 DB에 생성
	 * 
	 * @param SysModel
	 *            - 등록 정보 모델
	 * @return String - 등록 결과
	 * @throws Exception
	 */
	public String create(SysModel model) throws Exception {
		return (String) create("Group3DAO.create", model);
	}

	/**
	 * 시스템 소분류 정보를 DB에 수정
	 * 
	 * @param SysModel
	 *            - 수정 정보 모델
	 * @exception Exception
	 */
	public void update(SysModel model) throws Exception {
		update("Group3DAO.update", model);
	}

	/**
	 * 시스템 소분류 정보를 DB에서 삭제
	 * 
	 * @param SysModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete(SysModel model) throws Exception {
		delete("Group3DAO.delete", model);
	}

	/**
	 * 시스템 소분류 정보 정보를 DB에서 조회
	 * 
	 * @param SysModel
	 *            - 조회 대상 모델
	 * @return SysModel - 조회 결과 모델
	 * @throws Exception
	 */
	public SysModel read(SysModel model) throws Exception {
		return (SysModel) read("Group3DAO.read", model);
	}

	/**
	 * 시스템 소분류 정보 정보 목록을 DB에서 조회
	 * 
	 * @param SysModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<SysModel> readList(SysModel model) throws Exception {
		return (List<SysModel>) readList("Group3DAO.readList", model);
	}

	public List<SysModel> readList4User(SysModel model) throws Exception {
		return (List<SysModel>) readList("Group3DAO.readList4User", model);
	}
	
	public String readNextSeq() {
		return (String) getSqlMapClientTemplate().queryForObject("Group3DAO.NextSeq");
	}
}
