package com.zz;

import com.zz.sys.pojo.BaoWen;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.time.LocalDateTime;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        DateFormat format = DateFormat.getDateTimeInstance();
        System.out.println(format);
        BaoWen baoWen = new BaoWen();
    }

}
