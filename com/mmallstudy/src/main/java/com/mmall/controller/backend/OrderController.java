package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by duanpengyang on 17-8-7.
 */
@Controller
@RequestMapping("/order/")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping(value = "create.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse create(HttpSession session,Integer shippingId){
        User user =(User) session .getAttribute(Const.CURRENT_USER);
        if (user ==null){
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
        return iOrderService.cretaeOrder(user.getId(),shippingId);
    }

    @RequestMapping(value = "cancel.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse cancel(HttpSession session,Long  orderNo){
        User user =(User) session .getAttribute(Const.CURRENT_USER);
        if (user ==null){
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
        return iOrderService.cancel(user.getId(),orderNo);
    }


    @RequestMapping(value = "get_order_cart_product.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getOrderCartProduct(HttpSession session){
        User user =(User) session .getAttribute(Const.CURRENT_USER);
        if (user ==null){
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
        return iOrderService.getOrderCartProduct(user.getId());
    }


    @RequestMapping(value = "detail.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse detail(HttpSession session,Long orderNo){
        User user =(User) session .getAttribute(Const.CURRENT_USER);
        if (user ==null){
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
        return iOrderService.getOrderDetail(user.getId(),orderNo);
    }

    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user =(User) session .getAttribute(Const.CURRENT_USER);
        if (user ==null){
            return ServerResponse.CreateByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
        return iOrderService.getOrderList(user.getId(),pageNum, pageSize);
    }







}
