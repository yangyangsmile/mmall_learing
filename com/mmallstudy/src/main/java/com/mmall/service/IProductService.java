package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

/**
 * Created by duanpengyang on 17-8-3.
 */
public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);
    ServerResponse setSaleStatus(Integer productId, Integer status);
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);
    ServerResponse getProductList(int pageNum ,int pageSize);
    ServerResponse<PageInfo>serachProduct(String productName, Integer productId, int pageNum , int pageSize);
    ServerResponse<ProductDetailVo>getProductDetail(Integer productId);
    ServerResponse<PageInfo>getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);
}
