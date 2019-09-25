package com.dubbo.reader.service;

import com.dubbo.book.model.Book;
import com.dubbo.book.service.BookService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 读者服务
 */
@Service
public class ReaderService  {

    @Reference
    BookService bookService;

    /**
     * 返回一本书
     * @param id
     * @return
     */
    public Book readBook(int id)  {

       return id<0?bookService.getBook () : bookService.getBook (id);

    }

    /**
     * 返回所有的书籍
     * @return
     */
    public List<Book> getBookList() {
        return bookService.getAllBook ();
    }
}
