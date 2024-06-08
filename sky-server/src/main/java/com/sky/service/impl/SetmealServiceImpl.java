package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
    @Autowired
    DishMapper dishMapper;

    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);// 将传入的数据先放进套餐表中
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
        return new PageResult(page1.getTotal(), page1.getResult());
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        //if we wang to start
        if (status == StatusConstant.ENABLE) {
            //check the dish in this setmeal
            List<Dish> dishes = dishMapper.getBySetmealId(id);
            dishes.forEach(dish -> {
                if (dish.getStatus() == StatusConstant.DISABLE) {//某个菜品停售
                    throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            });
        }
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        setmealMapper.update(setmeal);// use the update method
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });
        setmealMapper.deleteBath(ids);
        setmealDishMapper.deleteBathBySetmealId(ids);

    }

    @Override
    public SetmealVO getByIdWithDish(Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);

        // get the setmealdish
        List<SetmealDish> setmealIdsByDishIds = setmealDishMapper.getBySetmealId(id);
        setmealVO.setSetmealDishes(setmealIdsByDishIds);
        return setmealVO;
    }

    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();//new an object
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        // update the setmealdish
        Long setmealId = setmeal.getId();
        setmealDishMapper.deleteBySetmeaId(setmealId);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });
        setmealDishMapper.insertBatch(setmealDishes);
    }

    @Override
    public List<Setmeal> list(Setmeal setmeal) {

        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;

    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
