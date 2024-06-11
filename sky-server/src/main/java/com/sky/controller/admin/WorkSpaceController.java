package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * ClassName: WorkSpaceController
 * Package: com.sky.controller.admin
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/11/24 12:00
 * @Version 1.0
 */
@RestController("adminWorkspaceController")
@RequestMapping("/admin/workspace")
@Api(tags = "shop")
@Slf4j
public class WorkSpaceController {

    @Autowired
    WorkspaceService workspaceService;




//接口路径：GET/admin/workspace/businessData
    @ApiOperation(" get the workspace data ")
    @GetMapping("/businessData")
    public Result<BusinessDataVO> businessData(){
        //获得当天的开始时间
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        //获得当天的结束时间
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);
        BusinessDataVO businessDataVO=workspaceService.getBusinessData(begin,end);
        return Result.success(businessDataVO);
    }

}
