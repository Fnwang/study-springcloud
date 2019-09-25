package com.dubbo.book.service;

import com.dubbo.book.model.Book;

import java.util.List;

/**
 * 对外提供服务：借书
 */
public interface BookService {

    /**
     * 随机借出一本书
     * @return
     */
    Book getBook();

    /**
     * 指定借出一本书
     * @param args
     * @return
     */
    Book getBook(int args);

    /**
     * 查询所有
     * @return
     */
    List<Book> getAllBook();

}
