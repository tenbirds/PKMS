package com.pkms.common.tags.paging.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.model.AbstractModel;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.wings.properties.service.PropertyServiceIf;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : 
 * @Date : 2012. 4. 5.
 * 
 */
@Service("PagingService")
public class PagingService implements PagingServiceIf {

	@Resource(name = "PropertyService")
	private PropertyServiceIf propertyService;

	@Override
	public PaginationInfo getPaginationInfo(AbstractModel abstractPagingModel) throws Exception {
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(abstractPagingModel.getPageIndex());
		paginationInfo.setRecordCountPerPage(propertyService.getInt("recordCountPerPage"));
		paginationInfo.setPageSize(propertyService.getInt("pageSize"));
		
		abstractPagingModel.setFirstIndex(paginationInfo.getFirstRecordIndex());
		abstractPagingModel.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		return paginationInfo;
	}

	@Override
	public PaginationInfo getPaginationInfo(AbstractModel abstractPagingModel, int recordCountPerPage, int pageSize) throws Exception {
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(abstractPagingModel.getPageIndex());
		paginationInfo.setRecordCountPerPage(recordCountPerPage);
		paginationInfo.setPageSize(pageSize);
		
		abstractPagingModel.setFirstIndex(paginationInfo.getFirstRecordIndex());
		abstractPagingModel.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		return paginationInfo;
	}

}
