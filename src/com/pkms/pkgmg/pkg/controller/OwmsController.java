package com.pkms.pkgmg.pkg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * Owms Controller<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Controller
public class OwmsController {

	@RequestMapping(value = "/pkgmg/pkg/Owms_Ajax_Read.do")
	public String read(PkgModel pkgModel, Model model) throws Exception {
		model.addAttribute("PkgModel", pkgModel);
		return "/pkgmg/pkg/Owms_Ajax_Read";
	}

}
