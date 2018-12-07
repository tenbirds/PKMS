package com.pkms.board.qna.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.board.qna.model.QnaModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("CommentDao")
public class CommentDao extends IbatisAbstractDAO{

	public List<?> readList(QnaModel qnaModel) {
		return readList("CommentDao.readList", qnaModel);
	}

	public void create(QnaModel qnaModel) {
		create("Comment.create", qnaModel);
	}

	public void delete(QnaModel qnaModel) {
		delete("Comment.delete", qnaModel);
	}

}
