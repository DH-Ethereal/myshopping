package com.jc.books.service;

import com.jc.books.commons.PageParams;
import com.jc.books.model.Comment;

import java.util.Map;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-24 18:26
 **/
public interface CommentService {
    Map<String, Object> queryCommentInfo(int book_id, PageParams pageParams);

    void addComment(Comment comment);
}
