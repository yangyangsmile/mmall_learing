package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by duanpengyang on 17-7-25.
 */
public interface IUserService {
    ServerResponse<User> login(String userName, String passWord);
    ServerResponse<String> register(User user);
    ServerResponse<String> checkValid(String str , String type);
    ServerResponse selectQuestion(String username);
    ServerResponse<String> checkAnswer(String username,String question ,String answer);
    ServerResponse<String>forgetResetPassword(String username,String passwordNew,String forgetToken);
    ServerResponse<String> resetPassword(String password,String passwordNew,User user);
    ServerResponse <User> updateInfoMation(User user);
    ServerResponse<User> getInformation(Integer userId);
    ServerResponse checkAdminRole(User user);
}
