package com.liyk.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.liyk.server.pojo.Employee;
import com.liyk.server.pojo.Salary;
import com.liyk.server.service.IEmployeeService;
import com.liyk.server.service.ISalaryService;
import com.liyk.server.utils.RespBean;
import com.liyk.server.utils.RespPageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LiYongkui
 * @title: SalarySobCfgController
 * @description: 员工套账
 * @date 2021-06-18 018 21:42
 */
@Api(tags = "SalarySobCfgController")
@RequestMapping("/salary/sobcfg")
@RestController
public class SalarySobCfgController {
    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有员工套账")
    @GetMapping("/")
    public RespPageBean getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size){
        return employeeService.getEmployeeWithSalary(currentPage,size);
    }

    @ApiOperation(value = "获取所有工资套账")
    @GetMapping("/salaries")
    public List<Salary> getAllSalary(){
        return salaryService.list();
    }

    @ApiOperation(value = "更新员工套账")
    @PutMapping("/")
    public RespBean updateEmployeeSalary(Integer eid, Integer sid){
        if (employeeService.update(new UpdateWrapper<Employee>().set("salaryId",sid).eq("id",eid))){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }
}
