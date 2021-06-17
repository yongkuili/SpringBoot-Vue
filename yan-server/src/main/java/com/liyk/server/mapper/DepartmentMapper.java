package com.liyk.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liyk.server.pojo.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiYongkui
 * @since 2021-05-27
 */
@Repository
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getAllDepartmentsByParentId(int i);

    void addDep(Department dep);

    void deleteDep(Department department);
}
