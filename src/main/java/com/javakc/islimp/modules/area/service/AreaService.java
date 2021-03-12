package com.javakc.islimp.modules.area.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javakc.islimp.commons.base.service.BaseService;
import com.javakc.islimp.commons.redis.RedisComponent;
import com.javakc.islimp.modules.area.dao.AreaDao;
import com.javakc.islimp.modules.area.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @program: islimp
 * @description: 区域管理_逻辑层
 * @author: zhou hong gang
 * @create: 2020-12-10 09:51
 */
@Service
public class AreaService extends BaseService<AreaDao, Area> {

    @Autowired
    private RedisComponent redisComponent;

    /**
     * 分页查询
     * @param entity 查询参数
     * @param current 页码 从1开始
     * @param size 每页条数
     * @return 分页对象
     */
    public IPage<Area> queryByPage(Area entity, int current, int size)
    {
        //封装查询参数
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        //1.封装区域名称模糊查询
        if(StringUtils.hasLength(entity.getAreaName()))
        {
            wrapper.like("area_name", entity.getAreaName());
        }
        return baseMapper.selectPage(new Page<>(current, size), wrapper);
    }

    /**
     * 判断是否需要递归执行
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String, Object> delete(int id)
    {
        //判断若果是所有区域直接退出

        //判断是否已经被灯具管理所绑定
        //1.未绑定任何灯具(不仅自己还有所有的子节点)
            //1.1.是否为叶子节点
                //直接删除
            //1.2.是否为父节点
                //调用MySQL存储过程实现
        //2.已绑定灯具信息
            //2.1 提示用户信息

//        in 要删除节点的ID  out 0/1
        int result = 0;

        return Map.of("success", result == 0, "message", result==0?"恭喜该节点删除成功!":"很遗憾, 该区域已被绑定无法删除!");
    }

//    code = 10000
//    data = {
//        success: true/false
//        message: ""
//    }

    public Map<String, Object> selectAreaTree(int pid)
    {
        return baseMapper.selectAreaTree(pid);
    }

}
