package com.jc.books.controller;

import com.jc.books.commons.PageParams;
import com.jc.books.commons.Result;
import com.jc.books.model.Comment;
import com.jc.books.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: myshopping-project
 * @description: 评论控制类
 * @author: hjc
 * @create: 2021-08-24 18:23
 **/
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/queryCommentInfo")
    public Map<String, Object> queryCommentInfo(int book_id, PageParams pageParams){
        return commentService.queryCommentInfo(book_id,pageParams);
    }

    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment){
        try {
            commentService.addComment(comment);
            return Result.success("评论成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("评论跑偏了……");
    }
}
