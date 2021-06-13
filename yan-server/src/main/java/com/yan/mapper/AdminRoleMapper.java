package com.yan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yan.pojo.AdminRole;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiYongkui
 * @since 2021-05-27
 */
@Repository
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    Integer addRole(Integer adminId, Integer[] rids);
}
