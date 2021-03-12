package com.javakc.islimp.modules.area.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javakc.islimp.commons.base.entity.Base;
import com.javakc.islimp.commons.utils.validator.ObjectId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * @program: islimp
 * @description: 区域管理-属性
 * @author: zhou hong gang
 * @create: 2020-12-10 09:42
 */
@Getter
@Setter
@TableName(value = "islimp_area_manage", resultMap = "areaResultMap")
@ApiModel("区域属性")
public class Area extends Base {

    private Integer areaNum;

//    @NotBlank(message = "区域名称不可以为空")
    @Length(min = 2, max = 15, message = "区域名称必须在{min}-{max}之间")
    @Pattern(regexp = "^[\u4e00-\u9fa5]{0,}$", message = "区域名称只能是汉字")
    @ApiModelProperty(value = "区域名称", required = true, example = "四川省")
    private String areaName;

    @NotNull(message = "区域级别不可以为空")
    @Min(value = 1, message = "区域级别必须是整数且大于等于{value}")
    @Max(value = 5, message = "区域级别必须是整数且小于等于{value}")
    @ApiModelProperty(value = "区域级别(通过上级区域自动计算)", required = true)
    private Integer areaLevel;

    @NotNull(message = "上级区域不可以为空")
    @ObjectId(message = "上级区域的主键不可以为空")
    @ApiModelProperty(name="parent.id", value = "上级区域", required = true)
    private Area parent;

    @ApiModelProperty(value = "区域备注")
    private String areaPlus;

}
