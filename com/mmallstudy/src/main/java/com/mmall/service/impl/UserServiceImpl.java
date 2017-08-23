package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


/**
 * Created by duanpengyang on 17-7-25.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {


    @Autowired
    private UserMapper userMapper;

    public ServerResponse<User> login(String userName, String passWord) {
        if (userMapper.chackUser(userName)==1) {
            return ServerResponse.CreateByErrorMessage("用户名不存在");
        }
        User user = (User) userMapper.checkUserByPasswd(userName, passWord);
        if (user== null) {
            return ServerResponse.CreateByErrorMessage("密码错误");

        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse  .CreateBySuccess("登陆成功",user);
    }

    public ServerResponse<String> register(User user){


        ServerResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if (!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(),Const.EMAILI);
        if (!validResponse.isSuccess()){
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        String md5Password = MD5Util.MD5EncodeUtf8(user.getPassword());
        user.setPassword(md5Password);
        int result = userMapper.insert(user);
        if (result >0){
            return ServerResponse.CreateBySuccessMessage("创建用户成功");
        }
        return ServerResponse.CreateByErrorMessage("创建用户失败");
    }

    public ServerResponse<String> checkValid(String str , String type) {
        if (StringUtils.isBlank(type)) {

            if (Const.USERNAME.equals(type)) {           //开始校验
                if (userMapper.chackUser(str) > 0) {
                    return ServerResponse.CreateByErrorMessage("用户名已经存在");
                }
            }
            if (Const.EMAILI.equals(type)) {
                if (userMapper.chackEamil(str) > 0) {
                    return ServerResponse.CreateByErrorMessage("EMAIL已经存在");
                }
            }
        }else{
                return ServerResponse.CreateByErrorMessage("参数错误");
            }
            return ServerResponse.CreateBySuccessMessage("校验成功");
    }

    public ServerResponse selectQuestion(String username){
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            return ServerResponse.CreateByErrorMessage("用户不存在");
        }
         String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)){
            return ServerResponse.CreateBySuccess(question);
        }
        return ServerResponse.CreateByErrorMessage("找回密码的问题是空的");
    }
    public ServerResponse<String> checkAnswer(String username,String question ,String answer){
        int resultCount = userMapper.checkAnwer(username,question,answer);
        if (resultCount>0){
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.CreateBySuccess(forgetToken);
        }
        return ServerResponse.CreateByErrorMessage("问题答案错误");
    }

    public ServerResponse<String>forgetResetPassword(String username,String passwordNew,String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.CreateByErrorMessage("参数错误，TOKEN 需要传递");
        }
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.CreateByErrorMessage("用户不存在");
        }

        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.CreateByErrorMessage("token 无效或者过期");
        }
        if (StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);
            if (rowCount > 0) {
                return ServerResponse.CreateBySuccessMessage("更改密码成功");
            }
        } else {
            return ServerResponse.CreateByErrorMessage("token 错误");
        }
        return ServerResponse.CreateByErrorMessage("修改密码失败");
    }


    public ServerResponse<String> resetPassword(String password,String passwordNew,User user){
        //防止横向越权
        int resultCount  = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(password),user.getId());
        if (resultCount == 0) {
            return ServerResponse.CreateByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updatecount = userMapper.updateByPrimaryKeySelective(user);
        if(updatecount>0){
            return ServerResponse.CreateBySuccessMessage("密码更新成功");
        }
        return ServerResponse.CreateByErrorMessage("密码更新失败");
    }

    public ServerResponse <User> updateInfoMation(User user){
        //username 不能被更新
        //email校验
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if (resultCount>0){
            return ServerResponse.CreateByErrorMessage("EMAIL已经存在");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount>0){
            return ServerResponse.CreateBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.CreateByErrorMessage("更新个人信息失败");
    }

    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if (user ==null){
            return ServerResponse.CreateByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.CreateBySuccess(user);
    }



    //backend

    public ServerResponse checkAdminRole(User user){
        if (user != null && user.getRole().intValue()==Const.Role.ROLE_ADMIN){
            return ServerResponse.CreateBySuccess();
        }
        return ServerResponse.CreateByError();
    }


}

