package com.pkms.common.newmail.service;

import java.util.List;

import com.pkms.common.mail.model.MailModel;
import com.pkms.common.newmail.model.NewMailModel;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;

public interface NewMailServiceIf {

	
	public void maileModule(String system_seq, List<String> gubun, String nowPkgStatus,NewMailModel inputModel);
	
	public void maileModuleUserId(  String nowPkgStatus, NewMailModel inputModel);
	
	public List<NewMailModel> pkgUserInfoList( NewMailModel inputModel) throws Exception;
	
	public List<String> pkgUserIdList( NewMailModel inputModel) throws Exception;
	
	public List<String> pkgUserIdList2( NewMailModel inputModel) throws Exception;
	
	public List<NewMailModel> eqmentUserList(  NewMailModel eqment_seq) throws Exception;
	
	public String genrerateString(List<PkgEquipmentModel> eqmentList ,Pkg21Model pkg21Model , String stepCheck ,String user_ord) throws Exception;
	
	public List<PkgEquipmentModel> eqmentList(Pkg21Model pkg21Model ) throws Exception;
	
	public String makeSmsMsg(Pkg21Model pkg21Model,String stepCheck) throws Exception ;

	//wired 유선
	public String genrerateStringWired(List<PkgEquipmentModel> eqmentList ,Pkg21Model pkg21Model , String stepCheck ,String user_ord) throws Exception;
	
	public String makeSmsMsgWired(Pkg21Model pkg21Model,String stepCheck) throws Exception ;
	
}
