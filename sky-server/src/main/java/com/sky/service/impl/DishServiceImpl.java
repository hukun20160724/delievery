package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.util.List;

/**
 * ClassName: DishServiceImpl
 * Package: com.sky.service.impl
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/6/24 13:47
 * @Version 1.0
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        // get page and page size
        int page = dishPageQueryDTO.getPage();
        int pageSize = dishPageQueryDTO.getPageSize();
        PageHelper.startPage(page,pageSize);// use the default method
       Page<Dish> dishes= dishMapper.pageQuery(dishPageQueryDTO);
        long total = dishes.getTotal();
        return new PageResult(total,dishes);
    }

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        // add new data to dish table
        dishMapper.insert(dish);
        // add into flavor table
        //get the dish id form the dish table
        Long id = dish.getId();
        List<DishFlavor> flavors =
                dishDTO.getFlavors();
        if (flavors!=null && flavors.size()>0){
            // loop for each and set the dish id
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(id);
            });
            dishFlavorMapper.insertBath(flavors);
        }

    }

}
