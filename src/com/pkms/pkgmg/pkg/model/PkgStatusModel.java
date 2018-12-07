package com.pkms.pkgmg.pkg.model;

import java.util.List;

import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

/**
 * Pkg 검증요청 Status History<br>
 * 
 * @author : scott
 * @Date : 2012. 5. 08.
 * 
 */
public class PkgStatusModel extends AbstractModel {
	private static final long serialVersionUID = 1L;

	/**
	 * PKG seq
	 */
	private String pkg_seq = "";
	
	/**
	 * 상태
	 */
	private String status = "";

	/**
	 * 결과
	 */
	private final String[][] verify_result_3 = new String[][] { {"", "선택"}, {"양호", "양호"}, {"불량", "불량"} };
	
	/**
	 * 결과 배열
	 */
	private List<CodeModel> verify_result_3List;

	/**
	 * col1
	 */
	private String col1 = "";
	
	/**
	 * col2
	 */
	private String col2 = "";
	
	/**
	 * col3
	 */
	private String col3 = "";
	
	/**
	 * col4
	 */
	private String col4 = "";
	
	/**
	 * col5
	 */
	private String col5 = "";
	
	/**
	 * col6
	 */
	private String col6 = "";
	
	/**
	 * col7
	 */
	private String col7 = "";
	
	/**
	 * col8
	 */
	private String col8 = "";
	
	/**
	 * col9
	 */
	private String col9 = "";
	
	/**
	 * col10
	 */
	private String col10 = "";
	
	/**
	 * col11
	 */
	private String col11 = "";
	
	/**
	 * col12
	 */
	private String col12 = "";
	
	/**
	 * col13
	 */
	private String col13 = "";
	
	/**
	 * col14
	 */
	private String col14 = "";
	
	/**
	 * col15
	 */
	private String col15 = "";
	
	/**
	 * col16
	 */
	private String col16 = "";
	
	/**
	 * col17
	 */
	private String col17 = "";
	
	/**
	 * col18
	 */
	private String col18 = "";
	
	/**
	 * col19
	 */
	private String col19 = "";
	
	/**
	 * col20
	 */
	private String col20 = "";
	
	/**
	 * col21
	 */
	private String col21 = "";
	
	/**
	 * col22
	 */
	private String col22 = "";
	
	/**
	 * col23
	 */
	private String col23 = "";
	
	/**
	 * col24
	 */
	private String col24 = "";
	
	/**
	 * col25
	 */
	private String col25 = "";
	
	/**
	 * col26
	 */
	private String col26 = "";
	
	//개발 검증 추가 core lab
	private String col27 = "";
	private String col28 = "";
	private String col29 = "";
	private String col30 = "";
	private String col31 = "";
	private String col32 = "";
	
	private String col33 = "";
	private String col34 = "";
	private String col35 = "";
	private String col36 = "";
	private String col37 = "";
	private String col38 = "";
	
	//추가 상용화검증
	private String col39 = "";
	private String col40 = "";
	
	private String col41 = "";
	private String col42 = "";
	
	//검증4타입 
	private String col43 = "";
	private String verify_start_date = "";
	private String verify_end_date = "";
	
	//update 구분(flow 단계 넘기지 않기 위한 key)
	private String update_gubun = "N";
	
	public String getCol42() {
		return col42;
	}

	public void setCol42(String col42) {
		this.col42 = col42;
	}

	public String getCol41() {
		return col41;
	}

	public void setCol41(String col41) {
		this.col41 = col41;
	}

	public String getCol39() {
		return col39;
	}

	public void setCol39(String col39) {
		this.col39 = col39;
	}

	
	public String getCol40() {
		return col40;
	}

	public void setCol40(String col40) {
		this.col40 = col40;
	}


	/**
	 * 사용여부
	 */
	private String use_yn = "";
	
	/**
	 * 등록자
	 */
	private String reg_user = "";
	
	/**
	 * 등록일
	 */
	private String reg_date = "";
	
	/**
	 * 수정자
	 */
	private String update_user = "";
	
	/**
	 * 수정일
	 */
	private String update_date = "";

	/**
	 * 등록자 명
	 */
	private String reg_user_name = "";
	
	/**
	 * 수정자 명
	 */
	private String update_user_name = "";
	
	/**
	 * 확대 일정수립_주간_사전작업
	 */
	private String prev_content = "";
	
	/**
	 * 결과
	 */
	private final String[][] work_result_3 = new String[][] { {"", "선택"}, {"Y", "양호"}, {"N", "불량"},{"S","보류"}};
	
	/**
	 * 결과 배열
	 */
	
	//부서 변수 추가 0422 강수연
	private String sosok;
	private String update_sosok;
	
	public String getUpdate_sosok() {
		return update_sosok;
	}

	public void setUpdate_sosok(String update_sosok) {
		this.update_sosok = update_sosok;
	}

	public String getSosok() {
		return sosok;
	}

	public void setSosok(String sosok) {
		this.sosok = sosok;
	}

	private List<CodeModel> work_result_3List;
	
	public PkgStatusModel() {
		setVerify_result_3List();
		setWork_result_3List();
	}
	
	public String getPkg_seq() {
		return pkg_seq;
	}
	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getCol4() {
		return col4;
	}
	public void setCol4(String col4) {
		this.col4 = col4;
	}
	public String getCol5() {
		return col5;
	}
	public void setCol5(String col5) {
		this.col5 = col5;
	}
	public String getCol6() {
		return col6;
	}
	public void setCol6(String col6) {
		this.col6 = col6;
	}
	public String getCol7() {
		return col7;
	}
	public void setCol7(String col7) {
		this.col7 = col7;
	}
	public String getCol8() {
		return col8;
	}
	public void setCol8(String col8) {
		this.col8 = col8;
	}
	public String getCol9() {
		return col9;
	}
	public void setCol9(String col9) {
		this.col9 = col9;
	}
	public String getCol10() {
		return col10;
	}
	public void setCol10(String col10) {
		this.col10 = col10;
	}
	public String getCol11() {
		return col11;
	}
	public void setCol11(String col11) {
		this.col11 = col11;
	}
	public String getCol12() {
		return col12;
	}
	public void setCol12(String col12) {
		this.col12 = col12;
	}
	public String getCol13() {
		return col13;
	}
	public void setCol13(String col13) {
		this.col13 = col13;
	}
	public String getCol14() {
		return col14;
	}
	public void setCol14(String col14) {
		this.col14 = col14;
	}
	public String getCol15() {
		return col15;
	}
	public void setCol15(String col15) {
		this.col15 = col15;
	}
	public String getCol16() {
		return col16;
	}
	public void setCol16(String col16) {
		this.col16 = col16;
	}
	public String getCol17() {
		return col17;
	}
	public void setCol17(String col17) {
		this.col17 = col17;
	}
	public String getCol18() {
		return col18;
	}
	public void setCol18(String col18) {
		this.col18 = col18;
	}
	public String getCol19() {
		return col19;
	}
	public void setCol19(String col19) {
		this.col19 = col19;
	}
	public String getCol20() {
		return col20;
	}
	public void setCol20(String col20) {
		this.col20 = col20;
	}
	
	public String getCol21() {
		return col21;
	}

	public void setCol21(String col21) {
		this.col21 = col21;
	}

	public String getCol22() {
		return col22;
	}

	public void setCol22(String col22) {
		this.col22 = col22;
	}

	public String getCol23() {
		return col23;
	}

	public void setCol23(String col23) {
		this.col23 = col23;
	}

	public String getCol24() {
		return col24;
	}

	public void setCol24(String col24) {
		this.col24 = col24;
	}

	public String getCol25() {
		return col25;
	}

	public void setCol25(String col25) {
		this.col25 = col25;
	}

	public String getCol26() {
		return col26;
	}

	public void setCol26(String col26) {
		this.col26 = col26;
	}

	public String getCol27() {
		return col27;
	}

	public void setCol27(String col27) {
		this.col27 = col27;
	}

	public String getCol28() {
		return col28;
	}

	public void setCol28(String col28) {
		this.col28 = col28;
	}

	public String getCol29() {
		return col29;
	}

	public void setCol29(String col29) {
		this.col29 = col29;
	}

	public String getCol30() {
		return col30;
	}

	public void setCol30(String col30) {
		this.col30 = col30;
	}

	public String getCol31() {
		return col31;
	}

	public void setCol31(String col31) {
		this.col31 = col31;
	}

	public String getCol32() {
		return col32;
	}

	public void setCol32(String col32) {
		this.col32 = col32;
	}
	
	public String getCol33() {
		return col33;
	}

	public void setCol33(String col33) {
		this.col33 = col33;
	}

	public String getCol34() {
		return col34;
	}

	public void setCol34(String col34) {
		this.col34 = col34;
	}

	public String getCol35() {
		return col35;
	}

	public void setCol35(String col35) {
		this.col35 = col35;
	}

	public String getCol36() {
		return col36;
	}

	public void setCol36(String col36) {
		this.col36 = col36;
	}

	public String getCol37() {
		return col37;
	}

	public void setCol37(String col37) {
		this.col37 = col37;
	}

	public String getCol38() {
		return col38;
	}

	public void setCol38(String col38) {
		this.col38 = col38;
	}

	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getReg_user() {
		return reg_user;
	}
	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getUpdate_date() {
		return update_date;
	}
	
	public List<CodeModel> getVerify_result_3List() {
		return verify_result_3List;
	}

	public void setVerify_result_3List() {
		this.verify_result_3List = CodeUtil.convertCodeModel(verify_result_3);
	}
	
	public String getReg_user_name() {
		return reg_user_name;
	}

	public void setReg_user_name(String reg_user_name) {
		this.reg_user_name = reg_user_name;
	}

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	
	public List<CodeModel> getWork_result_3List() {
		return work_result_3List;
	}

	public void setWork_result_3List() {
		this.work_result_3List = CodeUtil.convertCodeModel(work_result_3);
	}

	public String getPrev_content() {
		return prev_content;
	}

	public void setPrev_content(String prev_content) {
		this.prev_content = prev_content;
	}

	public String getCol43() {
		return col43;
	}

	public void setCol43(String col43) {
		this.col43 = col43;
	}

	public String getVerify_start_date() {
		return verify_start_date;
	}

	public void setVerify_start_date(String verify_start_date) {
		this.verify_start_date = verify_start_date;
	}

	public String getVerify_end_date() {
		return verify_end_date;
	}

	public void setVerify_end_date(String verify_end_date) {
		this.verify_end_date = verify_end_date;
	}

	public String getUpdate_gubun() {
		return update_gubun;
	}

	public void setUpdate_gubun(String update_gubun) {
		this.update_gubun = update_gubun;
	}
	
	
}
