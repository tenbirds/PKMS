package com.pkms.board.faq.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.board.faq.model.FaqModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("FaqDao")
public class FaqDao extends IbatisAbstractDAO {
	public List<?> readList(FaqModel faqModel){
		return readList("faqDao.readList", faqModel);
	}
	
	public int readTotalCount(FaqModel faqModel) {
		return (Integer) getSqlMapClientTemplate().queryForObject("faqDao.readTotalCount", faqModel);
	}

	public void create(FaqModel faqModel) {
		create("faqDao.create", faqModel);
	}

	public FaqModel read(FaqModel faqModel) {
		return (FaqModel) read("faqDao.read", faqModel);
	}

	public void update(FaqModel faqModel) {
		update("faqDao.update", faqModel);
	}

	public void delete(FaqModel faqModel) {
		delete("faqDao.delete", faqModel);
	}
}
