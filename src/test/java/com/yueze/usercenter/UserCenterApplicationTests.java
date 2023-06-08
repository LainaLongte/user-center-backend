package com.yueze.usercenter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yueze.usercenter.model.domain.User;
import com.yueze.usercenter.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
class UserCenterApplicationTests {

    @Resource
    private UserService userService;

    /**
     * 校验密码
     */
    @Test
    void testDigest(){
        String newPassword = DigestUtils.md5DigestAsHex(("abcd" + "mypassword").getBytes());// 使用算法加密
        System.out.println(newPassword);
    }

    @Test
    void testCRUD() {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.like("userAccount", "man");
        List<User> list = userService.list(qw);
        System.out.println(list);
    }

}
