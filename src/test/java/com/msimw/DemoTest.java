package com.msimw;

import com.msimw.retrofit2x.test.IPushHttpApi;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by msimw on 17-9-12.
 */
//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-httpclient-retrofit2.xml")
public class DemoTest {

    @Autowired
    private IPushHttpApi<String> pushHttpApi;


    @Test
    public void oneTest() throws IOException {
        String push = this.pushHttpApi.push("127.0.0.1");
        System.out.println(push);
    }


}
