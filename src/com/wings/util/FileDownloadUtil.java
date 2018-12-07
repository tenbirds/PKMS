package com.wings.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Redirect;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

import com.pkms.common.mail.service.MailService;


public class FileDownloadUtil {
	static Logger logger = Logger.getLogger(FileDownloadUtil.class);

	/**
	 * 첨부파일 다운로드.
	 * 
	 * @param attachFileModel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, String localFile, String orgFileName) throws Exception {
		File uFile = new File(localFile);
		int fSize = (int) uFile.length();
		
		//URL 파일명 유효성 체크 시작, 2012.7.24
		Boolean result = true;
		
		Map map = request.getParameterMap();
		Iterator it = map.keySet().iterator();	
		
		String key = null;
		String[] val = null;
		
		while(it.hasNext()){
			key = (String) it.next();
			val = request.getParameterValues(key);
			
			if(val != null){
				for(int i=0; i<val.length; i++){
					if(val[i].indexOf("/") > -1 || val[i].indexOf(File.separator) > -1 || val[i].indexOf("\\") > -1 || val[i].indexOf("..") > -1){
						result = false;
						break;
					}
				}
			}
			if(!result) break;
		}
		
		if(!result){
			PrintWriter printwriter = response.getWriter();
			
			response.setContentType("text/html;charset=UTF-8");
			
			printwriter.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
			printwriter.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			printwriter.println("<head>");
			printwriter.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
			printwriter.println("<script>");
			printwriter.println("alert('요청 파일이 잘못 되었습니다.');");
			printwriter.println("</script>");
			printwriter.println("</head>");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
		}else{
			if (fSize > 0) {
				String mimetype = "application/x-msdownload";

				// response.setBufferSize(fSize); // 2010-12-07
				response.setContentType(mimetype);
				// response.setHeader("Content-Disposition",
				// "attachment; filename=\"" +
				// URLEncoder.encode(fvo.getOrignlFileNm(), "utf-8") +
				// "\"");
				setDisposition(orgFileName, request, response);
				response.setContentLength(fSize);

				/*
				 * FileCopyUtils.copy(in, response.getOutputStream());
				 * in.close(); response.getOutputStream().flush();
				 * response.getOutputStream().close();
				 */
				BufferedInputStream in = null;
				BufferedOutputStream out = null;

				try {
					in = new BufferedInputStream(new FileInputStream(uFile));
					out = new BufferedOutputStream(response.getOutputStream());

					FileCopyUtils.copy(in, out);
					out.flush();
				} catch (Exception ex) {
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception ex) {
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (Exception ex) {
						}
					}
				}
			} else {
				//response.setContentType("application/x-msdownload");

				PrintWriter printwriter = response.getWriter();
				
				response.setContentType("text/html;charset=UTF-8");
				
				printwriter.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
				printwriter.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
				printwriter.println("<head>");
				printwriter.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
				printwriter.println("<script>");
				printwriter.println("alert('파일을 찾을 수 없습니다.\\n\\n파일명: " + orgFileName + "');");
				printwriter.println("</script>");
				printwriter.println("</head>");
				printwriter.println("</html>");
				printwriter.flush();
				printwriter.close();
			}	
		}
	}
	
	/**
	 * 브라우저 구분 얻기.
	 * 
	 * @param request
	 * @return
	 */
	private static String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}

	/**
	 * Disposition 지정하기.
	 * 
	 * @param filename
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private static void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String browser = getBrowser(request);

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;

		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			// throw new RuntimeException("Not supported browser");
			throw new IOException("Not supported browser");
		}

		response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

		if ("Opera".equals(browser)) {
			response.setContentType("application/octet-stream;charset=UTF-8");
		}
	}
	
}
