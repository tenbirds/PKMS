package com.pkms.board.qna.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.board.qna.dao.CommentDao;
import com.pkms.board.qna.model.QnaModel;

@Service("CommentService")
public class CommentService implements CommentServiceIf{
	
	@Resource(name = "CommentDao")
	private CommentDao commentDao;

	@Override
	public void create(QnaModel qnaModel) throws Exception {
		this.commentDao.create(qnaModel);
	}

	@Override
	public QnaModel read(QnaModel qnaModel) throws Exception {
		return null;
	}

	@Override
	public List<?> readList(QnaModel qnaModel) throws Exception {
		/*
		 * DAO에서 정보 조회
		 */
		List<?> resultList = commentDao.readList(qnaModel);
		
		return resultList;
	}

	@Override
	public void update(QnaModel qnaModel) throws Exception {
		
	}

	@Override
	public void delete(QnaModel qnaModel) throws Exception {
		this.commentDao.delete(qnaModel);
	}

}
