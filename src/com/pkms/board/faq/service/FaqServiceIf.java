package com.pkms.board.faq.service;

import java.util.List;

import com.pkms.board.faq.model.FaqModel;

public interface FaqServiceIf {
	
	public void create(FaqModel faqModel) throws Exception;

	public FaqModel read(FaqModel faqModel) throws Exception;

	public List<?> readList(FaqModel faqModel) throws Exception;

	public void update(FaqModel faqModel) throws Exception;

	public void delete(FaqModel faqModel) throws Exception;
	
}
