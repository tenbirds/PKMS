package com.wings.util;

import javax.servlet.http.HttpServletRequest;

public class UserAgentUtil {

	public static boolean isMobile(HttpServletRequest request) {

		String userAgent = request.getHeader("user-agent").toLowerCase();
		
		if(userAgent != null 
				&&
				(userAgent.indexOf("lgtelecom") > -1 
				|| userAgent.indexOf("android") > -1 
				|| userAgent.indexOf("blackberry") > -1 
				|| userAgent.indexOf("iphone") > -1 
				|| userAgent.indexOf("ipad") > -1 
				|| userAgent.indexOf("samsung") > -1 
				|| userAgent.indexOf("symbian") > -1 
				|| userAgent.indexOf("sony") > -1 
				|| userAgent.indexOf("SCH-") > -1 
				|| userAgent.indexOf("SPH-") > -1
				)
				) {
			return true;
		}
		return false;
	}

}
