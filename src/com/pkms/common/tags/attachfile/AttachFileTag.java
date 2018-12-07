package com.pkms.common.tags.attachfile;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.poi.hwpf.model.PropertyNode.EndComparator;

import com.pkms.common.attachfile.model.AttachFileModel;

/**
 * 
 * @author : skywarker
 * @Date : 2012. 4. 02.
 * 
 */
public class AttachFileTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private AttachFileModel attachFileModel;

	private String name;

	private String mode;

	private String size;

	public int doEndTag() throws JspException {

		try {

			JspWriter out = pageContext.getOut();
			
			String size = "".equals(this.size) ? "15" : this.size;

			StringBuffer sb = new StringBuffer();

			if ("update".equals(this.mode) && attachFileModel != null) {
				sb.append("<div id=\"" + name + "_div_file" + "\" style=\"display:none;\">");
				sb.append("<input type=\"file\" name=\"" + name + "\" size=\"" + size + "\" onchange=\"javascript:fn_upload(this)\" />");
				sb.append("</div>");
				sb.append("<div id=\"" + name + "_div_delete" + "\">");
				sb.append("<a href=\"#\" onclick=\"javascript:downloadFile('" + attachFileModel.getFile_name() + "','" + attachFileModel.getFile_org_name() +  "','" + attachFileModel.getFile_path() + "'); return false;\">" + attachFileModel.getFile_org_name() + "</a>");
				sb.append("<input type=\"hidden\" id=\"" + name + "_delete" + "\" name=\"" + name + "_delete" + "\" />");
//				sb.append("<input type=\"button\" id=\"" + name + "_button" + "\" name=\"" + name + "_button" + "\" onClick=\"javascript:deleteFile('" + name + "');\" value=\"삭제\">");
				sb.append("<input type=\"button\" id=\"" + name + "_button" + "\" name=\"" + name + "_button" + "\" onClick=\"javascript:deleteFile('" + name + "');\" class=\"btn_del\" title=\"삭제\">");
				
				sb.append("</div>");
				
			} else if ("view".equals(this.mode) && attachFileModel != null) {
				sb.append("<a href=\"#\" onclick=\"javascript:downloadFile('" + attachFileModel.getFile_name() + "','" + attachFileModel.getFile_org_name() + "','" + attachFileModel.getFile_path() + "'); return false;\">" + attachFileModel.getFile_org_name() + "</a>");

			} else if ("view".equals(this.mode) && attachFileModel == null) {
			
			} else if ("mobile".equals(this.mode) && attachFileModel != null){
				sb.append("<div id=\"" + name + "_div_delete" + "\">");
				sb.append("<a href=\"#\" onclick=\"javascript:downloadFile('" + attachFileModel.getFile_name() + "','" + attachFileModel.getFile_org_name() +  "','" + attachFileModel.getFile_path() + "'); return false;\">" + attachFileModel.getFile_org_name() + "</a>");
				sb.append("</div>");
			} else {
				sb.append("<input type=\"file\" name=\"" + name + "\" size=\"" + size + "\" onchange=\"javascript:fn_upload(this)\" />");
				
			}

			out.println(sb);

			return EVAL_PAGE;

		} catch (IOException e) {
			throw new JspException();
		}
	}

	public void setAttachFileModel(AttachFileModel attachFileModel) {
		this.attachFileModel = attachFileModel;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public void setSize(String size) {
		this.size = size;
	}

}
