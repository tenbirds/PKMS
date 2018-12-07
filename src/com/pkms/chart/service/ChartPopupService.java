package com.pkms.chart.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.pkms.statsmg.stats.model.StatsModel;
import com.pkms.statsmg.stats.service.StatsServiceIf;
import com.wings.properties.service.PropertyServiceIf;

@Service("ChartPopupService")
public class ChartPopupService implements ChartPopupServiceIf{

	
	@Resource(name = "StatsService")
	private StatsServiceIf statsService;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Override
	public List<StatsModel> readList(StatsModel statsModel) throws Exception {
//		건수:${stats.value.pkg_count}
		List<StatsModel> resultList = statsService.readList(statsModel);
		
		if(statsModel.getChartCondition().equals("0")){
			statsModel.setChartGunun4Y("건수");
		}else if(statsModel.getChartCondition().equals("1")){
			statsModel.setChartGunun4Y("검증분야개수");
		}else if(statsModel.getChartCondition().equals("2")){
			statsModel.setChartGunun4Y("초도");
		}else if(statsModel.getChartCondition().equals("3")){
			statsModel.setChartGunun4Y("확대");
		}else if(statsModel.getChartCondition().equals("4")){
			statsModel.setChartGunun4Y("원복");
		}else if(statsModel.getChartCondition().equals("5")){
			statsModel.setChartGunun4Y("NEW");
		}else if(statsModel.getChartCondition().equals("6")){
			statsModel.setChartGunun4Y("PN");
		}else if(statsModel.getChartCondition().equals("7")){
			statsModel.setChartGunun4Y("CR");
		}
		
		if(statsModel.getDateCondition().equals("DD")){
			statsModel.setChartGunun4X("일별");
		}else if(statsModel.getDateCondition().equals("WW")){
			statsModel.setChartGunun4X("주별");
		}else if(statsModel.getDateCondition().equals("MM")){
			statsModel.setChartGunun4X("월별");
		}
		
		String key = "";
		for(StatsModel list : resultList){
			
			for(StatsModel map : list.getKindStatsMap().values()){
				
				key = "";
				
				if(statsModel.isKind_group1()){
					key += map.getGroup1_name();
				}
				if(statsModel.isKind_group2()){
					key += ">" + map.getGroup2_name();
				}
				if(statsModel.isKind_group3()){
					key += ">" + map.getGroup3_name();
				}
				if(statsModel.isKind_system()){
					key += ">" + map.getSystem_name();
				}
				if(statsModel.isKind_user()){
					key += ">" + map.getSystem_user_name();
				}
				if(statsModel.isKind_idc()){
					key += ">" + map.getIdc_name();
				}
				if(statsModel.isKind_equipment()){
					key += ">" + map.getEquipment_name();
				}
				
				map.setGroup_name(key);
			}
		}
		
		return resultList;
	}

}
