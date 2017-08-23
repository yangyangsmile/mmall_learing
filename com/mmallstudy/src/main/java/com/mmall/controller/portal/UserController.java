package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by duanpengyang on 17-7-25.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {



    @Autowired
    private IUserService iUserService;
    @RequestMapping(value ="login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String userName, String passWord, HttpSession session ){
       ServerResponse<User> response =  iUserService.login(userName,passWord);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER, response.getDate());
        }
        return response;
    }


    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.CreateBySuccess();
    }

    @RequestMapping(value ="register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }
    @RequestMapping(value = "checkvalid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type){
    return iUserService.checkValid(str,type) ;
    }

    @RequestMapping(value ="get_user_info.do" , method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.CreateBySuccess(user);
        }
        return ServerResponse.CreateByErrorMessage("用户未登陆，无法获取当前用户的信息");
    }

    @RequestMapping(value ="foget_get_question" ,method= RequestMethod.GET )
    @ResponseBody
    public ServerResponse<String> fogetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }



    @RequestMapping(value = "forget_check_answer",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse <String> checkAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username, question,answer);
    }

    @RequestMapping(value ="forget_reset_password.do" ,method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetRestPassword(String username,String password,String token){
        return iUserService.forgetResetPassword(username,password,token);
    }

    @RequestMapping(value ="reset_password.do" ,method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String>resetPasswore(HttpSession session , String password,String passwordNew){
            User user = (User) session.getAttribute(Const.CURRENT_USER);
            if(user==null){
                return ServerResponse.CreateByErrorMessage("用户未登陆");
            }
            return iUserService.resetPassword(password,passwordNew,user);
    }



    @RequestMapping(value ="update_information.do" ,method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse <User> update_infomation(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if( currentUser==null){
            return ServerResponse.CreateByErrorMessage("用户未登陆");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInfoMation(user);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getDate());
        }
        return response;
    }
    @RequestMapping(value ="get_information.do" ,method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> get_information(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登陆，需要强制登陆STRUCT= 10");
        }
        return iUserService.getInformation(user.getId());

    }



}
