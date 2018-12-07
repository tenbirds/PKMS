package com.pkms.batch.sktdb.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.batch.sktdb.service.Starter;
import com.pkms.org.service.OrgServiceIf;
import com.wings.properties.service.PropertyServiceIf;

@Controller
public class SktdbController {

	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;

	@Resource(name = "OrgService")
	protected OrgServiceIf orgService;
	
	@RequestMapping(value = "/SKT_DB_DEPT_Create.do")
	public String create4Dept(HttpServletRequest request, Model model) throws Exception {
		Starter starter = new Starter(
				propertyService.getString("SKTDB.FROM.CON"),
				propertyService.getString("SKTDB.FROM.ID"),
				propertyService.getString("SKTDB.FROM.PW"),
				propertyService.getString("SKTDB.TO.CON"),
				propertyService.getString("SKTDB.TO.ID"),
				propertyService.getString("SKTDB.TO.PW")
				);
		starter.create4Dept();
		
		orgService.initOrg();
		return "";
	}

	@RequestMapping(value = "/SKT_DB_PERSON_Create.do")
	public String create4Person(HttpServletRequest request, Model model) throws Exception {
		Starter starter = new Starter(
				propertyService.getString("SKTDB.FROM.CON"),
				propertyService.getString("SKTDB.FROM.ID"),
				propertyService.getString("SKTDB.FROM.PW"),
				propertyService.getString("SKTDB.TO.CON"),
				propertyService.getString("SKTDB.TO.ID"),
				propertyService.getString("SKTDB.TO.PW")
				);
		starter.create4Person();

		orgService.initOrg();
		return "";
	}
}
