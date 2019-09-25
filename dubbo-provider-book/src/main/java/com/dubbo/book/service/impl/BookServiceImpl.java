package com.dubbo.book.service.impl;

import com.dubbo.book.service.BookService;
import com.dubbo.book.model.Book;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 借书服务
 */
@Service
@Component
public class BookServiceImpl implements BookService {

    private static List<Book> bookList = new ArrayList<> ();

    //初始化数据
    static {
        bookList.add (new Book (1,"《鹿鼎记》","金庸"));
        bookList.add (new Book (2,"《沁园春》","毛泽东"));
        bookList.add (new Book (3,"《海上日出》","巴金"));
        bookList.add (new Book (4,"《天龙八部》","金庸"));
        bookList.add (new Book (5,"《本草纲目》","李时珍"));
    }

    //随机拿一本走
    @Override
    public Book getBook() {

        return getBook(this.getRanNumber ());
    }

    @Override
    public Book getBook(int id) {
        Book findBook = null;
        for (Book book:bookList) {
            if(book.getId ()==id){
                findBook = book;
                break;
            }
        }
        if(findBook ==null) {
            findBook = new Book (0, "《没找到书》", "还没人写");
        }
        return findBook;
    }

    @Override
    public List<Book> getAllBook() {
        return bookList;
    }

    /**
     * 随机
     * @return
     */
    private  int getRanNumber()
    {
        //顺便拿一本走
        Random random = new Random ();
        int key = random.nextInt ();
        return key;
    }


}
