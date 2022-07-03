package com.caleb.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.caleb.demo.service.JtaService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtomikosApplication.class)
public class DemoApplicationTests {

    @Autowired
    private JtaService jtaService;

    @Test
    public void contextLoads() {
//        jtaService.testJTA();
        jtaService.testJTABatch();
    }


}
