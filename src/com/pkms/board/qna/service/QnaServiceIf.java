package com.pkms.board.qna.service;

import java.util.List;

import com.pkms.board.qna.model.QnaModel;

public interface QnaServiceIf {

	public void create(QnaModel qandaModel) throws Exception;

	public Object[] read(QnaModel qandaModel) throws Exception;

	public List<?> readList(QnaModel qandaModel) throws Exception;

	public void update(QnaModel qandaModel) throws Exception;

	public void delete(QnaModel qandaModel) throws Exception;


}
