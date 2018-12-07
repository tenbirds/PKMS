package com.pkms.common.owms.service;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.owms.dao.OwmsDao;
import com.pkms.common.owms.model.OwmsModel;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : 
 * @Date : 2012. 
 * 
 */
@Service("OwmsService")
public class OwmsService implements OwmsServiceIf {
	
	@Resource(name = "OwmsDao")
	private OwmsDao owmsDao;
	
	private final String PROTOCOL = "HTTP://";
	private final String SERRVER = "owms.sktelecom.com";
	private final String CONTEXT = "/pkmsLink.do";
	
	/*
	private final String PROTOCOL = "HTTP://";
	private final String SERRVER = "localhost";
	private final String CONTEXT = "/conn_test.jsp";
	*/

	@Override
	public void create(OwmsModel owmsModel) {
		/**
		 * OWMS 연동 정보를 저장
		 * 연동 데이터를 받아서 HTTP POST로 전달
		 */
		
		StringBuffer postData = new StringBuffer("reqno=").append(owmsModel.getReqno()).append("&applicant_id=").append(owmsModel.getApplicant_id())
				.append("&reqtlt=").append(owmsModel.getReqtlt()).append("&change_sumary=").append(owmsModel.getChange_sumary())
				.append("&applicant_sumary=").append(owmsModel.getApplicant_sumary()).append("&equipment_sumary=").append(owmsModel.getEquipment_sumary())
				.append("&test_mobile_num=").append(owmsModel.getTest_mobile_num()).append("&emergencyflg=").append(owmsModel.getEmergencyflg())
				.append("&test_dt=").append(owmsModel.getTest_dt());
		
		try{
			
			URL url = new URL(new StringBuffer(PROTOCOL).append(SERRVER).append(CONTEXT).toString());
			URLConnection connection = url.openConnection();
			
			HttpURLConnection hurlc = (HttpURLConnection)connection;
			hurlc.setRequestMethod("POST");				//전달 방식 post, get
			hurlc.setDoInput(true);						//서버로부터 메세지를 받을수 있도록 함, default true
			hurlc.setDoOutput(true);					//서버로 데이터를 전송할 수 있도록 함, get이면 안씀, true면 자동 post default false
			hurlc.setDefaultUseCaches(false);			//
			hurlc.setUseCaches(false);
			
			PrintWriter out = new PrintWriter(hurlc.getOutputStream());
			out.println(postData);
			out.close();
			
			long startTime = System.currentTimeMillis();
			
			InputStream input = connection.getInputStream();
			byte buffer[] = new byte[4096];
			int cnt = -1;
			
			Map<String, List<String>> headers = connection.getHeaderFields();
			Iterator<String> itr = headers.keySet().iterator();
			
			System.out.println("----------------- HTTP Responses go like this ...");
			while(itr.hasNext()){
				Object key = itr.next();
				System.out.println(key+"="+headers.get(key));
			}
			
			while((cnt = input.read(buffer))!=-1){
				System.out.print(new String(buffer, 0, cnt));
			}
			
			input.close();
			
			System.out.println("-----------------  HTTP Responses has been finished ...");
			System.out.println("####### Elapsed Time = ["+(System.currentTimeMillis() - startTime)+" ms]");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public OwmsModel read(OwmsModel owmsModel) throws Exception {
		/**
		 * OWMS 연동 테이블에서 상태값 조회
		 * OWM_PKMS_LINK
		 * OWM_PKMS_LINK.PROC_ST_CD 가 진행 상태
		 * 11가지 코드값이 있지만 현재 AS-IS는 
		 * 요청된 REQ_NO 에 대해서 PROC_ST_CD 가 10, 20, 30이 있을때 과금처리 완료
		 * 10 : 검토 요청, 20 : 과금담당자승인, 30 : IT기술원담당자 승인
		 * TO-BE는 ????????????????????????????????????????????????
		 */
		
		owmsModel = owmsDao.read(owmsModel);
		
		return owmsModel;
	}
}
