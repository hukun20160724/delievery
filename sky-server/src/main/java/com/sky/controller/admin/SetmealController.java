package com.sky.controller.admin;

import com.google.j2objc.annotations.AutoreleasePool;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: SetmealController
 * Package: com.sky.controller.admin
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/7/24 13:20
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
@Slf4j
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    @ApiOperation("add new setmeal...")
    @PostMapping()
    public Result save(@RequestBody SetmealDTO setmealDTO){
        setmealService.saveWithDish(setmealDTO);
        return Result.success();

    }
    // GET http://localhost/api/setmeal/page?page=1&pageSize=10&status=
    @ApiOperation(" get the setmeal list...")
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){// use a new vo to accept the data
        PageResult pageResult=setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);

    }
    //POST/admin/setmeal/status/{status}
    @ApiOperation("change the status")
    @PostMapping("/status/{status}")
    public Result updateStatus(Long id, @PathVariable("status") Integer status){

        setmealService.updateStatus(id,status);
        return Result.success();

    }
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result delete(@RequestParam List<Long> ids){
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    ///admin/setmeal/{id}
    @ApiOperation(" get the setmeal by id")
    @GetMapping("/{id}")
    public  Result<SetmealVO> getById(@PathVariable("id") Long id){
        SetmealVO setmealVO=setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        setmealService.update(setmealDTO);
        return Result.success();
    }


}
