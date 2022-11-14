package com.ch.mail.thirdparty;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThirdPartyApplicationTests {

    @Autowired
    OSS ossClient;
    @Test
    public void contextLoads() {
    }

    @Test
    public void testUpload() throws FileNotFoundException {
        InputStream inputStreamn = new FileInputStream("H:\\日常\\壁纸\\wallhaven-zm1lwy.png");
        ossClient.putObject("ch-mail","2.png",inputStreamn);
    }
}
