package com.pkms.pkgmg.pkg.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

//import com.pkms.pkgmg.pkg.dao.PkgDetailDAO;
import com.pkms.pkgmg.pkg.dao.PkgDetailVariablDAO;
import com.pkms.pkgmg.pkg.model.PkgDetailModel;
import com.pkms.pkgmg.pkg.model.PkgDetailVariableModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.tempmg.model.TempmgModel;
import com.pkms.tempmg.service.TempmgColumnServiceIf;

/**
 * PKG 보완적용내역 관련 Service<br/>
 * - 가변필드 포함
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Service("PkgDetailVariableService")
public class PkgDetailVariableService implements PkgDetailVariableServiceIf {

	@Resource(name = "TempmgColumnService")
	private TempmgColumnServiceIf tempmgColumnService;

	@Resource(name = "PkgDetailService")
	private PkgDetailServiceIf pkgDetailService;
	
	@Resource(name = "PkgDetailVariablDAO")
	private PkgDetailVariablDAO pkgDetailVariablDAO;
	
/*	@Resource(name = "PkgDetailDAO")
	private PkgDetailDAO pkgDetailDAO;*/
	
	
	@Override
	public void create(PkgDetailVariableModel model) throws Exception {
		pkgDetailVariablDAO.create(model);
	}
	
	@Override
	public PkgModel read(PkgModel pkgModel) throws Exception {
		//템플릿 세팅
		this.setTemplate(pkgModel);
		
		// 보완적용내역 조회-detail 내용 조회
		List<PkgDetailModel> pkgDetailModelList = pkgDetailService.readList(pkgModel);
		
		// detail 내용 set
		pkgModel.setPkgDetailModelList(pkgDetailModelList);
		pkgModel.setPkg_detail_count(String.valueOf(pkgDetailModelList.size()));

		// 보완적용내역 조회-detail_variable 내용 조회
		List<List<PkgDetailVariableModel>> pkgDetailVariableModelList = new ArrayList<List<PkgDetailVariableModel>>();

		for (PkgDetailModel pkgDetailModel : pkgDetailModelList) {
			// pkg_detail_seq 값에 해당하는 pkg_detail_variable 목록 조회
			List<PkgDetailVariableModel> pkgDetailVariableModelListSub = pkgDetailService.variable_readList(pkgDetailModel);
			int aa=0;
			String replace_text="";
			for(PkgDetailVariableModel ppp : pkgDetailVariableModelListSub){
				if(pkgDetailVariableModelListSub.get(aa).getContent() == null){
					
				}else{										
					replace_text = ppp.getContent().replaceAll("<", "＜").replaceAll(">", "＞");
					ppp.setContent(replace_text);
					pkgDetailVariableModelListSub.set(aa, ppp);
				}
				aa++;
			}
			// detail_variable 내용 add
			pkgDetailVariableModelList.add(pkgDetailVariableModelListSub);
		}

		// detail_variable set
		pkgModel.setPkgDetailVariableModelList(pkgDetailVariableModelList);

		
		return pkgModel;
	}

	@Override
	public void update(PkgModel pkgModel) throws Exception {
		//보완적용내역 신규/보완/개선 구분값 수정 추가 1002 - ksy
		PkgDetailModel pkgDetailModel = new PkgDetailModel();
		pkgDetailModel.setPkg_seq(pkgModel.getPkg_seq());
		pkgDetailModel.setPkg_detail_seq(pkgModel.getPkg_detail_seq());
		pkgDetailModel.setNew_pn_cr_gubun(pkgModel.getNew_pn_cr_gubun());
				
		if(!pkgDetailModel.getNew_pn_cr_gubun().equals("신규") && !pkgDetailModel.getNew_pn_cr_gubun().equals("보완") && !pkgDetailModel.getNew_pn_cr_gubun().equals("개선")){
			throw new Exception("error.biz.분류는 신규/보완/개선 중 하나를 입력해야 합니다.");
		}
		pkgDetailService.updateNPC_gubun(pkgDetailModel);
		//
				
		List<PkgDetailVariableModel> modelList = new ArrayList<PkgDetailVariableModel>();
		PkgDetailVariableModel pkgDetailVariableModel;
		
		String pkg_detail_seq = pkgModel.getPkg_detail_seq();
		String[] detail_variable_contents = pkgModel.getDetail_variable_content();
		
		int size = detail_variable_contents.length;
		
		for(int i = 0; i < size; i++) {
			pkgDetailVariableModel = new PkgDetailVariableModel();
			
			if(i == 0){ //중요도(CRI/MAJ/MIN)
				if(!"CRI".equals(detail_variable_contents[0]) && !"MAJ".equals(detail_variable_contents[0]) && !"MIN".equals(detail_variable_contents[0])){
					throw new Exception("error.biz.중요도는 CRI/MAJ/MIN 중 하나를 입력해야 합니다.");
				}
			}else if(i == 2){ //상용검증결과(OK/NOK/COK/null)
				if(!"OK".equals(detail_variable_contents[2]) && !"NOK".equals(detail_variable_contents[2]) && !"COK".equals(detail_variable_contents[2]) && !"".equals(detail_variable_contents[2])){
					throw new Exception("error.biz.상용검증결과는 OK/NOK/COK/null(미입력) 중 하나를 입력해야 합니다.");
				}
			}else if(i == 4){ //요구분류(운용/기술원/공급사자체/null)
				if(!"운용".equals(detail_variable_contents[4]) && !"기술원".equals(detail_variable_contents[4]) && !"공급사자체".equals(detail_variable_contents[4]) && !"".equals(detail_variable_contents[4])){
					throw new Exception("error.biz.요구분류는 운용/기술원/공급사자체/null(미입력) 중 하나를 입력해야 합니다.");
				}
			}else if(i == 13){ //자체검증결과(OK/NOK/COK)
				if(!"OK".equals(detail_variable_contents[13]) && !"NOK".equals(detail_variable_contents[13]) && !"COK".equals(detail_variable_contents[13])){
					throw new Exception("error.biz.자체검증결과는 OK/NOK/COK 중 하나를 입력해야 합니다.");
				}
			}else if(i == 17){ //검증내역 개수
				if(!isStringDouble(detail_variable_contents[17]) && !"".equals(detail_variable_contents[17])){
					throw new Exception("error.biz.검증내역 개수는 숫자(정수) / NULL(미입력) 중 선택해야 합니다.");
				}
			}else if(i == 18){ //개선내역 개수
				if(!isStringDouble(detail_variable_contents[18]) && !"".equals(detail_variable_contents[18])){
					throw new Exception("error.biz.개선내역 개수는 숫자(정수) / NULL(미입력) 중 선택해야 합니다.");
				}
			}else if(i == 19){ //개발검증결과(OK/NOK/COK/null)
				if(!"OK".equals(detail_variable_contents[19]) && !"NOK".equals(detail_variable_contents[19]) && !"COK".equals(detail_variable_contents[19]) && !"".equals(detail_variable_contents[19])&& !"BYPASS".equals(detail_variable_contents[19])){
					throw new Exception("error.biz.개발검증결과는 OK/NOK/COK/BYPASS/null(미입력) 중 하나를 입력해야 합니다.");
				}
			}

			// pkg_detail_valiable set
			pkgDetailVariableModel.setPkg_detail_seq(pkg_detail_seq);
			pkgDetailVariableModel.setOrd(String.valueOf(i + 0));
			pkgDetailVariableModel.setSession_user_id(pkgModel.getSession_user_id());
			pkgDetailVariableModel.setContent(detail_variable_contents[i]);
			modelList.add(pkgDetailVariableModel);
		}
		
		pkgDetailVariablDAO.delete(pkgModel);
		pkgDetailVariablDAO.update(modelList);
		
	}
	
	@Override
	public void insert(PkgModel pkgModel) throws Exception {
		
		PkgDetailVariableModel pkgDetailVariableModel = null;
		
		
		String[] detail_variable_contents2 = pkgModel.getDetail_variable_content2();
		String new_pn_cr_gubun = detail_variable_contents2[0];

		int detailMaxSeq = pkgDetailVariablDAO.detailSeq();
		int detailMaxNO = pkgDetailVariablDAO.detailNO(pkgModel.getPkg_seq());
		detailMaxSeq = detailMaxSeq + 1;
		detailMaxNO = detailMaxNO + 1;
		
		for (int i = 0; i < detail_variable_contents2.length-1; i++) {
			pkgDetailVariableModel = new PkgDetailVariableModel();
	
			if(i == 1){ //중요도(CRI/MAJ/MIN)
				if(!"CRI".equals(detail_variable_contents2[1]) && !"MAJ".equals(detail_variable_contents2[1]) && !"MIN".equals(detail_variable_contents2[1])){
					throw new Exception("error.biz.중요도는 CRI/MAJ/MIN 중 하나를 입력해야 합니다.");
				}
			}else if(i == 0){
				if(!"OK".equals(detail_variable_contents2[0]) && !"개선".equals(detail_variable_contents2[0]) && !"보완".equals(detail_variable_contents2[0]) && !"신규".equals(detail_variable_contents2[0])){
					throw new Exception("error.biz.분류는 개선/보완/신규 중 하나를 입력해야 합니다.");
				}
			}else if(i == 3){ //상용검증결과(OK/NOK/COK/null)
				if(!"OK".equals(detail_variable_contents2[3]) && !"NOK".equals(detail_variable_contents2[3]) && !"COK".equals(detail_variable_contents2[3]) && !"".equals(detail_variable_contents2[3])){
					throw new Exception("error.biz.상용검증결과는 OK/NOK/COK/null(미입력) 중 하나를 입력해야 합니다.");
				}
			}else if(i == 5){ //요구분류(운용/기술원/공급사자체/null)
				if(!"운용".equals(detail_variable_contents2[5]) && !"기술원".equals(detail_variable_contents2[5]) && !"공급사자체".equals(detail_variable_contents2[5]) && !"".equals(detail_variable_contents2[5])){
					throw new Exception("error.biz.요구분류는 운용/기술원/공급사자체/null(미입력) 중 하나를 입력해야 합니다.");
				}
			}else if(i == 14){ //자체검증결과(OK/NOK/COK)
				if(!"OK".equals(detail_variable_contents2[14]) && !"NOK".equals(detail_variable_contents2[14]) && !"COK".equals(detail_variable_contents2[14])){
					throw new Exception("error.biz.자체검증결과는 OK/NOK/COK 중 하나를 입력해야 합니다.");
				}
			}else if(i == 18){ //검증내역 개수
				if(!isStringDouble(detail_variable_contents2[18]) && !"".equals(detail_variable_contents2[18])){
					throw new Exception("error.biz.검증내역 개수는 숫자(정수) / NULL(미입력) 중 선택해야 합니다.");
				}
			}else if(i == 19){ //개선내역 개수
				if(!isStringDouble(detail_variable_contents2[19]) && !"".equals(detail_variable_contents2[19])){
					throw new Exception("error.biz.개선내역 개수는 숫자(정수) / NULL(미입력) 중 선택해야 합니다.");
				}
			}else if(i == 20){ //개발검증결과(OK/NOK/COK/null)
				if(!"OK".equals(detail_variable_contents2[20]) && !"NOK".equals(detail_variable_contents2[20]) && !"COK".equals(detail_variable_contents2[20]) && !"".equals(detail_variable_contents2[20])){
					throw new Exception("error.biz.상용검증결과는 OK/NOK/COK/null(미입력) 중 하나를 입력해야 합니다.");
				}
			}
			
			pkgDetailVariableModel.setDetailMaxSeq(detailMaxSeq);
			pkgDetailVariableModel.setOrd(String.valueOf(i + 0));
			pkgDetailVariableModel.setSession_user_id(pkgModel.getSession_user_id());
			pkgDetailVariableModel.setContent(detail_variable_contents2[i+1]);
			pkgDetailVariablDAO.add1(pkgDetailVariableModel);
			
	}
		pkgDetailVariableModel.setPkg_seq(pkgModel.getPkg_seq());
		pkgDetailVariableModel.setDetailMaxNO(detailMaxNO);
		pkgDetailVariableModel.setNew_pn_cr_gubun(new_pn_cr_gubun);
		pkgDetailVariablDAO.add2(pkgDetailVariableModel);

		
		
	}

	@Override
	public void delete(PkgModel pkgModel) throws Exception {
		// 보완적용내역 삭제(삭제 순서 변경불가!!)
		pkgDetailVariablDAO.deleteAll(pkgModel);
		pkgDetailService.delete(pkgModel);
	}
	
	@Override
	public void okNokUpdate(PkgModel pkgModel) throws Exception {
		
		String ok = pkgModel.getOk();
		String nok = pkgModel.getNok();
		String cok = pkgModel.getCok();
		
		List<String> okArray = new ArrayList<String>();
		List<String> nokArray = new ArrayList<String>();
		List<String> cokArray = new ArrayList<String>();
		

		String okList[] = ok.split(",");
		String nokList[] = nok.split(",");
		String cokList[] = cok.split(",");
		
		
		for(int i=0;i<okList.length;i++){
			okArray.add(okList[i]);
		}
		for(int i=0;i<nokList.length;i++){
			nokArray.add(nokList[i]);
		}
		for(int i=0;i<cokList.length;i++){
			cokArray.add(cokList[i]);
		}
		
		if(okArray.size() != 0){
			pkgDetailVariablDAO.okUpdate(okArray);
		}
		if(nokArray.size() != 0){
			pkgDetailVariablDAO.nokUpdate(nokArray);			
		}
		if(cokArray.size() != 0){
			pkgDetailVariablDAO.cokUpdate(cokArray);			
		}
		
		String ok_dev = pkgModel.getOk_dev();
		String nok_dev = pkgModel.getNok_dev();
		String cok_dev = pkgModel.getCok_dev();
		String bypass_dev = pkgModel.getBypass_dev();
		
		List<String> okArray_dev = new ArrayList<String>();
		List<String> nokArray_dev = new ArrayList<String>();
		List<String> cokArray_dev = new ArrayList<String>();
		List<String> bypassArray_dev = new ArrayList<String>();
		

		String okList_dev[] = ok_dev.split(",");
		String nokList_dev[] = nok_dev.split(",");
		String cokList_dev[] = cok_dev.split(",");
		String bypassList_dev[] = bypass_dev.split(",");
		
		
		for(int i=0;i<okList_dev.length;i++){
			okArray_dev.add(okList_dev[i]);
		}
		for(int i=0;i<nokList_dev.length;i++){
			nokArray_dev.add(nokList_dev[i]);
		}
		for(int i=0;i<cokList_dev.length;i++){
			cokArray_dev.add(cokList_dev[i]);
		}
		for(int i=0;i<bypassList_dev.length;i++){
			bypassArray_dev.add(bypassList_dev[i]);
		}
		
		if(okArray_dev.size() != 0){
			pkgDetailVariablDAO.okUpdate_dev(okArray_dev);
		}
		if(nokArray_dev.size() != 0){
			pkgDetailVariablDAO.nokUpdate_dev(nokArray_dev);			
		}
		if(cokArray_dev.size() != 0){
			pkgDetailVariablDAO.cokUpdate_dev(cokArray_dev);			
		}
		if(cokArray_dev.size() != 0){
			pkgDetailVariablDAO.bypassUpdate_dev(bypassArray_dev);			
		}
	}
	
	/**
	 * 템플릿 세팅
	 * @param pkgModel
	 * @throws Exception
	 */
	private void setTemplate(PkgModel pkgModel) throws Exception {
		List<TempmgModel> tempmgModelList = null;
		List<TempmgModel> useYTempmgModelList = null;
		
		// 적용된 보완적용내역 조회-template 가변 제목 조회
		TempmgModel tempmgModel = new TempmgModel();
		tempmgModel.setPosition("");
		
		//신규
		if(pkgModel.getTpl_seq() == null || "".equals(pkgModel.getTpl_seq())) {
			tempmgModelList = new ArrayList<TempmgModel>();
			
		//기존에 등록한 데이터가 있는 경우
		} else {
			tempmgModel.setTpl_seq(pkgModel.getTpl_seq());
			tempmgModelList = tempmgColumnService.readList(tempmgModel);
			
			// 적용 template 버전
			for(TempmgModel temp : tempmgModelList) {
				pkgModel.setTpl_ver(temp.getTpl_ver());
				break;
			}
		}

		// template 가변 제목 set
		pkgModel.setTempmgModelList(tempmgModelList);
		pkgModel.setTempmgModelListSize(tempmgModelList.size());

		// 현재 사용 중인 template 버전
		tempmgModel.setTpl_seq("");
		tempmgModel.setUse_yn("Y");
		useYTempmgModelList = tempmgColumnService.readList(tempmgModel);
		
		for(TempmgModel temp : useYTempmgModelList) {
			pkgModel.setUseY_tpl_ver(temp.getTpl_ver());
			break;
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
