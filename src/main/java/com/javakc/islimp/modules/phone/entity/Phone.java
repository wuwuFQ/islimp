package com.javakc.islimp.modules.phone.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @program: islimp
 * @description: 手机属性
 * @author: zhou hong gang
 * @create: 2020-12-19 09:46
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@Document(indexName = "phone")
public class Phone {
    @Id
    private int id; //主键

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name; //手机名称

    @Field(type = FieldType.Keyword)
    private String type; //类型 手机 平板 PC...

    @Field(type = FieldType.Keyword)
    private String brand; //品牌

    @Field(type = FieldType.Double)
    private Double price; //价格

    @Field(type = FieldType.Keyword, index = false)
    private String images; //图片 不对图片查询,指定为 false
}
