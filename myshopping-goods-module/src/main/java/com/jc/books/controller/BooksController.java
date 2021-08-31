package com.jc.books.controller;

import com.jc.books.model.Book;
import com.jc.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-22 14:38
 **/
@RestController
@RequestMapping("/book")
public class BooksController {

    @Autowired
    private BookService bookService;

    @GetMapping("/queryBooks")
    public List<Book> queryBooks(){
        return bookService.queryBooks();
    }

    @GetMapping("/queryBookById")
    public Book queryBookById(int book_id){
        return bookService.queryBookById(book_id);
    }

    @GetMapping("/queryBooksById")
    public List<Book> queryBooksById(Integer...book_ids){
        return bookService.queryBooksById(book_ids);
    }
}
