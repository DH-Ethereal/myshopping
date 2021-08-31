package com.jc.books.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jc.books.commons.PageParams;
import com.jc.books.mapper.CommentMapper;
import com.jc.books.model.Comment;
import com.jc.books.model.view.CommentView;
import com.jc.books.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-24 18:27
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public Map<String, Object> queryCommentInfo(int book_id, PageParams pageParams) {

        PageHelper.startPage(pageParams);
        List<CommentView> commentViewList = commentMapper.queryCommentInfo(book_id);
        PageInfo pageInfo = new PageInfo(commentViewList);

        Map<String, Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("commentList",pageInfo.getList());

        return map;
    }

    @Override
    public void addComment(Comment comment) {
        commentMapper.addComment(comment);
    }
}
