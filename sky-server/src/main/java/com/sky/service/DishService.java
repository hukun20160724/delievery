package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * ClassName: DishService
 * Package: com.sky.service
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/6/24 13:47
 * @Version 1.0
 */
public interface DishService {
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);



    void saveWithFlavor(DishDTO dishDTO);

    void deleteBatch(List<Long> ids);

    DishVO getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDTO dishDTO);

    void updateStatus(Long id, Integer status);

    List<Dish> getListBycategoryId(Long categoryId);
}
