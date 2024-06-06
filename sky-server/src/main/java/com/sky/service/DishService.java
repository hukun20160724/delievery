package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

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
}
