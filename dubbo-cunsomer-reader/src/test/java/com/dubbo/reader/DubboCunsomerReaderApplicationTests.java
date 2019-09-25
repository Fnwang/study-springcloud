package com.dubbo.reader;

import com.dubbo.reader.service.ReaderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DubboCunsomerReaderApplicationTests {

    @Autowired
    ReaderService readerService;

    @Test
    public void testgetBook() {


    }

}
