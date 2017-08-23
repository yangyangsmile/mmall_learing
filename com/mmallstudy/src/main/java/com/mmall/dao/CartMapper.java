package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectCartByUserIdProductId(@Param("userid") Integer userid,@Param("productid") Integer productId);

    List<Cart> selectCartByUserId( Integer userId);

    int selectCartProductCheckStatusByUserId(Integer userId);

    int deleteByUserIdProdutIds(@Param("userId") Integer userId,@Param("productIdList") List<String>productIdlist);

    int checkOrUncheckedProduct(@Param("userId") Integer userId, @Param("checked") Integer checked,@Param("productid") Integer productId );

    int selectCartProductCount(Integer userId);

    List<Cart> selectCheckedCartByUserId(Integer userId);
}