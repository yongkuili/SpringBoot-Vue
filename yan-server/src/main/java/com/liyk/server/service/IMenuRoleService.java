package com.liyk.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liyk.server.pojo.MenuRole;
import com.liyk.server.utils.RespBean;

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
