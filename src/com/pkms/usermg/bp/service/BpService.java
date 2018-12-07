package com.pkms.usermg.bp.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pkms.common.mail.model.MailModel;
import com.pkms.common.mail.service.MailServiceIf;
import com.pkms.usermg.bp.dao.BpDAO;
import com.pkms.usermg.bp.model.BpModel;
import com.pkms.usermg.bp.model.BpModel.BP_APPROVAL_STATE;
import com.pkms.usermg.bp.model.BpModel.USE_YN;
import com.pkms.usermg.user.model.BpUserModel;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.BpUserServiceIf;
import com.pkms.usermg.user.service.SktUserServiceIf;

/**
 * 
 * 
 * 협력업체 정보를 관리하는 서비스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 16.
 * 
 */
@Service("BpService")
public class BpService implements BpServiceIf {

	@Resource(name = "BpDAO")
	private BpDAO bpDAO;

	@Resource(name = "BpUserService")
	private BpUserServiceIf bpUserService;

	@Resource(name = "MailService")
	private MailServiceIf maService;

	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;

	@Override
	public void create(BpModel bpModel) throws Exception {
		/*
		 * 생성 시 권한에 따른 approval_state의 값에 대한 정의는 요청되는 JSP에서 지정 되어 넘어 옴.
		 */
		bpDAO.create(bpModel);
	}

	@Override
	public BpModel read(BpModel bpModel) throws Exception {

		BpModel rModel = bpDAO.read(bpModel);

		if (rModel == null) {
			rModel = bpModel;
		}

		return rModel;
	}

	@Override
	public int readCount(BpModel model) throws Exception {
		return bpDAO.readCount(model);
	}

	@Override
	public List<?> readList(BpModel bpModel) throws Exception {

		List<?> resultList = bpDAO.readList(bpModel);
		int totalCount = bpDAO.readTotalCount(bpModel);
		bpModel.setTotalCount(totalCount);

		return resultList;
	}

	@Override
	public List<?> readListAll(BpModel bpModel) throws Exception {

		List<?> resultList = bpDAO.readListAll(bpModel);
		bpModel.setTotalCount(resultList.size());

		return resultList;
	}

	@Override
	public void update(BpModel bpModel) throws Exception {

		/*
		 * SQL에서 Approval_state의 값 유무에 따라 Approval_state 값만 업데이트 하거나 다른
		 * 필드에 대한 값을 업데이트 한다.
		 */

		// 승인 관련 업데이트 시
		if (StringUtils.hasLength(bpModel.getApproval_state())) {
			BpUserModel bpUserModel = bpModel.createBpUserModel();

			// 사용중인 BP User 조회
			bpUserModel.setUse_yn(USE_YN.YES.getCode());
			List<BpUserModel> bpUserList = bpUserService.readList(bpUserModel);
			for (BpUserModel updateBpUserModel : bpUserList) {
				updateBpUserModel.setApproval_state(bpModel.getApproval_state());
				bpUserService.update(updateBpUserModel);
			}

			// 승인 요청시 승인 담당자에게 이메일 전송
			if (BP_APPROVAL_STATE.REQUEST.getCode().equals(bpModel.getApproval_state())) {
				sendMailContet(false, bpModel, bpUserList);

				// 승인 완료시 업체 담당자에게 이메일 발송
			} else if (BP_APPROVAL_STATE.COMPLETE.getCode().equals(bpModel.getApproval_state())) {
				sendMailContet(true, bpModel, bpUserList);

			}

		} else { // 승인 관련이 아닌 값에 대한 업데이트 시
			BpModel currentModel = bpDAO.read(bpModel);

			// 업데이트 값 중 사용여부가 변경 되는 경우에는 해당 업체의 사용자들에 대한 사용 여부도 변경
			if (!currentModel.getUse_yn().equals(bpModel.getUse_yn())) {

				ArrayList<Object> objects = new ArrayList<Object>();
				BpUserModel bpUserModel = bpModel.createBpUserModel();

				// 사용중인 BP User 조회
				bpUserModel.setUse_yn(USE_YN.YES.getCode());
				for (Object object : bpUserService.readList(bpUserModel)) {
					objects.add(object);
				}

				// 미 사용중인 BP User 조회
				bpUserModel.setUse_yn(USE_YN.NO.getCode());
				for (Object object : bpUserService.readList(bpUserModel)) {
					objects.add(object);
				}

				// 전체 BP User에 대한 사용여부 값을 업체의 사용여부 변경되는 값으로 업데이트
				for (Object object : objects) {
					if (object instanceof BpUserModel) {
						BpUserModel updateBpUserModel = (BpUserModel) object;
						updateBpUserModel.setUse_yn(bpModel.getUse_yn());
						bpUserService.updateYN(updateBpUserModel);
					}
				}
			}

		}
		bpDAO.update(bpModel);
	}

	private void sendMailContet(boolean bp, BpModel bpModel, List<BpUserModel> bpUserList) throws Exception {

		MailModel mailModel = new MailModel();
		if (bp) {
			ArrayList<String> emailList = new ArrayList<String>();
			for (BpUserModel bpUserModel : bpUserList) {
				emailList.add(bpUserModel.getBp_user_email());
			}
			mailModel.setTos(emailList.toArray(new String[0]));
			mailModel.setTosInfo(emailList.toArray(new String[0]));
			
			String mailFrom = null;
			mailFrom = bpModel.getSession_user_email();
			mailModel.setFrom(mailFrom);
		} else {
			SktUserModel sktUserModel = new SktUserModel();
			sktUserModel.setEmpno(bpModel.getApproval_user_id());
			sktUserModel = sktUserService.read(sktUserModel);
			mailModel.setTo(sktUserModel.getEmail());
			mailModel.setToInfo(sktUserModel.getEmail());
			mailModel.setFrom(sktUserModel.getEmail());
		}

		if (bp) {
			mailModel.setMsgSubj("[PKMS] 협력업체/담당자 승인 완료");
		} else {
			mailModel.setMsgSubj("[PKMS] 협력업체/담당자 승인 요청");
		}
		StringBuffer sb = new StringBuffer();
		if (bp) {
			sb.append("    -협력업체/담당자 승인 완료");
		} else {
			sb.append("    -협력업체/담당자 승인 요청");
		}
		sb.append("    업체명: " + bpModel.getBp_name()).append("<br />");
		sb.append("    사업자등록번호: " + bpModel.getBp_num()).append("<br />");
		sb.append("    전화번호: " + bpModel.getBp_tel1() + "-" + bpModel.getBp_tel2() + "-" + bpModel.getBp_tel3()).append("<br />");
		sb.append("    담당자: ").append("<br />");
		for (BpUserModel bpUserModel : bpUserList) {
			sb.append("      [이름: " + bpUserModel.getBp_user_name() + ", 아이디: " + bpUserModel.getBp_user_id() + ", 전화번호: " + bpUserModel.getBp_user_phone1() + "-" + bpUserModel.getBp_user_phone2() + "-" + bpUserModel.getBp_user_phone3() + ", 이메일: " + bpUserModel.getBp_user_email()).append("<br />");
		}

		mailModel.setMsgText("<tr><td colspan='2'>"+ sb.toString()+"</td></tr>");
		if (bp) {
			maService.create4Multi(mailModel);
		} else {
			maService.create(mailModel);
		}

	}
	
	@Override
	public List<BpModel> readListSystem(BpModel bpModel) throws Exception {
		List<BpModel> resultList = null;
		List<BpModel> equipmentList = null;
		int totalCount = 0;
		
		if("bp".equals(bpModel.getCompanyCondition())){
			resultList = bpDAO.readListSystem(bpModel);
			totalCount = bpDAO.readListSystemCount(bpModel);
			bpModel.setTotalCount(totalCount);
		} else if("skt".equals(bpModel.getCompanyCondition())){
			if("1".equals(bpModel.getSearchCondition())){
				resultList = bpDAO.readListSystem_skt(bpModel);
				totalCount = bpDAO.readListSystemCount_skt(bpModel);
			}else{
				resultList = bpDAO.readListSystem_skt_sosok(bpModel);
				totalCount = bpDAO.readListSystemCount_skt_sosok(bpModel);
			}
		}else{
			if("1".equals(bpModel.getSearchCondition())){				
				resultList = bpDAO.readListSystem_operator(bpModel);
				totalCount = bpDAO.readListSystemCount_operator(bpModel);
			}else{
				resultList = bpDAO.readListSystem_operator_sosok(bpModel);
				totalCount = bpDAO.readListSystemCount_operator_sosok(bpModel);
			}
			int cnt=0;
			String equipment_name = "";
			for(BpModel system : resultList){
				system.setSearchCondition(bpModel.getSearchCondition());
				equipmentList = bpDAO.readList_equipment(system);
				int e_cnt=0;
				for(BpModel equipment : equipmentList){
					if(e_cnt == 0){
						equipment_name = equipment.getEquipment_name();
					}else if(e_cnt > 0){
						equipment_name += ", "+equipment.getEquipment_name();
					}
					e_cnt++;
				}
				system.setEquipment_name(equipment_name);
				resultList.set(cnt, system);
				cnt++;
			}
		}
		bpModel.setTotalCount(totalCount);
		return (List<BpModel>) resultList;
	}
	
	@Override
	public List<BpModel> findIdList(BpModel bpModel) throws Exception {
		return bpDAO.findIdList(bpModel);
	}
	
	@Override
	public void pass_update(BpModel bpModel) throws Exception {
		// SHA PW 인코딩
		if (StringUtils.hasLength(bpModel.getBp_user_pw())) {
			ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
			bpModel.setBp_user_pw(shaPasswordEncoder.encodePassword(bpModel.getBp_user_pw(), null));
		}
		
		List<BpModel> idList = bpDAO.findIdList(bpModel);
		
		int check = 0;
		if(idList != null){
			for(BpModel idModel : idList){
				if(bpModel.getBp_user_id().equals(idModel.getBp_user_id())){
					check++;
				}
			}
		}
		
		if(check == 0){
			throw new Exception("error.biz.정확하지 않은 입력 값이 있습니다.\n확인 바랍니다.");
		}
		
		bpDAO.pass_update(bpModel);
	}
}
