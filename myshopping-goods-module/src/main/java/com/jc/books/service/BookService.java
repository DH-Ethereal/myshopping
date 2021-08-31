package com.jc.books.service;

import com.jc.books.model.Book;

import java.util.List;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-22 14:55
 **/
public interface BookService {
    public List<Book> queryBooks();

    Book queryBookById(int book_id);

    List<Book> queryBooksById(Integer[] book_ids);
}
