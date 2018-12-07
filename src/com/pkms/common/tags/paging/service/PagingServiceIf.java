package com.pkms.common.tags.paging.service;

import com.pkms.common.model.AbstractModel;
import com.pkms.common.tags.paging.pagination.PaginationInfo;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : 
 * @Date : 2012. 4. 5.
 * 
 */
public interface PagingServiceIf {

	public PaginationInfo getPaginationInfo(AbstractModel abstractPagingModel) throws Exception;

	public PaginationInfo getPaginationInfo(AbstractModel abstractPagingModel, int recordCountPerPage, int pageSize) throws Exception;

}
