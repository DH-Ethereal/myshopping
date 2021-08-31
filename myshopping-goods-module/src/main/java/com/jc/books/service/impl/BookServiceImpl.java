package com.jc.books.service.impl;

import com.jc.books.model.Book;
import com.jc.books.mapper.BookMapper;
import com.jc.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-22 14:55
 **/
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> queryBooks() {
        return bookMapper.queryBooks();
    }

    @Override
    public Book queryBookById(int book_id) {
        return bookMapper.queryBookById(book_id);
    }

    @Override
    public List<Book> queryBooksById(Integer[] book_ids) {
        return bookMapper.queryBooksById(book_ids);
    }
}
