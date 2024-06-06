package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "employee api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation("employee login")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation("employee logout")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * add a new employee
     * @param employeeDTO
     * @return
     */
    @ApiOperation(" add a new employee")
    @PostMapping()
    public Result save(@RequestBody EmployeeDTO employeeDTO){// use a new vo to accept the data from F
        log.info("add new",employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();

    }

    @ApiOperation(" get employee list")
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){// use a new vo to accept the data from F
        PageResult pageResult=employeeService.pageQuery(employeePageQueryDTO);// return a PageResult object
        return Result.success(pageResult);

    }

    @ApiOperation(" get employee by id")
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){// use a new vo to accept the data from F Employee employee=employeeService.getById(id);// return a PageResult object

        Employee employee=employeeService.getById(id);
        return Result.success(employee);

    }

    @ApiOperation(" update the employee")
    @PutMapping()
    public Result update(@RequestBody EmployeeDTO employeeDTO){// use a new vo to accept the data from F
        log.info("update......",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();

    }


}
