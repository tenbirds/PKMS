package com.pkms.common.login.service;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.pkms.common.login.dao.LoginDao;
import com.pkms.usermg.bp.model.BpModel;

@Service(value = "LoginService")
public class LoginService implements LoginServiceIf {
	@Resource(name = "LoginDao")
	private LoginDao loginDao;
	
	
	@Override
	public void SsoLogin(HttpServletRequest request, HttpServletResponse response, Model model, String loginType) throws Exception {
		
		String userId = null;
		String userType = null;
		String password = null;
		String selected_status = null;
		userId = request.getHeader("SM_USER");
//		userId = "1107749";

		if(null != request.getParameter("sm_user")){
			userId = request.getParameter("sm_user");
		}
		
		if(null!=userId){
			userType = "M";
		}
		
		if("M".equals(loginType)){
			userType = "MM";
			
			password = request.getHeader("password");
			if(null != request.getParameter("password")){
				password=request.getParameter("password");
			}
			
			selected_status = request.getHeader("selected_status");
			if(null != request.getParameter("selected_status")){
				selected_status=request.getParameter("selected_status");
			}
		}
		
		PrintWriter printwriter = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		printwriter.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		printwriter.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		printwriter.println("<head>");
		printwriter.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		printwriter.println("<script>");
		printwriter.println("function fn_Sso_submit(){");
		printwriter.println("	var form = document.getElementById(\"mForm\");");
		printwriter.println("	form.target = \"_self\";");
		printwriter.println("	form.action = \"/j_spring_security_check.do\";");
		printwriter.println("	form.submit();");
		printwriter.println("}");
		printwriter.println("</script>");
		printwriter.println("</head>");
		printwriter.println("<body onload=\"fn_Sso_submit()\">");
		printwriter.println("	<form id='mForm' action='/j_spring_security_check.do' method='post' onsubmit=\"return false;\">");
		printwriter.println("		<input type=\"hidden\" id=\"check_user_type\" name=\"check_user_type\" value=\"" + userType +"\" />");
		printwriter.println("		<input type=\"hidden\" id=\"j_username\" name=\"j_username\" value=\""+ userId +"\" />");
		printwriter.println("		<input type=\"hidden\" id=\"loginYype\" name=\"loginYype\" value=\""+ loginType +"\" />");
		if(loginType.equals("N")){
			printwriter.println("		<input type=\"hidden\" id=\"pkg_seq\" name=\"pkg_seq\" value=\""+ request.getParameter("pkg_seq") +"\" />");
		}
		if(loginType.equals("M")){
			printwriter.println("		<input type=\"hidden\" id=\"pkg_seq\" name=\"pkg_seq\" value=\""+ request.getParameter("pkg_seq") +"\" />");
			printwriter.println("		<input type=\"hidden\" id=\"j_password\" name=\"j_password\" value=\""+ password +"\" />");
			printwriter.println("		<input type=\"hidden\" id=\"selected_status\" name=\"selected_status\" value=\""+ selected_status +"\" />");
		}
		printwriter.println("	</form>");
		printwriter.println("</body>");
		printwriter.println("</html>");
		printwriter.flush();
		printwriter.close();
	}
	
	@Override
	public BpModel read_loginCnt(BpModel bpModel) {	
		return loginDao.read_loginCnt(bpModel);
	}
	
	@Override
	public void update_loginCnt(BpModel bpModel) {	
		loginDao.update_loginCnt(bpModel);
	}

}
