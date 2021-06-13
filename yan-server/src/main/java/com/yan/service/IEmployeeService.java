package com.yan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yan.pojo.Employee;
import com.yan.utils.RespBean;
import com.yan.utils.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiYongkui
 * @since 2021-05-27
 */
public interface IEmployeeService extends IService<Employee> {

    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    RespBean maxWorkId();

    RespBean insertEmployee(Employee employee);

    /**
     * 查询员工
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);
}
