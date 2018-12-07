package com.pkms.pkgmg.schedule.service;

import java.util.List;

import com.pkms.pkgmg.schedule.model.ScheduleModel;

/**
 * PKG검증/일정 > 일정목록<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 05.
 * 
 */
public interface ScheduleServiceIf {

	public List<ScheduleModel> readList(ScheduleModel scheduleModel) throws Exception;

	public ScheduleModel setSearchCondition(ScheduleModel scheduleModel) throws Exception;
	public String excelDownload(ScheduleModel scheduleModel) throws Exception;

	
	/**pkg검증일정-미완료건 7일마다 메일/sms 발송 추가  */
	public void sendPkgMailSms() throws Exception;
	
	public void create_testbedEq() throws Exception;
	public void create_realEq() throws Exception;

	public void getFtpFile(String connect, String filename) throws Exception;
	
	public void create_group_depth() throws Exception;
	
}
