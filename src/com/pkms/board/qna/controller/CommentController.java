package com.pkms.board.qna.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.board.qna.model.QnaModel;
import com.pkms.board.qna.service.CommentServiceIf;
import com.pkms.common.util.ResultUtil;

@Controller
public class CommentController {
	
	@Resource(name = "CommentService")
	private CommentServiceIf commentService;
	
	@RequestMapping(value = "/board/comment/Comment_Create.do")
	public String create(QnaModel qnaModel, Model model) throws Exception{
		commentService.create(qnaModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/comment/Comment_Delete.do")
	public String delete(QnaModel qnaModel, Model model) throws Exception{
		commentService.delete(qnaModel);
		return ResultUtil.handleSuccessResult();
	}

}
