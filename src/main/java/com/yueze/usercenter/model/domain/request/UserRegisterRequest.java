package com.yueze.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author yueze
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 4406432347037674443L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

}