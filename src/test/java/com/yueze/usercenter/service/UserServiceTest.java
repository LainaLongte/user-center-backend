package com.yueze.usercenter.service;
import java.util.Date;

import com.yueze.usercenter.mapper.UserMapper;
import com.yueze.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {

    @Resource
    UserService userService;

    /**
     * CRUD测试
     */
    @Test
    void testAddUser(){
        User user = new User();
        user.setUsername("queen");
        user.setUserAccount("queen");
        user.setAvatarUrl("https://images.unsplash.com/photo-1599572741422-03c2e858184a?ixid=Mnw4OTgyNHwwfDF8c2VhcmNofDE0fHwlRTUlOEElQTglRTclODklQTl8ZW58MHx8fHwxNjgyNDI2ODM2&ixlib=rb-4.0.3&w=750&dpi=2");
        user.setGender(0);
        user.setUserPassword("12345678");
        user.setPhone("110");
        user.setEmail("110@");

        boolean result = userService.save(user);
        System.out.println(user.getId());
        assertTrue(result);
    }

    /**
     *用户注册测试
     */
    @Test
    void userRegister() {
        // 非空
        String userAccount = "superman";
        String userPassword = "";
        String checkPassword = "";
        String planetCode = "20564";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        // 长度
        userPassword = "123456";
        checkPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        // 两个密码不一致
        userPassword = "12345678";
        checkPassword = "12345677";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        assertTrue(result > 0);
        // 账户不包含特殊字符
        userAccount = "yue ze";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        // 账户不能重复
        userAccount = "yueze";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
    }

}