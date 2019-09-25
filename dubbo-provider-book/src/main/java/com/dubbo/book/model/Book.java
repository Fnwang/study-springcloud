package com.dubbo.book.model;

import java.io.Serializable;

/**
 * 书
 */
public class Book implements Serializable {

    int id;
    String bookName;
    String uathor;

    public Book(int id, String bookName, String uathor) {
        this.id = id;
        this.bookName = bookName;
        this.uathor = uathor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUathor() {
        return uathor;
    }

    public void setUathor(String uathor) {
        this.uathor = uathor;
    }


}
