package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.util.ArrayList;
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

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        // get page and page size
        int page = dishPageQueryDTO.getPage();
        int pageSize = dishPageQueryDTO.getPageSize();
        PageHelper.startPage(page, pageSize);// use the default method

        Page<DishVO> dishes = dishMapper.pageQuery(dishPageQueryDTO);
        long total = dishes.getTotal();
        return new PageResult(total, dishes);
    }

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        // add new data to dish table
        dishMapper.insert(dish);
        // add into flavor table
        //get the dish id form the dish table
        Long id = dish.getId();
        List<DishFlavor> flavors =
                dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            // loop for each and set the dish id
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(id);
            });
            dishFlavorMapper.insertBath(flavors);
        }

    }

    @Override
    public void deleteBatch(List<Long> ids) {
        //check the dish status
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            Integer status = dish.getStatus();
            if (status == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //check dish is bind with setmeal
        List<Long> setmealIdsByDishIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIdsByDishIds!=null && setmealIdsByDishIds.size()>0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //delete from dish,flavor
        /*for (Long id : ids) {
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteByDishId(id);
        }*/
        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByDishIds(ids);

    }

    @Override
    public DishVO getByIdWithFlavor(Long id) {
        // get dish from dish table
        Dish dish = dishMapper.getById(id);
        DishVO dishVO=new DishVO();
        // get flavor from flavor table
        List<DishFlavor> dishFlavors=dishFlavorMapper.getByDishId(id);
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        // handle thev dish table
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        // flavor
        // delete first,then update
        dishFlavorMapper.deleteByDishId(dishDTO.getId());//delete

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            // loop for each and set the dish id
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            dishFlavorMapper.insertBath(flavors);
        }
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Dish dish = dishMapper.getById(id);// get teh dish
        dish.setStatus(status);
        dishMapper.update(dish);
    }

    @Override
    public List<Dish> getListBycategoryId(Long categoryId) {
        Dish dish = new Dish();
        dish.setStatus(StatusConstant.ENABLE);
        dish.setCategoryId(categoryId);
        return dishMapper.getListBycategoryId(dish);
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {

            List<Dish> dishList = dishMapper.getListBycategoryId(dish);// 根据类id得到的菜品可能有很多个
            // 用一个list来接收
            List<DishVO> dishVOList = new ArrayList<>();

            for (Dish d : dishList) {
                DishVO dishVO = new DishVO();//新建一个菜品的vo对象
                BeanUtils.copyProperties(d,dishVO);//把查到的菜品copy给新的对象

                //根据菜品id查询对应的口味
                List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());//根据菜品id查询是否有口味

                dishVO.setFlavors(flavors);//有的话，就设置给dishvo
                dishVOList.add(dishVO);//把dishvo追加到list后面
            }

            return dishVOList;
        }


}
