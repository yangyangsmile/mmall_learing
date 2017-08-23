package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by duanpengyang on 17-8-1.
 */
@Controller
@RequestMapping("/card")
@ResponseBody
public class CartController {
    @Autowired
    private ICartService icartService;

    @RequestMapping(value ="add.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse add(HttpSession session ,Integer count ,Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null)
        {
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return icartService.add(user.getId(),productId,count);
    }

    @RequestMapping(value ="update.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse update(HttpSession session ,Integer count ,Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null)
        {
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return icartService.update(user.getId(),productId,count);
    }

    @RequestMapping(value ="delete_product.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse deleteProduct(HttpSession session ,String  productIds){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null)
        {
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return icartService.deleteProduct(user.getId(), productIds);
    }


    @RequestMapping(value ="list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse list(HttpSession session ){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null)
        {
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return icartService.list(user.getId());
    }


    //全选
    //全反选
    @RequestMapping(value ="select_all.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse <CartVo> select_all(HttpSession session ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        return icartService.selectOrUnSelect(user.getId(), Const.Cart.CHECKED,null);
    }
    @RequestMapping(value ="un_select_all.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<CartVo> un_select_all(HttpSession session ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        return icartService.selectOrUnSelect(user.getId(), Const.Cart.UNCHECKED,null);
    }
        //单独选
    //单独反选

    @RequestMapping(value ="un_select.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<CartVo> un_select(HttpSession session,Integer productId ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        return icartService.selectOrUnSelect(user.getId(), Const.Cart.UNCHECKED,productId);
    }

    @RequestMapping(value ="select.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<CartVo> select(HttpSession session, Integer productId ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        return icartService.selectOrUnSelect(user.getId(), Const.Cart.CHECKED,productId);
    }


    //查询当前购物车里边的产品数量，如果一个产品
    @RequestMapping(value ="get_Cart_Product_Count.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(HttpSession session ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.CreateBySuccess(0);
        }
        return icartService.getCartProductCount(user.getId());
    }




}
