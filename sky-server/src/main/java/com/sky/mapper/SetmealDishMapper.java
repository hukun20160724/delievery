package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * ClassName: SetmealDishMapper
 * Package: com.sky.mapper
 * Description:
 * SetmealDishMapper
 * @Author Kun Hu
 * @Create 6/6/24 22:39
 * @Version 1.0
 */
@Mapper
public interface SetmealDishMapper {

    List<Long> getSetmealIdsByDishIds(List<Long> ids);


    void insertBatch(List<SetmealDish> setmealDishes);

    void deleteBathBySetmealId(List<Long> ids);


    @Select("select * from setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> getBySetmealId(Long id);

    @Delete("delete from setmeal_dish where setmeal_id=#{setmealId}")
    void deleteBySetmeaId(Long setmealId);
}
