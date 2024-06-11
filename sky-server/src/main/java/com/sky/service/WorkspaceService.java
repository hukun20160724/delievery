package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

/**
 * ClassName: WorkspaceService
 * Package: com.sky.service
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/11/24 12:04
 * @Version 1.0
 */
public interface WorkspaceService {
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    SetmealOverViewVO getSetmealOverViewVO();

    DishOverViewVO getoverviewDishes();

    OrderOverViewVO getOrderOverView();
}
