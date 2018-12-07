package com.pkms.statsmg.reports.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.schedule.service.ScheduleService;
import com.pkms.statsmg.reports.dao.WeeklyReportDAO;
import com.pkms.statsmg.reports.model.QuarterlyReportModel.REPORT_ITEM;
import com.pkms.statsmg.reports.model.QuarterlyReportModel;
import com.pkms.statsmg.reports.model.WeeklyReportModel;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.DateUtil;
import com.wings.util.ExcelUtil;
import com.wings.util.ObjectUtil;

/**
 * 
 * 
 * 주간 보고서 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 28.
 * 
 */
@Service("WeeklyReportService")
public class WeeklyReportService implements WeeklyReportServiceIf {
	static Logger logger = Logger.getLogger(ScheduleService.class);
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "WeeklyReportDAO")
	private WeeklyReportDAO weeklyReportDAO;

	@Override
	public List<WeeklyReportModel> readList(WeeklyReportModel weeklyReportModel) throws Exception {
		List<WeeklyReportModel> resultList = new ArrayList<WeeklyReportModel>();

		if (!StringUtils.hasLength(weeklyReportModel.getEnd_date()) || !StringUtils.hasLength(weeklyReportModel.getStart_date())) {
			return resultList;
		}

		TreeMap<String, WeeklyReportModel> resultMap = new TreeMap<String, WeeklyReportModel>();

		weeklyReportModel.setItem(REPORT_ITEM.PNCR);
		List<WeeklyReportModel> newPnCrList = weeklyReportDAO.readList(weeklyReportModel);
		for (WeeklyReportModel report : newPnCrList) {

			String kindKey = report.getGroup1_seq() + report.getGroup2_seq() + report.getGroup3_seq() + report.getSystem_seq();

			WeeklyReportModel sumReportModel = resultMap.get(kindKey);
			if (sumReportModel == null) {
				sumReportModel = report.clone();
			}

			if ("NEW".equals(report.getNew_pncr_gubun())) {
				sumReportModel.setNewCount(sumReportModel.getNewCount() + report.getCount());
			} else if ("PN".equals(report.getNew_pncr_gubun())) {
				sumReportModel.setPnCount(sumReportModel.getPnCount() + report.getCount());
			} else if ("CR".equals(report.getNew_pncr_gubun())) {
				sumReportModel.setCrCount(sumReportModel.getCrCount() + report.getCount());
			}

			resultMap.put(kindKey, sumReportModel);
		}

		weeklyReportModel.setItem(REPORT_ITEM.PKG);
		List<WeeklyReportModel> equipmentList = weeklyReportDAO.readList(weeklyReportModel);
		for (WeeklyReportModel report : equipmentList) {

			String kindKey = report.getGroup1_seq() + report.getGroup2_seq() + report.getGroup3_seq() + report.getSystem_seq();

			WeeklyReportModel reportModel = resultMap.get(kindKey);
			if (reportModel == null) {
				reportModel = report.clone();

			}

			String subKey = report.getWork_date() + report.getIdc_seq();

			WeeklyReportModel subReportModel = reportModel.getSubReportMap().get(subKey);
			if (subReportModel == null) {
				subReportModel = report.clone();
			}

			if ("99".equals(report.getStatus())) {
				if ("7".equals(report.getStatus2()) || "9".equals(report.getStatus2())) {
					subReportModel.setEquipmentPkgRevertCount(subReportModel.getEquipmentPkgRevertCount() + report.getCount());
				}
			} else {
				if ("S".equals(report.getWork_gubun())) {
					subReportModel.setEquipmentPkgStartCount(subReportModel.getEquipmentPkgStartCount() + report.getCount());
				} else if ("E".equals(report.getWork_gubun())) {
					subReportModel.setEquipmentPkgEndCount(subReportModel.getEquipmentPkgEndCount() + report.getCount());
				}
			}
			
			//pkg count
			reportModel.getPkgSeqMap().put(report.getPkg_seq(), report.getPkg_seq());
			
			reportModel.getSubReportMap().put(subKey, subReportModel);

			resultMap.put(kindKey, reportModel);
		}

		Iterator<String> iter = resultMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			WeeklyReportModel result = resultMap.get(key);
						
			// PKG 상세 검증 개수 세팅
			result.initPkg_seq_list();
			List<WeeklyReportModel> verifyList = weeklyReportDAO.readListPkgVerify(result);
			int verifyCount = 0;
			for(WeeklyReportModel verify : verifyList){
				try{
					verifyCount += Integer.parseInt(verify.getPkg_verify_count().trim());
				}catch(Exception ex){
				}
			}
			result.setPkg_verify_count(String.valueOf(verifyCount));

			
			resultList.add(result);
		}

		return resultList;
	}
	
	
	/**
	 * EXCEL DOWNLOAD
	 */
	
	public String excelDownload(WeeklyReportModel weeklyReportModel) throws Exception {
		
		weeklyReportModel.setPaging(false);  
		
		List<List<WeeklyReportModel>> DataList = new ArrayList<List<WeeklyReportModel>>();
		
		if (!StringUtils.hasLength(weeklyReportModel.getBase_date())) {
			weeklyReportModel.setBase_date(DateUtil.dateFormat());
		}
		
		// 현재기준 주의 끝 토요일 날짜
		//String baseDate = getBaseDate(weeklyReportModel.getBase_date());
		
		// 현재기준 주의 끝 토요일 날짜
		String baseDate = DateUtil.getBaseDate(weeklyReportModel.getBase_date());
		
		// baseDate 기준으로 실적 기준 날짜 세팅
		weeklyReportModel.setStart_date_before(DateUtil.formatDateByDayWeekly(baseDate, -6));
		weeklyReportModel.setEnd_date_before(baseDate);

		// 실적 조회
		weeklyReportModel.setStart_date(weeklyReportModel.getStart_date_before());
		weeklyReportModel.setEnd_date(weeklyReportModel.getEnd_date_before());
		
		List<WeeklyReportModel> readListWeek =  this.readList(weeklyReportModel);	
		DataList.add(readListWeek);
		
		
		// baseDate 기준으로 계획 기준 날짜 세팅
		weeklyReportModel.setStart_date_after(DateUtil.formatDateByDay(baseDate, 1));
		weeklyReportModel.setEnd_date_after(DateUtil.formatDateByDay(baseDate, 7));

		// 계획 조회
		weeklyReportModel.setStart_date(weeklyReportModel.getStart_date_after());
		weeklyReportModel.setEnd_date(weeklyReportModel.getEnd_date_after());
		
		List<WeeklyReportModel> readListNext =  this.readList(weeklyReportModel);	
		
		DataList.add(readListNext);
		
		
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> colList;
		 
		
		String[] headers1 = new String[]{"system_user_name","group1_name","group2_name","group3_name","system_name","work_date","과금","downtime","newCount"  };
		int datalist_cnt =0;
		List<Integer> merge_list =  new ArrayList<Integer>();
		
		for(List<WeeklyReportModel> datalist :DataList ){
			
			 colList = new ArrayList<String>();
			 String title ="";
			 
			 if(datalist_cnt==0){
				 title ="실적("+weeklyReportModel.getStart_date_before()+" ~ "+weeklyReportModel.getEnd_date_before()+")";
			 }else{
				 title = "계획("+weeklyReportModel.getStart_date_after()+" ~ "+weeklyReportModel.getEnd_date_after()+")";
			 }
			 
			 colList.add(title);
			 rowList.add(colList);
			 merge_list.add(1);
			 datalist_cnt++;
			for(WeeklyReportModel weeklyReport  : datalist){  
				merge_list.add(0);
				colList = new ArrayList<String>();
				 
				for(String field : headers1) {
					
					if( field.equals("system_name") ){
						
						String system_name = (ObjectUtil.getObjectFieldValue(weeklyReport, field)==null?"":ObjectUtil.getObjectFieldValue(weeklyReport, field).toString());
					    String pkg_count = (ObjectUtil.getObjectFieldValue(weeklyReport,"pkg_count")==null?"":ObjectUtil.getObjectFieldValue(weeklyReport, "pkg_count").toString());
					    String pkg_verify_count = (ObjectUtil.getObjectFieldValue(weeklyReport,"pkg_verify_count")==null?"":ObjectUtil.getObjectFieldValue(weeklyReport, "pkg_verify_count").toString());
					    
						system_name+="\n"+" (PKG 건수: "+pkg_count+"건)";
						system_name+="\n"+"(검증 건수: "+pkg_verify_count+"건)";
						
						colList.add(system_name);
						
					} else if( field.equals("work_date") ){
						
						TreeMap<String, WeeklyReportModel> workDateMap =weeklyReport.getSubReportMap();
						Iterator<String> iterDataMap =workDateMap.keySet().iterator();
						
						String work_date = "";
					    String idc_name =  "";
					    
					    int equipmentPkgStartCount = 0;
					    int equipmentPkgEndCount = 0;
					    int equipmentPkgRevertCount = 0;
					    
						while (iterDataMap.hasNext()) {
							
							
							String key = iterDataMap.next();
							WeeklyReportModel result = workDateMap.get(key);
							 
							//work_date+= result.getWork_date();
							idc_name = result.getIdc_name();
							
							equipmentPkgStartCount= result.getEquipmentPkgStartCount();
							equipmentPkgEndCount= result.getEquipmentPkgEndCount();
							equipmentPkgRevertCount= result.getEquipmentPkgRevertCount();
							
							work_date += "[ " + result.getWork_date()+" ]" + idc_name;
						    
						    if( equipmentPkgStartCount>0){
						    	work_date+="(초도 : "+equipmentPkgStartCount+")";	
						    }
						    if( equipmentPkgEndCount>0){
						    	work_date+="(확대 : "+equipmentPkgEndCount+")";	
						    }
						    if( equipmentPkgRevertCount>0){
						    	work_date+="(원복 : "+equipmentPkgRevertCount+")";	
						    } 
						    
						    work_date+="\n";
							
						};
						
					 
						TreeMap<String, String> pkgnoTreeMap =weeklyReport.getPkgSeqMap();
						Iterator<String> iterPkgNo =pkgnoTreeMap.keySet().iterator();
						
						String pkg_no ="";
						
						while (iterPkgNo.hasNext()) {
							
							String key = iterPkgNo.next();
							String result = pkgnoTreeMap.get(key);
							pkg_no +=result+",";
							
						};
						
					    work_date +="(pkg_seq: "+pkg_no.substring(0, pkg_no.length()-1)+" )";
					    
					    colList.add(work_date);
					    
						
					}else if( field.equals("newCount") ){
						
						String newCount = (ObjectUtil.getObjectFieldValue(weeklyReport, field)==null?"":ObjectUtil.getObjectFieldValue(weeklyReport, field).toString());
					    String pnCount = (ObjectUtil.getObjectFieldValue(weeklyReport,"pnCount")==null?"":ObjectUtil.getObjectFieldValue(weeklyReport, "pnCount").toString());
					    String crCount = (ObjectUtil.getObjectFieldValue(weeklyReport,"crCount")==null?"":ObjectUtil.getObjectFieldValue(weeklyReport, "crCount").toString());
					     
					    newCount = "NEW ( "+newCount+") / PN ( "+pnCount+" / CR ( "+crCount+")";
					    
					    colList.add(newCount); 
						
					}else if( field.equals("과금") ){ 
						 
					    colList.add("*"); 
						
					}else{
						colList.add((ObjectUtil.getObjectFieldValue(weeklyReport, field)==null?"":ObjectUtil.getObjectFieldValue(weeklyReport, field).toString()));
					}
				}
				 
				rowList.add(colList);
			 
			}//end for
			
		}
	     
		
		 String [] excel_header = "담당자 ,대분류,중분류,소분류,시스템,일정 및 국사별 PKG 적용건수,과금관련,서비스 중단,NEW/PN/CR 건수".split(",");
		 
		 Map<Integer,String[]> headerMap  = new HashMap<Integer,String[]>();  
		 
		 headerMap.put(0, excel_header); 
		
		String downloadFileName =ExcelUtil.write("주간 "+weeklyReportModel.getEXCEL_FILE_NAME(), propertyService.getString("Globals.fileStorePath"), headerMap, rowList,merge_list,8,"C");
		
		return downloadFileName;
	}

}//end class
