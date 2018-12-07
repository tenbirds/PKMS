package com.pkms.pkgmg.pkg.service;

import java.util.List;

import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;

/**
 * 초도/확대 장비 관련 Service
 * 
 * @author : 009
 * @Date : 2012. 5. 08.
 * 
 */
public interface PkgEquipmentServiceIf {

	/**
	 * 새로운 EqWorkSeq(작업시스템연동 key) 생성(sequence)
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String readEqWorkSeq(PkgEquipmentModel model) throws Exception;

	/**
	 * 초도/확대 적용 일정 등록
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void create(PkgEquipmentModel model) throws Exception;
	public void create21(PkgEquipmentModel model) throws Exception;
	public void update(PkgEquipmentModel model) throws Exception;
	public void s_work_update(PkgEquipmentModel model) throws Exception;
	/**
	 * 초도/확대 적용 일정 조회
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<PkgEquipmentModel> readList(PkgEquipmentModel model) throws Exception;
	public List<PkgEquipmentModel> read21List(PkgEquipmentModel model) throws Exception;

	/**
	 * 초도/확대 적용 일정 삭제
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void delete(PkgEquipmentModel model) throws Exception;
	
	public PkgEquipmentModel readName(PkgEquipmentModel model) throws Exception;
	public PkgEquipmentModel readOrd(PkgEquipmentModel model) throws Exception;
	public PkgEquipmentModel maxOrd(PkgEquipmentModel model) throws Exception;
	public void createAccess(PkgEquipmentModel model) throws Exception;
	public void updateAccess(PkgEquipmentModel model) throws Exception;
	public List<PkgEquipmentModel> readAccess(PkgEquipmentModel model) throws Exception;
	public void updateResultAccess(PkgEquipmentModel model) throws Exception;
	public void deleteAccess(PkgEquipmentModel model) throws Exception;
}
