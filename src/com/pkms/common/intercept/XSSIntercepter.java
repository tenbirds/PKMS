package com.pkms.common.intercept;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author PKMS
 * 특정문자 replace intercepter
 * pkms-servlet.xml 에 정의된 path 에 적용
 */
public class XSSIntercepter extends HandlerInterceptorAdapter {

	private Logger log = Logger.getLogger(this.getClass());

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	
		log.debug("=========XSS Intercepter Start===========");
		
		Map map = request.getParameterMap();
		Iterator it = map.keySet().iterator();
		
		String key = null;
		String[] val = null;
		
		while(it.hasNext()){
			key = (String) it.next();
			val = request.getParameterValues(key);
			
			if(val != null){
				for(int i=0; i<val.length; i++){
					val[i] = cleanXSS(val[i]);
					log.debug("=========XSS Filtering===========" + key + ":" + val[i]);
				}
			}
		}
		log.debug("=========XSS Intercepter End===========");
		
		return true;
	}
	
	/**
	 * XSS 필터링 함수
	 * @param value
	 * @return
	 */
	private String cleanXSS(String value) {                 
        value = value.replaceAll("<", "＜").replaceAll(">", "＞");
        value = value.replaceAll("\\(", "（").replaceAll("\\)", "）");
        value = value.replaceAll("\'", "’");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "ｓｃｒｉｐｔ");
		return value;
	 } 
}
