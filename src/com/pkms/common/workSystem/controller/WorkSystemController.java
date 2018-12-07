package com.pkms.common.workSystem.controller;

import java.security.MessageDigest;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.common.workSystem.service.WorkSystemServiceIf;
import com.pkms.common.workSystem.model.WorkSystemModel;


@Controller
public class WorkSystemController {
	
	@Resource(name = "WorkSystemService")
	private WorkSystemServiceIf workSystemService;
	
	@Resource(name = "AttachFileService")
	protected AttachFileServiceIf attachFileService;
	
	public static MessageDigest md;
	
	@RequestMapping(value = "/common/workSystem/Iwcs_view.do")
	public String create(WorkSystemModel workSystemModel, Model model) throws Exception {		
		WorkSystemModel wsModel = null;
		wsModel = workSystemService.read_IwcsViewSeq(workSystemModel);
		
		String id = this.sha512(workSystemModel.getSession_user_id());
		String seq = String.valueOf(wsModel.getSeq());
		return ResultUtil.handleSuccessResultParam(model, seq, id);
	}
	
	
	//sha512 변환
	private String sha512(String userPassword) throws Exception {
		String tempPassword = "";
		
		try {
            //SHA-256알고리즘 사용
            if(md==null) md = MessageDigest.getInstance("SHA-512");

            //문자열로 받아들임
            md.update(userPassword.getBytes());
            byte[] mb = md.digest();

            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));

                while (s.length() < 2) {
                    s = "0" + s;

                }
                s = s.substring(s.length() - 2);
                tempPassword += s;

            }
            return tempPassword;

        } catch (Exception e) {
        	throw new Exception("error.biz.암호화 변환 에러입니다.");
        }
	}
	
}