package com.pkms.board.qna.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.board.qna.model.QnaModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("QnaDao")
public class QnaDao extends IbatisAbstractDAO{

	public List<?> readList(QnaModel qnaModel) {
		return readList("qnaDao.readList", qnaModel);
	}

	public int readTotalCount(QnaModel qnaModel) {
		return (Integer) getSqlMapClientTemplate().queryForObject("qnaDao.readTotalCount", qnaModel);
	}

	public void create(QnaModel qnaModel) {
		create("qnaDao.create", qnaModel);
	}

	public QnaModel read(QnaModel qnaModel) {
		return (QnaModel) read("qnaDao.read", qnaModel);
	}

	public void update(QnaModel qnaModel) {
		update("qnaDao.update", qnaModel);
	}

	public void delete(QnaModel qnaModel) {
		delete("qnaDao.delete", qnaModel);
	}

}
