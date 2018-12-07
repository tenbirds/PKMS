<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>


<%
	String type = request.getParameter("type");

	if("B".equals(type)){
%>		
		<img src="/images/notice_popup_bp.gif" />
		<span style="margin-left:13px;">
			<a href="http://203.236.20.242/PKMS_BP_menual.doc">
				<img src="/images/notice_popup_bp_link.gif" alt="BP매뉴얼 다운로드" border="0" />
			</a>
		</span>
<%
	}else if("S".equals(type)){
%>
		<img src="/images/notice_popup_skt.gif" />
<%
	}else{
%>
		<img src="/images/notice_popup.gif" />
<%
	}
%>