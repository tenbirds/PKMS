package com.pkms.board.qna.service;

import java.util.List;

import com.pkms.board.qna.model.QnaModel;

public interface CommentServiceIf {
	
	public void create(QnaModel qnaModel) throws Exception;

	public QnaModel read(QnaModel qnaModel) throws Exception;

	public List<?> readList(QnaModel qnaModel) throws Exception;

	public void update(QnaModel qnaModel) throws Exception;

	public void delete(QnaModel qnaModel) throws Exception;

}
