package com.jc.books.mapper;

import com.jc.books.model.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-22 14:53
 **/
@Repository
public interface BookMapper {

    @Select("select * from tbl_book")
    public List<Book> queryBooks();

    @Select("select * from tbl_book where book_id = #{book_id}")
    Book queryBookById(int book_id);

    List<Book> queryBooksById(@Param("book_ids") Integer[] book_ids);
}
