package com.pkms.batch.sktdb.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pkms.batch.sktdb.dao.SktDbScheduleDao;
import com.pkms.batch.sktdb.model.SktDbScheduleModel;

@Service("SktDbScheduleService")
public class SktDbScheduleService implements SktDbScheduleServiceIf{
	@Resource(name = "SktDbScheduleDao")
	private SktDbScheduleDao sktDbScheduleDao;
	
	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 10 20 * * ?") 
//	@Scheduled(cron = "0 0 5 * * ?") /* 새벽 5시 */
//	@Scheduled(cron = "0 */1 * * * *") // 테스트
	public void syncDB_ONSInfo() {
		
		try {
			
			long startTime = System.currentTimeMillis();
			System.out.println("!!turn on ONS DB Scheduler!!");
			/* ONS DB */
//			List<SktDbScheduleModel> onsDeptList = (List<SktDbScheduleModel>) sktDbScheduleDao.readONSDeptList();
//			List<SktDbScheduleModel> onsPersonList = (List<SktDbScheduleModel>)sktDbScheduleDao.readONSPersonList();
			
			/* ONS 부서 */
			sktDbScheduleDao.onsDeptDelete();
			sktDbScheduleDao.onsDeptCreate();
			
			/* ONS 인사*/
			sktDbScheduleDao.onsPersonDelete();
			sktDbScheduleDao.onsPersonCreate();
			
			/* system 및 equipment 담당자 관리*/
			sktDbScheduleDao.deleteSYSUserList();
			sktDbScheduleDao.deleteEQUserList();
			
			long endTime = System.currentTimeMillis();
			System.out.println("ONS DB 정보 sync 소요시간(초.0f) : " + ( endTime - startTime )/1000.0f +"초");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
