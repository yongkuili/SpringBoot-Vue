package com.yan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yan.pojo.MenuRole;
import com.yan.utils.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiYongkui
 * @since 2021-05-27
 */
public interface IMenuRoleService extends IService<MenuRole> {

    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
