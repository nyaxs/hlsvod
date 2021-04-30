package com.liuyh.hlsvod;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HlsvodApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void fileTest(){
        String p1 = "C:\\Users\\liuyo\\Desktop\\test111\\live\\22\\";
        String p2 = "C:\\Users\\liuyo\\Desktop\\test111\\live\\dest";

        FileUtil.copy(p1,p2,true);
    }
}
