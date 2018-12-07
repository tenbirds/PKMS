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

import com.pkms.pkgmg.schedule.model.ScheduleModel;
import com.pkms.pkgmg.schedule.service.ScheduleService;
import com.pkms.statsmg.reports.dao.QuarterlyReportDAO;
import com.pkms.statsmg.reports.model.QuarterlyReportModel;
import com.pkms.statsmg.reports.model.QuarterlyReportModel.REPORT_ITEM;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ExcelUtil;
import com.wings.util.ObjectUtil;

/**
 * 
 * 
 * 분기별 보고서 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 28.
 * 
 */
@Service("QuarterlyReportService")
public class QuarterlyReportService implements QuarterlyReportServiceIf {
	static Logger logger = Logger.getLogger(ScheduleService.class);
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "QuarterlyReportDAO")
	private QuarterlyReportDAO quarterlyReportDAO;

	@Override
	public List<QuarterlyReportModel> readList(QuarterlyReportModel quarterlyReportModel) throws Exception {
		List<QuarterlyReportModel> resultList = new ArrayList<QuarterlyReportModel>();
		
		if (!StringUtils.hasLength(quarterlyReportModel.getEnd_date()) || !StringUtils.hasLength(quarterlyReportModel.getStart_date())) {
			return resultList;
		}

		TreeMap<String, QuarterlyReportModel> resultMap = new TreeMap<String, QuarterlyReportModel>();

		HashMap<String, HashMap<String, String>> pncrSystemCountMap = new HashMap<String, HashMap<String, String>>();

		String kindKey = "";
		String kindName = "";
		
		quarterlyReportModel.setItem(REPORT_ITEM.PNCR);
		List<QuarterlyReportModel> newPnCrList = quarterlyReportDAO.readList(quarterlyReportModel);
		for (QuarterlyReportModel report : newPnCrList) {

			// 분류에 따른 그룹핑 키 생성
			// 기본 0 - 대분류
			kindKey = report.getGroup1_seq();
			kindName = report.getGroup1_name();
			
			if("1".equals(quarterlyReportModel.getKindCondition())){// 중분류 선택
				kindKey = report.getGroup1_seq() + report.getGroup2_seq();
				kindName = report.getGroup1_name() + ">" + report.getGroup2_name();
				
			}else if("2".equals(quarterlyReportModel.getKindCondition())){// 소분류 선택
				kindKey = report.getGroup1_seq() + report.getGroup2_seq() + report.getGroup3_seq();
				kindName = report.getGroup1_name() + ">" + report.getGroup2_name() + ">" + report.getGroup3_name();
			}
			
			QuarterlyReportModel sumReportModel = resultMap.get(kindKey);
			if (sumReportModel == null) {
				sumReportModel = new QuarterlyReportModel();
				sumReportModel.setGroup_name(kindName);
			}
			sumReportModel.setPncrSystemName(report.getSystem_name());

			HashMap<String, String> countMap = pncrSystemCountMap.get(kindKey);
			if (countMap == null) {
				countMap = new HashMap<String, String>();
			}
			countMap.put(report.getSystem_seq(), report.getSystem_seq());
			pncrSystemCountMap.put(kindKey, countMap);

			if ("NEW".equals(report.getNew_pncr_gubun())) {
				sumReportModel.setNewCount(sumReportModel.getNewCount() + report.getCount());
			} else if ("PN".equals(report.getNew_pncr_gubun())) {
				sumReportModel.setPnCount(sumReportModel.getPnCount() + report.getCount());
			} else if ("CR".equals(report.getNew_pncr_gubun())) {
				sumReportModel.setCrCount(sumReportModel.getCrCount() + report.getCount());
			}

			resultMap.put(kindKey, sumReportModel);
		}

		HashMap<String, HashMap<String, String>> pkgSystemCountMap = new HashMap<String, HashMap<String, String>>();
		HashMap<String, HashMap<String, String>> pkgEquipmentCountMap = new HashMap<String, HashMap<String, String>>();
		
		quarterlyReportModel.setItem(REPORT_ITEM.PKG);
		List<QuarterlyReportModel> equipmentList = quarterlyReportDAO.readList(quarterlyReportModel);
		boolean check = false;
		for (QuarterlyReportModel report : equipmentList) {
			check = false;
			
			// 분류에 따른 그룹핑 키 생성
			// 기본 0 - 대분류
			kindKey = report.getGroup1_seq();
			kindName = report.getGroup1_name();
			
			if("1".equals(quarterlyReportModel.getKindCondition())){// 중분류 선택
				kindKey = report.getGroup1_seq() + report.getGroup2_seq();
				kindName = report.getGroup1_name() + ">" + report.getGroup2_name();
				
			}else if("2".equals(quarterlyReportModel.getKindCondition())){// 소분류 선택
				kindKey = report.getGroup1_seq() + report.getGroup2_seq() + report.getGroup3_seq();
				kindName = report.getGroup1_name() + ">" + report.getGroup2_name() + ">" + report.getGroup3_name();
			}

			QuarterlyReportModel sumReportModel = resultMap.get(kindKey);
			if (sumReportModel == null) {
				sumReportModel = new QuarterlyReportModel();
				
				sumReportModel.setGroup_name(kindName);
				
			}
			sumReportModel.setPkgSystemName(report.getSystem_name());

			if ("99".equals(report.getStatus())) {
				if ("7".equals(report.getStatus2()) || "9".equals(report.getStatus2())) {
					check = true;
					sumReportModel.setEquipmentPkgRevertCount(sumReportModel.getEquipmentPkgRevertCount() + report.getCount());
				}
			} else {
				if ("S".equals(report.getWork_gubun())) {
					check = true;
					sumReportModel.setEquipmentPkgStartCount(sumReportModel.getEquipmentPkgStartCount() + report.getCount());
				} else if ("E".equals(report.getWork_gubun())) {
					check = true;
					sumReportModel.setEquipmentPkgEndCount(sumReportModel.getEquipmentPkgEndCount() + report.getCount());
				}
			}

			if (check) {

				// PKG 관련 시스템 개수
				HashMap<String, String> systemCountMap = pkgSystemCountMap.get(kindKey);
				if (systemCountMap == null) {
					systemCountMap = new HashMap<String, String>();
				}
				systemCountMap.put(report.getSystem_seq(), report.getSystem_seq());
				pkgSystemCountMap.put(kindKey, systemCountMap);

				HashMap<String, String> equipmentCountMap = pkgEquipmentCountMap.get(kindKey);
				if (equipmentCountMap == null) {
					equipmentCountMap = new HashMap<String, String>();
				}
				equipmentCountMap.put(report.getEquipment_seq(), report.getEquipment_seq());
				pkgEquipmentCountMap.put(kindKey, equipmentCountMap);

				resultMap.put(kindKey, sumReportModel);
			}
		}

		Iterator<String> iter = resultMap.keySet().iterator();
		while (iter.hasNext()) {

			String key = iter.next();
			QuarterlyReportModel result = resultMap.get(key);

			result.setPncrSystemCount(getMapCount(pncrSystemCountMap.get(key)));
			result.setPkgSystemCount(getMapCount(pkgSystemCountMap.get(key)));
			result.setPkgEquipmentCount(getMapCount(pkgEquipmentCountMap.get(key)));

			resultList.add(result);
		}

		return resultList;
	}

	private int getMapCount(Map<String, String> countMap) {
		if (countMap == null) {
			return 0;
		}
		return countMap.size();
	}
	
	/**
	 * EXCEL DOWNLOAD
	 */
	
	public String excelDownload(QuarterlyReportModel quarterlyReportModel) throws Exception {
		
		quarterlyReportModel.setPaging(false); //QuarterlyReportModel quarterlyReportModel
		
		List<QuarterlyReportModel> readList =  this.readList(quarterlyReportModel);	
		
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> colList;
		String[] headers = new String[]{"group_name"};//equipmentPkgEndCount equipmentPkgRevertCount
		String[] headers1 = new String[] {"pkgSystemName","pkgEquipmentCount","equipmentPkgStartCount","newCount"};//equipmentPkgEndCount equipmentPkgRevertCount
		
		for(QuarterlyReportModel quarterlyReport  : readList){  
			colList = new ArrayList<String>();
			
			for(String field : headers) { 
				colList.add(ObjectUtil.getObjectFieldValue(quarterlyReport, field)==null?"":ObjectUtil.getObjectFieldValue(quarterlyReport, field).toString());
			}
			
			int equipmentPkgStartCount = Integer.parseInt((ObjectUtil.getObjectFieldValue(quarterlyReport, "equipmentPkgStartCount")==null?"0":ObjectUtil.getObjectFieldValue(quarterlyReport, "equipmentPkgStartCount").toString()));
			int equipmentPkgEndCount = Integer.parseInt((ObjectUtil.getObjectFieldValue(quarterlyReport, "equipmentPkgEndCount")==null?"0":ObjectUtil.getObjectFieldValue(quarterlyReport, "equipmentPkgEndCount").toString()));
			int equipmentPkgRevertCount = Integer.parseInt((ObjectUtil.getObjectFieldValue(quarterlyReport, "equipmentPkgRevertCount")==null?"0":ObjectUtil.getObjectFieldValue(quarterlyReport, "equipmentPkgRevertCount").toString()));

			if((equipmentPkgStartCount+equipmentPkgEndCount+equipmentPkgRevertCount)>0){
				
				for(String field : headers1) {
					
					if(field.equals("pkgSystemName")){
						
						int pkgSystemCount = Integer.parseInt((ObjectUtil.getObjectFieldValue(quarterlyReport, "pkgSystemCount")==null?"0":ObjectUtil.getObjectFieldValue(quarterlyReport, "pkgSystemCount").toString()));
					
						if(pkgSystemCount>1){
							
							colList.add((ObjectUtil.getObjectFieldValue(quarterlyReport, field)==null?"":ObjectUtil.getObjectFieldValue(quarterlyReport, field).toString())+" 외" +(pkgSystemCount-1)+" 종");
						 
						}else{
							colList.add((ObjectUtil.getObjectFieldValue(quarterlyReport, field)==null?"":ObjectUtil.getObjectFieldValue(quarterlyReport, field).toString()));
						}
						
					}else if(field.equals("equipmentPkgStartCount")){
						
						colList.add("초도 ("+equipmentPkgStartCount+")/"+"확대 ("+equipmentPkgEndCount+")/"+"원복 ("+equipmentPkgRevertCount+")");
						
					}else if(field.equals("pkgEquipmentCount")){
						String pkgEquipmentCount = (ObjectUtil.getObjectFieldValue(quarterlyReport,field)==null?"0":ObjectUtil.getObjectFieldValue(quarterlyReport,field).toString());
						
						colList.add(pkgEquipmentCount+" 식");
						
					}else if(field.equals("newCount")){
						
						int newCount = Integer.parseInt((ObjectUtil.getObjectFieldValue(quarterlyReport, "newCount")==null?"0":ObjectUtil.getObjectFieldValue(quarterlyReport, "newCount").toString()));
						int pnCount = Integer.parseInt((ObjectUtil.getObjectFieldValue(quarterlyReport, "pnCount")==null?"0":ObjectUtil.getObjectFieldValue(quarterlyReport, "pnCount").toString()));
						int crCount = Integer.parseInt((ObjectUtil.getObjectFieldValue(quarterlyReport, "crCount")==null?"0":ObjectUtil.getObjectFieldValue(quarterlyReport, "crCount").toString()));

						if((newCount+pnCount+crCount)>0){
							colList.add("NEW ("+newCount+") / PN ("+pnCount+") / CR ("+crCount+")");
						}else{
							colList.add("-");
						}
						
						
					}else{
						
						colList.add(ObjectUtil.getObjectFieldValue(quarterlyReport, field)==null?"":ObjectUtil.getObjectFieldValue(quarterlyReport, field).toString());	
					}
					
				}
			}
			 
			rowList.add(colList);
			
		} 
		
		String downloadFileName =ExcelUtil.write("분기 "+quarterlyReportModel.getEXCEL_FILE_NAME(), propertyService.getString("Globals.fileStorePath"), new String[]{ "분류","PKG 시스템","PKG 장비","PKG 적용 건수","NEW/PN/CR 건수"}, rowList );
		
		return downloadFileName;
	}
}
