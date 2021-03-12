package com.javakc.islimp.modules.area.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javakc.islimp.modules.area.entity.Area;

import java.util.Map;

/**
 * 区域数据层接口
 */
public interface AreaDao extends BaseMapper<Area> {
    Map<String, Object> selectAreaTree(int pid);
}
