package com.pkms.board.notice.service;

import java.util.List;

import com.pkms.board.notice.model.NoticeModel;


public interface NoticeServiceIf {

	public void create(NoticeModel noticeModel) throws Exception;

	public NoticeModel read(NoticeModel noticeModel) throws Exception;

	public List<?> readList(NoticeModel noticeModel) throws Exception;

	public void update(NoticeModel noticeModel) throws Exception;

	public void delete(NoticeModel noticeModel) throws Exception;
	
	public List<?> readList4Main(NoticeModel noticeModel) throws Exception;


}
