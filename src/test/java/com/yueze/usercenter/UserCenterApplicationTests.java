package com.yueze.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class UserCenterApplicationTests {
    /**
     * 校验密码
     */
    @Test
    void testDigest(){
        String newPassword = DigestUtils.md5DigestAsHex(("abcd" + "mypassword").getBytes());// 使用算法加密
        System.out.println(newPassword);
    }

    @Test
    void contextLoads() {
    }

}
