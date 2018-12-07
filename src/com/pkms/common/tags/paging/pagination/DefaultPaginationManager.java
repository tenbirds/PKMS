package com.pkms.common.tags.paging.pagination;

import java.util.Map;

/**
 * 인터페이스 PaginationManager 기본 구현 클래스.<br>
 * PaginationRenderer의 구현체를 빈설정 파일을 참조하여 반환한다.
 * 
 * @author : skywarker
 * @Date : 2012. 3. 14.
 * 
 */
public class DefaultPaginationManager implements PaginationManager {

	private Map<String, PaginationRenderer> rendererType;

	/**
	 * Set PaginationRenderer 구현 클래스
	 * 
	 * @param rendererType
	 *            - PaginationRenderer 구현 클래스 집합 Map.
	 */
	public void setRendererType(Map<String, PaginationRenderer> rendererType) {
		this.rendererType = rendererType;
	}

	/**
	 * 
	 * @param type
	 *            - tag의 파라미터 값.
	 */
	public PaginationRenderer getRendererType(String type) {

		return (rendererType != null && rendererType.containsKey(type)) ? (PaginationRenderer) rendererType.get(type) : new DefaultPaginationRenderer();
	}

}
