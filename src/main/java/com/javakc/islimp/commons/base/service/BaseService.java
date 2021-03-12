package com.javakc.islimp.commons.base.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javakc.islimp.commons.base.entity.Base;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @program: islimp
 * @description: 公共逻辑层实现
 * @author: zhou hong gang
 * @create: 2020-12-09 11:06
 */
@Transactional(
    readOnly = true,
    isolation = Isolation.REPEATABLE_READ
)
public class BaseService<D extends BaseMapper<T>, T extends Base> extends ServiceImpl<D, T> {

}
