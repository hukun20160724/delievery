package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

/**
 * ClassName: SetmealService
 * Package: com.sky.service
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/7/24 13:21
 * @Version 1.0
 */
public interface SetmealService {
    void saveWithDish(SetmealDTO setmealDTO);

    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);
}
