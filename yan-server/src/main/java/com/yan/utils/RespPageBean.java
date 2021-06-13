package com.yan.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LiYongkui
 * @title: RespPageBean
 * @description: 公共返回对象 分页
 * @date 2021-06-07 00717:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespPageBean {
    /**
     * 总条数
     */
    private Long total;
    /**
     * 数据
     */
    private List<?> data;
}

