package com.pkms.common.tags.paging;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.pkms.common.tags.paging.pagination.DefaultPaginationManager;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.pagination.PaginationManager;
import com.pkms.common.tags.paging.pagination.PaginationRenderer;

/**
 * 페이징을 위한 Tag class 실제 페이징을 위한 작업은 PaginationRenderer에게 위임한다.<br>
 * 어떤 PaginationRenderer를 사용할지는 PaginationManager에게 위임하는데,<br>
 * PaginationManager는 빈설정 파일의 정보와 태그의 type 속성값을 비교하여 PaginationRenderer을<br>
 * 결정한다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 3. 14.
 * 
 */
public class PaginationTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private PaginationInfo paginationInfo;
	private String type;
	private String jsFunction;

	public int doEndTag() throws JspException {

		try {

			JspWriter out = pageContext.getOut();

			PaginationManager paginationManager;

			// WebApplicationContext에 id 'paginationManager'로 정의된 해당
			// Manager를 찾는다.
			WebApplicationContext ctx = RequestContextUtils.getWebApplicationContext(pageContext.getRequest(), pageContext.getServletContext());

			if (ctx.containsBean("paginationManager")) {
				paginationManager = (PaginationManager) ctx.getBean("paginationManager");
			} else {
				// bean 정의가 없다면 DefaultPaginationManager를 사용. 빈설정이 없으면
				// 기본 적인 페이징 리스트라도 보여주기 위함.
				paginationManager = new DefaultPaginationManager();
			}

			PaginationRenderer paginationRenderer = paginationManager.getRendererType(type);

			String contents = paginationRenderer.renderPagination(paginationInfo, jsFunction);

			out.println(contents);

			return EVAL_PAGE;

		} catch (IOException e) {
			throw new JspException();
		}
	}

	public void setJsFunction(String jsFunction) {
		this.jsFunction = jsFunction;
	}

	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}

	public void setType(String type) {
		this.type = type;
	}
}
