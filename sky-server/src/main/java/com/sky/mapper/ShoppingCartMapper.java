package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * ClassName: ShoppingCartMapper
 * Package: com.sky.mapper
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/8/24 12:16
 * @Version 1.0
 */
@Mapper
public interface ShoppingCartMapper {


    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * update item number
     * @param shoppingCart
     */
    @Update("update shopping_cart set number=#{number} where id=#{id}")
    void updateById(ShoppingCart shoppingCart);

    /**
     * insert into shopping cart
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time) " +
            "VALUES (#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

    @Delete("delete from shopping_cart where user_id=#{userId}")
    void clean(ShoppingCart shoppingCart);

    @Delete("delete from shopping_cart where dish_id=#{dishId}")
    void deletBydishId(Long dishId);
    @Delete("delete from shopping_cart where setmeal_id=#{setmealId}")
    void deletBydsetmealId(Long setmealId);


    void insertBatches(List<ShoppingCart> shoppingCartList);
}
