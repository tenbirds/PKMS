package com.pkms.common.util;

import java.util.ArrayList;
import java.util.List;

import com.pkms.common.pkmscode.model.PkmsCodeModel;
import com.wings.model.CodeModel;

public class CodeUtil {

	public static List<CodeModel> convertCodeModel(String[][] codeArray) {

		if (codeArray == null) {
			return null;
		}

		ArrayList<CodeModel> items = new ArrayList<CodeModel>();

		for (String[] code : codeArray) {
			if (code == null || code.length != 2) {
				return null;
			}
			CodeModel codeModel = new CodeModel();
			codeModel.setCode(code[0]);
			codeModel.setCodeName(code[1]);
			items.add(codeModel);
		}
		return items;
	}

	public static List<CodeModel> convertCodeModel(List<?> codeList) {

		if (codeList == null) {
			return null;
		}

		ArrayList<CodeModel> items = new ArrayList<CodeModel>();

		for (Object object : codeList) {
			if (!(object instanceof PkmsCodeModel)) {
				return null;
			}
			PkmsCodeModel pkmsCodeModel = (PkmsCodeModel) object;
			CodeModel codeModel = new CodeModel();
			codeModel.setCode(pkmsCodeModel.getCommon_code());
			codeModel.setCodeName(pkmsCodeModel.getName());
			items.add(codeModel);
		}
		return items;
	}
}
