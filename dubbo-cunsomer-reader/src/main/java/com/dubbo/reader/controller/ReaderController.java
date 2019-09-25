package com.dubbo.reader.controller;

import com.dubbo.reader.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReaderController extends AbsController {

    @Autowired
    ReaderService readerService;

    /**
     * 找一本书
     * @param id
     * @return
     */
    @GetMapping("/read/{id}")
    public String read(@PathVariable(value = "id") int id) {
        int i = 10/id;
        return super.getData (readerService.readBook (id));
    }

    /**
     * 查询所有书
     * @return
     */
    @GetMapping("/list")
    public String List() {
        return super.getData (objects -> {
            //查询所有
            return  readerService.getBookList ();
        });
    }
}
