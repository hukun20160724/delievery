package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * ClassName: ShoppingCartService
 * Package: com.sky.service
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/8/24 12:05
 * @Version 1.0
 */
public interface ShoppingCartService {


    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> list();
}
