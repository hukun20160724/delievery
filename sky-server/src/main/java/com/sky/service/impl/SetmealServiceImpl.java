package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: SetmealServiceImpl
 * Package: com.sky.service.impl
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/7/24 13:22
 * @Version 1.0
 */
@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);// 将传入的数据先放进套餐表中
        setmealMapper.insert(setmeal);

        //get the setmeal id
        Long setmealId = setmeal.getId();
        // 得到套餐内的不同的菜品
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        // 将菜品与套餐的关系插入setmel_dish 这个表格，就就相当于绑定他们之间的关系
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);//给每个菜品设置套餐的id
        });
        //保存套餐和菜品的关联关系
        setmealDishMapper.insertBatch(setmealDishes);

    }

    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        // pagehelper
        int page = setmealPageQueryDTO.getPage();
        int pageSize = setmealPageQueryDTO.getPageSize();
        PageHelper.startPage(page, pageSize);
        // setmeal entity didnot have the categoryName, so need a new vo
        Page<SetmealVO> page1 = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page1.getTotal(),page1.getResult());
    }
}
