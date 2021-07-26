package com.zackyj.Mmall.service;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.model.pojo.User;

public interface IUserService {
    CommonResponse<User> login(String username, String password);
}
