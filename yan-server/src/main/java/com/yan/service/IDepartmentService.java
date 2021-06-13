package com.yan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yan.pojo.Department;
import com.yan.utils.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiYongkui
 * @since 2021-05-27
 */
public interface IDepartmentService extends IService<Department> {

    List<Department> getAllDepartments();

    RespBean addDep(Department department);

    RespBean deleteDep(Integer id);
}
