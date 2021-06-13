package com.yan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yan.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiYongkui
 * @since 2021-05-27
 */
public interface IMenuService extends IService<Menu> {

    //通过用户ID查询菜单列表
    List<Menu> getMenusByAdminId();

    //根据角色查询菜单
    List<Menu> getMenusWithRole();

    //获取所有菜单
    List<Menu> getAllMenus();
}
