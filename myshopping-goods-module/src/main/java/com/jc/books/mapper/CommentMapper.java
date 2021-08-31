package com.jc.books.mapper;

import com.jc.books.model.Comment;
import com.jc.books.model.view.CommentView;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-24 18:34
 **/
@Repository
public interface CommentMapper {

    @Select("SELECT comm.comment_id,comm.book_id,comm.comment_message,comm.comment_time,user.user_name FROM tbl_comment comm INNER JOIN tbl_user user ON comm.user_id = user.user_id WHERE comm.book_id=#{book_id}")
    List<CommentView> queryCommentInfo(int book_id);

    @Insert("insert into tbl_comment values(default,#{user_id},#{book_id},#{comment_message},now())")
    void addComment(Comment comment);
}
