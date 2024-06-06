package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: DishFlavorMapper
 * Package: com.sky.mapper
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/6/24 15:15
 * @Version 1.0
 */
@Mapper
public interface DishFlavorMapper {

    void insertBath(List<DishFlavor> flavors);
}
