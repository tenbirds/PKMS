package com.pkms.statsmg.stats.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.statsmg.stats.dao.StatsDAO;
import com.pkms.statsmg.stats.model.StatsModel;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ExcelUtil;
import com.wings.util.ObjectUtil;

/**
 * 
 * 
 * PKG 통계 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 28.
 * 
 */
@Service("StatsService")
public class StatsService implements StatsServiceIf {
	static Logger logger = Logger.getLogger(StatsService.class);
	@Resource(name = "StatsDAO")
	private StatsDAO statsDAO;

	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;

	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;

	private final String STATS_READLIST_SESSION_KEY = "STATS_READLIST_SESSION_KEY";

	@Override
	public List<StatsModel> readList(StatsModel statsModel) throws Exception {
		List<StatsModel> resultList = new ArrayList<StatsModel>();

		if (!StringUtils.hasLength(statsModel.getEnd_date()) || !StringUtils.hasLength(statsModel.getStart_date())) {
			return resultList;
		}

		TreeMap<Object, StatsModel> dateStatsMap = new TreeMap<Object, StatsModel>();

		// String kindKey = "";
		// String kindName = "";

		List<StatsModel> equipmentList = statsDAO.readList(statsModel);
		
		for (StatsModel stats : equipmentList) {

			StatsModel dateStatsModel = stats.clone();

			// 분류에 따른 그룹핑 키 생성
			String kindKey = "";
//			System.out.println("td 합치자==="+stats.getRownum().length());
			if(stats.getRownum().length() == 1){
				stats.setRownum("0"+stats.getRownum());
			}else if(stats.getRownum().length() == 3){
				stats.setRownum("A"+stats.getRownum());
			}
			kindKey += stats.getRownum();

			StatsModel sumStatsModel = dateStatsModel.getKindStatsMap().get(kindKey);
			if (sumStatsModel == null) {
				sumStatsModel = stats.clone();
				// sumStatsModel.setGroup_name(kindName);
			}

			StatsModel subStatsModel = stats.clone(); 

			// pkg count
			sumStatsModel.getPkgSeqMap().put(stats.getPkg_seq(), stats.getPkg_name());
			
//			sumStatsModel.getSubStatsMap().put(subKey, subStatsModel);
			sumStatsModel.getSubStatsMap().put(kindKey, subStatsModel);
			
			dateStatsModel.getKindStatsMap().put(kindKey, sumStatsModel);
			
			dateStatsMap.put(kindKey, dateStatsModel);
		}
		
		for (StatsModel dateStats : dateStatsMap.values()) {

			for (StatsModel stats : dateStats.getKindStatsMap().values()) {
				// NEW/PN/CR 개수 세팅
				stats.initPkg_seq_list();
//				List<StatsModel> pncrList = statsDAO.readListPncr(stats);
//				for (StatsModel pncr : pncrList) {
//					stats.setNewCount(pncr.getNewCount());
//					stats.setPnCount(pncr.getPnCount());
//					stats.setCrCount(pncr.getCrCount());
//				}
				
				// PKG 상세 검증 개수 세팅
				List<StatsModel> verifyList = statsDAO.readListPkgVerify(stats);
				int verifyCount = 0;
				for (StatsModel verify : verifyList) {
					try {
						verifyCount += Integer.parseInt(verify.getPkg_verify_count().trim());
//						System.out.println("=========================verifyCount================================"+verifyCount);
					} catch (Exception ex) {
					}
				}
				stats.setPkg_verify_count(String.valueOf(verifyCount));

			}
			
			resultList.add(dateStats);

			logger.debug("resultList.size()---------------------->"+resultList.size());
		}
		
		return resultList;
	}

	@Override
	public StatsModel setSearchCondition(StatsModel statsModel) throws Exception {
		if (statsModel.isSessionCondition()) {
			StatsModel sessionModel = (StatsModel) sessionService.read(STATS_READLIST_SESSION_KEY);
			if (sessionModel == null) {
				statsModel = new StatsModel();
			} else {
				statsModel = sessionModel;
			}
		} else {
			sessionService.create(STATS_READLIST_SESSION_KEY, statsModel);
		}
		return statsModel;

	}

	/**
	 * EXCEL DOWNLOAD
	 */

	public String excelDownload(StatsModel statsModel) throws Exception {

		statsModel.setPaging(false); // QuarterlyReportModel
										// quarterlyReportModel

		List<StatsModel> readList = this.readList(statsModel);
		
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> colList;
//		String[] headers = new String[] { "date_unit" };// equipmentPkgEndCount
														// equipmentPkgRevertCount
		
		// kind_group1,kind_group2,kind_group3,kind_system,kind_user,kind_idc,kind_equipment
//		String header_temp = "";
//		String excel_head = "날짜";
		
/*
 * 선택사항에 따른 분류 추가 if (statsModel.isKind_??()) {}
 */
//		if (statsModel.isKind_group1()) {// 대분류 
//			header_temp += "group1_name";
//			excel_head += ",대분류";
//		}
		
		String header_temp = "rownum,pkg_name,system_name,content,group1_name,group2_name,group3_name,system_user_name,ver,ver_gubun,sbt,roaming_link,pe_yn,verify_day,dev_yn";
		String excel_head = "순번,PKG제목,시스템,주요보완내역,대분류,중분류,소분류,대표담당자,버전,버전구분,SBT,로밍영향도,과금영향도,검증소요일,검증요청범위";
		
		
		/*if (header_temp.length() > 0) { // 중분류
			header_temp += ",group2_name";
			excel_head += ",중분류";
		} else {
			header_temp += "group2_name";
			excel_head += "중분류";
		}*/
		
		String[] headers2 = header_temp.split(",");
		String[] headers3 = "pkg_count,pkg_verify_count,chodo_workdate,chodo,hwakdae_workdate,hwakdae,pkg_end_ox,newCount,pnCount,crCount,on_yn,non_yn,vol_yn,cha_yn,sec_yn,on_comment,non_comment,vol_comment,cha_comment,sec_comment,col4,col4_file,col6,col6_file,col8,col8_file,col29,col29_file,col10,col10_file,col30,col30_file,col31,col31_file,col32,col32_file,col12,col12_file,col41,col41_file,col14,col14_file,col16,col16_file,col18,col18_file,col20,col20_file,col22,col22_file,col24,col24_file,col26,col26_file,col39,col39_file".split(",");
		List<Integer> merge_list = new ArrayList<Integer>();
		int mergeCnt =0;

		for (StatsModel stats : readList) {
			logger.debug("lllaaasssfffgggddeee---------->"+mergeCnt);
			
			TreeMap<String, StatsModel> statsModelTreeMap =stats.getKindStatsMap();
			Iterator<String> iterStats =statsModelTreeMap.keySet().iterator();
			
			merge_list.add(statsModelTreeMap.size());
			
			while (iterStats.hasNext()) {
				
				colList = new ArrayList<String>();
				
//				for (String field : headers) {
//
//					String temp = (ObjectUtil.getObjectFieldValue(stats, field) == null ? "" : ObjectUtil.getObjectFieldValue(stats, field).toString());
//					
//					if (statsModel.getDateCondition().equals("MM")) {// dateCondition
//						temp += " 월 ";
//					} else {
//						temp += " 주 ";
//					}
//					logger.debug("lllaaasssfffgggddeee--temp-------->"+temp);
//					colList.add(temp);
//				}
				
				Object key = iterStats.next();
				StatsModel result = statsModelTreeMap.get(key);
				
				for (String field : headers2) {
					colList.add(ObjectUtil.getObjectFieldValue(result, field) == null ? "" : ObjectUtil.getObjectFieldValue(result, field).toString());
				}

				for (String field : headers3) {
					colList.add(ObjectUtil.getObjectFieldValue(result, field) == null ? "" : ObjectUtil.getObjectFieldValue(result, field).toString());
				}

				rowList.add(colList);
				
				
				mergeCnt++;
			};

		}

		String excel_head2 = excel_head;
		excel_head += ",PKG 검증,PKG 검증,PKG 적용,PKG 적용,PKG 적용,PKG 적용,PKG 적용,신규/보완/개선,신규/보완/개선,신규/보완/개선"
				+ ",검증 대상 수행 여부,검증 대상 수행 여부,검증 대상 수행 여부,검증 대상 수행 여부,검증 대상 수행 여부"
				+ ",검증 항목 Comment,검증 항목 Comment,검증 항목 Comment,검증 항목 Comment,검증 항목 Comment"
				+ ",보완 내역별 시험 결과,보완 내역별 시험 결과,Regression Test 및 기본 검증 결과,Regression Test 및 기본 검증 결과"
				+ ",성능 용량 시험 결과,성능 용량 시험 결과,개발 근거 문서,개발 근거 문서"
				+ ",신규 기능 규격서,신규 기능 규격서,보완 내역서,보완 내역서"
				+ ",시험 절차서,시험 절차서,코드 리뷰 및 SW 아키텍처 리뷰,코드 리뷰 및 SW 아키텍처 리뷰"
				+ ",기능 검증 결과,기능 검증 결과,성능 용량 시험 결과,성능 용량 시험 결과"
				+ ",보안내역서-기능 변경 요청서,보안내역서-기능 변경 요청서,보안내역별 검증 결과,보안내역별 검증 결과"
				+ ",서비스 영향도(로밍 포함),서비스 영향도(로밍 포함),과금 영향도,과금 영향도"
				+ ",작업절차서-S/W블록 내역,작업절차서-S/W블록 내역,PKG 적용 후 check list,PKG 적용 후 check list"
				+ ",CoD/PoD 변경사항-운용팀 공지사항,CoD/PoD 변경사항-운용팀 공지사항,보안 Guide 적용확인서,보안 Guide 적용확인서";
		
		excel_head2 += ",건수,검증분야,초도일자,초도적용장비수,확대일자,확대적용장비수,PKG완료여부,신규,보완,개선"
				+ ",기능,비기능,용량,과금,보안,기능,비기능,용량,과금,보안"
				+ ",Comment,첨부파일,Comment,첨부파일"
				+ ",Comment,첨부파일,Comment,첨부파일"
				+ ",Comment,첨부파일,Comment,첨부파일"
				+ ",Comment,첨부파일,Comment,첨부파일"
				+ ",Comment,첨부파일,Comment,첨부파일"
				+ ",Comment,첨부파일,Comment,첨부파일"
				+ ",Comment,첨부파일,Comment,첨부파일"
				+ ",Comment,첨부파일,Comment,첨부파일"
				+ ",Comment,첨부파일,Comment,첨부파일";
		//logger.debug("excel_head---------------------------->" + excel_head);
		//logger.debug("excel_head2---------------------------->" + excel_head2);

		Map<Integer, String[]> headerMap = new HashMap<Integer, String[]>();

		headerMap.put(0, excel_head.split(","));
		headerMap.put(1, excel_head2.split(","));

		//(String filename, String localFilePath, Map<Integer,String[]> headers, List<List<String>> dataList ,List<Integer> mergeCount,int mergeCell,String mergeType) 

		String downloadFileName = ExcelUtil.write(statsModel.getEXCEL_FILE_NAME(), propertyService.getString("Globals.fileStorePath"), headerMap, rowList, merge_list, 1, "R");

		return downloadFileName;
	}
	
	public String equipmentExcelDownload(StatsModel statsModel) throws Exception {

		statsModel.setPaging(false); // QuarterlyReportModel
										// quarterlyReportModel

		List<StatsModel> readList = this.equipment_readList(statsModel);
		TreeMap<Object, StatsModel> dateStatsMap = new TreeMap<Object, StatsModel>();
		for (StatsModel stats : readList) {

			StatsModel dateStatsModel = stats.clone();

			// 분류에 따른 그룹핑 키 생성
			String kindKey = "";

			kindKey += stats.getSystem_seq();

			StatsModel sumStatsModel = dateStatsModel.getKindStatsMap().get(kindKey);
			if (sumStatsModel == null) {
				sumStatsModel = stats.clone();
			}

			StatsModel subStatsModel = stats.clone(); 
			
			sumStatsModel.getSubStatsMap().put(kindKey, subStatsModel);
			
			dateStatsModel.getKindStatsMap().put(kindKey, sumStatsModel);
			
			dateStatsMap.put(kindKey, dateStatsModel);
		}
		
		
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> colList;
		
		String header_temp = "group1_name,group2_name,group3_name,system_name,supply,full_name,oneline_explain,impact_systems,road_map_ox";
		String excel_head = "대분류,중분류,소분류,시스템,공급사,시스템 Full Name,한줄설명,영향시스템,16년 로드맵 입력";
		
		String[] headers2 = header_temp.split(",");
		String[] headers3 = "boramae_cnt,sungsu_cnt,bundang_cnt,suyu_cnt,inchun_cnt,senterm_cnt,buam_cnt,taepyong_cnt,bonri_cnt,seobu_cnt,dunsan_cnt,busa_cnt,pkms_cnt,dv_user,da_user,vu_user,au_user,pu_user,eq_user,bpu0,bpu1,sales_user_info,vo_user,se_user,ch_user,no_user,file1,file2,file3,file4,file5,file6,file7,file8,system_user_name,dev_system_user_name,bp_user_name,non_ox,vol_ox,cha_ox,non_title,vol_title,cha_title".split(",");
		List<Integer> merge_list = new ArrayList<Integer>();
		int mergeCnt =0;

		for (StatsModel stats : readList) {
			logger.debug("엑셀 다운로드 merge 카운트---------->"+mergeCnt);
			
			TreeMap<String, StatsModel> statsModelTreeMap =stats.getKindStatsMap();
			Iterator<String> iterStats =statsModelTreeMap.keySet().iterator();
			
			merge_list.add(statsModelTreeMap.size());
			
			while (iterStats.hasNext()) {
				
				colList = new ArrayList<String>();
				
				Object key = iterStats.next();
				StatsModel result = statsModelTreeMap.get(key);

				for (String field : headers2) {
					colList.add(ObjectUtil.getObjectFieldValue(result, field) == null ? "" : ObjectUtil.getObjectFieldValue(result, field).toString());
				}

				for (String field : headers3) {
					colList.add(ObjectUtil.getObjectFieldValue(result, field) == null ? "" : ObjectUtil.getObjectFieldValue(result, field).toString());
				}

				rowList.add(colList);
				
				
				mergeCnt++;
			};

		}

		String excel_head2 = excel_head;
		excel_head += ",수도권,수도권,수도권,수도권,수도권,부산,부산,대구,대구,서부,중부,중부,PKMS"
				+ ",담당자,담당자,담당자,담당자,담당자,담당자,담당자,담당자,담당자,담당자,담당자,담당자,담당자"
				+ ",첨부파일,첨부파일,첨부파일,첨부파일,첨부파일,첨부파일,첨부파일,첨부파일"
				+ ",대표 담당자,대표 담당자,대표 담당자,템플릿 등록,템플릿 등록,템플릿 등록,템플릿 등록 항목,템플릿 등록 항목,템플릿 등록 항목";
		
		excel_head2 += ",보라매,성수,분당,수유,인천,센텀,부암,태평,본리,서 부,둔산,부사,검증센터"
				+ ",개발검증,개발승인,상용검증,상용승인,사업기획,장비운용,협력업체,협력업체1,영업담당,용량검증,보안검증,과금검증,비기능검증"
				+ ",시스템 매뉴얼,PKG 표준절차서,교육자료,관련규격,성능용량,시설현황,RM 방안,기타"
				+ ",상용,개발,BP,비기능,용량,과금,비기능,용량,과금";

		Map<Integer, String[]> headerMap = new HashMap<Integer, String[]>();

		headerMap.put(0, excel_head.split(","));
		headerMap.put(1, excel_head2.split(","));
		
		String downloadFileName = ExcelUtil.write("시설현황", propertyService.getString("Globals.fileStorePath"), headerMap, rowList, merge_list, 1, "R");

		return downloadFileName;
	}

	@Override
	public List<StatsModel> equipment_readList(StatsModel statsModel) throws Exception {
		List<StatsModel> resultList = statsDAO.equipment_readList(statsModel);
		
		return resultList;
	}
	
	@Override
	public List<StatsModel> equipment_idc_readList(HttpServletRequest request, StatsModel statsModel) throws Exception {
		List<StatsModel> resultList = statsDAO.equipment_idc_readList(statsModel);
		
		HashMap<String, Integer> colspan_cnt_map = new HashMap<String, Integer>();
		
		int cnt_seoul = 0;
		int cnt_busan = 0;
		int cnt_daegu = 0;
		int cnt_seobu = 0;
		int cnt_jungbu = 0;
		
		for (StatsModel stats : resultList) {	
			if("수도권Network본부".equals(stats.getCentral_name())){
				colspan_cnt_map.put("seoul", cnt_seoul+=1);
			}else if("부산Network본부".equals(stats.getCentral_name())){
				colspan_cnt_map.put("busan", cnt_busan+=1);
			}else if("대구Network본부".equals(stats.getCentral_name())){
				colspan_cnt_map.put("daegu", cnt_daegu+=1);
			}else if("서부Network본부".equals(stats.getCentral_name())){
				colspan_cnt_map.put("seobu", cnt_seobu+=1);
			}else if("중부Network본부".equals(stats.getCentral_name())){
				colspan_cnt_map.put("jungbu", cnt_jungbu+=1);
			}
			request.setAttribute("central", colspan_cnt_map);
		}

		return resultList;
	}
	
	@Override
	public List<StatsModel> equipment_cnt_readList(StatsModel statsModel) throws Exception {
		List<StatsModel> resultList = statsDAO.equipment_cnt_readList(statsModel);

		return resultList;
	}
	
	@Override
	public List<StatsModel> equipment_group3_sum_readList(StatsModel statsModel) throws Exception {
		List<StatsModel> resultList = statsDAO.equipment_group3_sum_readList(statsModel);

		return resultList;
	}
	
	@Override
	public List<StatsModel> equipment_group1_sum_readList(StatsModel statsModel) throws Exception {
		List<StatsModel> resultList = statsDAO.equipment_group1_sum_readList(statsModel);

		return resultList;
	}
	
	//기간 일 구하기
	 public static long diffOfDate(String startday, String endday) throws Exception
	{
	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	 Date beginDate = formatter.parse(startday);
	 Date endDate = formatter.parse(endday);
	 long diff = endDate.getTime() - beginDate.getTime();
	 long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
	 return diffDays;
	}

}
