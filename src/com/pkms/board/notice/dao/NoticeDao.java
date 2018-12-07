package com.pkms.board.notice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.board.notice.model.NoticeModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("NoticeDao")
public class NoticeDao extends IbatisAbstractDAO{

	public List<?> readList(NoticeModel noticeModel) throws Exception{
		return readList("noticeDao.readList", noticeModel);
	}

	public int readTotalCount(NoticeModel noticeModel) {
		
		return  (Integer) getSqlMapClientTemplate().queryForObject("noticeDao.readTotalCount", noticeModel);
	}

	public NoticeModel read(NoticeModel noticeModel) {
		return (NoticeModel) read("noticeDao.read", noticeModel);
	}

	public void create(NoticeModel noticeModel) {
		create("noticeDao.create", noticeModel);
	}

	public void update(NoticeModel noticeModel) {
		update("noticeDao.update", noticeModel);
	}

	public void delete(NoticeModel noticeModel) {
		delete("noticeDao.delete", noticeModel);
	}

	public List<?> readList4Main(NoticeModel noticeModel) {
		return readList("noticeDao.readList4Main", noticeModel);
	}
	
	
	
}
