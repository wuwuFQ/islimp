package com.javakc.islimp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javakc.islimp.modules.area.entity.Area;
import com.javakc.islimp.modules.area.service.AreaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class IslimpApplicationTests {

    @Autowired
    private AreaService areaService;

    @Test
    void contextLoads() {
//        新增
//        Area entity = new Area();
//        entity.setAreaName("天津市");
//        entity.setAreaNum(20001);
//        entity.setAreaLevel(1);
//        entity.setAreaPlus("区域备注");
//        entity.setPid(0);
//
//        areaService.save(entity);

//        查询
//        List<Area> list = areaService.list();
//        list.forEach(area -> {
//            System.out.println( area );
//        });

//        分页查询
//        IPage<Area> iPage = areaService.queryByPage(1, 10);
//        System.out.println( iPage );

//        分页条件查询
//        Area entity = new Area();
//        entity.setAreaName("北京");
//        IPage<Area> iPage = areaService.queryByPage(entity, 1, 10);
//        System.out.println( iPage.getRecords() );


//        System.out.println( areaService.getById(3) );

//        修改
//        Area entity = new Area();
//        entity.setId(1);
//        entity.setAreaName("天津市");
//        entity.setAreaNum(20001);
//        entity.setAreaLevel(1);
//        entity.setAreaPlus("区域备注");
//        entity.setPid(0);
//
//        areaService.updateById(entity);

//        删除
//        areaService.removeByIds(List.of(1, 2));

    }

}
