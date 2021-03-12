package com.javakc.islimp.modules.area.controller;

import com.javakc.islimp.modules.area.entity.Area;
import com.javakc.islimp.modules.area.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @program: islimp
 * @description: 区域接口
 * @author: zhou hong gang
 * @create: 2020-12-10 13:31
 */
@RestController
@RequestMapping("area")
@Api(tags = "区域接口")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @PostMapping
    @ApiOperation("添加区域")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "areaNum", value = "区域编号", required = true),
        @ApiImplicitParam(name = "areaName", value = "区域名称", required = true),
        @ApiImplicitParam(name = "areaLevel", value = "区域级别", required = true),
        @ApiImplicitParam(name = "parent.id", value = "上级区域", required = true),
        @ApiImplicitParam(name = "areaPlus", value = "区域备注", required = true)
    })
    public void save(@ApiIgnore @RequestBody @Valid Area entity)
    {
        areaService.save(entity);
    }

    @PutMapping
    @ApiOperation("修改区域")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "区域主键", required = true),
        @ApiImplicitParam(name = "areaNum", value = "区域编号", required = true),
        @ApiImplicitParam(name = "areaName", value = "区域名称", required = true),
        @ApiImplicitParam(name = "areaLevel", value = "区域级别", required = true),
        @ApiImplicitParam(name = "parent.id", value = "上级区域", required = true),
        @ApiImplicitParam(name = "areaPlus", value = "区域备注", required = true)
    })
    public void update(@ApiIgnore @RequestBody Area entity)
    {
        areaService.updateById(entity);
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除区域")
    public Map<String, Object> delete(@PathVariable int id)
    {
        return areaService.delete(id);
    }

    @GetMapping
    @ApiOperation("查询区域树形结构")
    @PreAuthorize("hasAuthority('area:tree')")
    public Map<String, Object> queryTree()
    {
        return areaService.selectAreaTree(-1);
    }

    @GetMapping("{id}")
    @ApiOperation("查询主键查询区域")
    @PreAuthorize("hasAuthority('area:load')")
    public Area load(@PathVariable int id)
    {
        return areaService.getById(id);
    }

}
