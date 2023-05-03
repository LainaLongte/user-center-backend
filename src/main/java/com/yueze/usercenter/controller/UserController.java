package com.yueze.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yueze.usercenter.model.domain.User;
import com.yueze.usercenter.model.domain.request.UserLoginRequest;
import com.yueze.usercenter.model.domain.request.UserRegisterRequest;
import com.yueze.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yueze.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.yueze.usercenter.contant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // 校验请求参数
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        // String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }

    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request) {
        if (isAdmin(request)) {
//            throw new BusinessException(ErrorCode.NO_AUTH);
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public Boolean deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (isAdmin(request)) {
//            throw new BusinessException(ErrorCode.NO_AUTH);
            return false;
        }
        if (id <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
            return false;
        }
        return userService.removeById(id);
    }

    /**
     * 是否为管理员
     *
     * @param request 请求参数
     * @return 是否成功
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user == null || user.getUserRole() != ADMIN_ROLE;
    }
}
