package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by duanpengyang on 17-8-6.
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;
    public ServerResponse add (Integer userId,Shipping shipping){
        shipping.setId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount>0){
            Map result = Maps.newHashMap();
            result.put("shippingId",shipping.getId());
            return ServerResponse.CreateBySuccess("新建地址成功",result);
        }
        return ServerResponse.CreateByErrorMessage("新建地址失败");

    }

    public ServerResponse del(Integer userId, Integer shippingId){
        int resultCount = shippingMapper.deleteByUserIdAndShippingId(shippingId,userId);
        if (resultCount>0){
            return ServerResponse.CreateBySuccess("删除地址成功");
        }
        return ServerResponse.CreateByErrorMessage("删除地址失败");
    }

    public ServerResponse update(Integer userId,Shipping shipping){
        shipping.setId(userId);
        int resultCount = shippingMapper.updateByShipping(shipping);
        if (resultCount>0){
            return ServerResponse.CreateBySuccess("更新地址成功");
        }
        return ServerResponse.CreateByErrorMessage("更新地址失败");

    }
    public ServerResponse <Shipping>select(Integer userId,Integer shippingId){
        Shipping shipping = shippingMapper.selectByShippingId(shippingId,userId);
        if (shipping ==null){
            return ServerResponse.CreateByErrorMessage("无法查询到该地址");
        }
        return ServerResponse.CreateBySuccess("更新地址成功",shipping);
    }


    public ServerResponse<PageInfo>list(Integer userId,int pageNum ,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.CreateBySuccess(pageInfo);
    }

}
