package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by duanpengyang on 17-8-2.
 */
@Controller
@RequestMapping("/manage/category")

public class CategoryManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,String categoryName ,@RequestParam(value ="parentId" ,defaultValue ="0") int parentId){
        User user  = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        //校验是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iCategoryService.addCategory(categoryName,parentId);
        }
        return ServerResponse.CreateByErrorMessage("无权限操作，需要管理员权限");
    }


    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session ,Integer categoryId,String categoryName){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){

            // TODO: 17-8-2
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        }
        return ServerResponse.CreateByErrorMessage("无权限操作，需要管理员权限");
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildernParallelCategory(HttpSession session ,@RequestParam( value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //查询子节点信息
            return iCategoryService.getChildenParallelCategory(categoryId);
        }
        return ServerResponse.CreateByErrorMessage("无权限操作，需要管理员权限");
    }


    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //查询当前节点ID 和递归子节点ID
            return iCategoryService.getChildenParallelCategory(categoryId);
//            return iCategoryService.getChildenParallelCategory(categoryId);
        }
        return ServerResponse.CreateByErrorMessage("无权限操作，需要管理员权限");
    }
}
