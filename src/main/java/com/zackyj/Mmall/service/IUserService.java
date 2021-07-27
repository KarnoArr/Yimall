package com.zackyj.Mmall.service;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.model.pojo.User;

public interface IUserService {
    CommonResponse<User> login(String username, String password);

    CommonResponse<User> register(User user);

    CommonResponse<String> checkValid(String str, String type);

    CommonResponse<String> getForgetQuestion(String username);

    CommonResponse<String> checkAnswer(String username, String question, String answer);

    CommonResponse<String> forgetResetPwd(String username, String newPwd, String forgetToken);

    CommonResponse<String> resetPassword(String oldPwd, String newPwd, User user);

    CommonResponse<User> updateUserInfo(User user);
}
