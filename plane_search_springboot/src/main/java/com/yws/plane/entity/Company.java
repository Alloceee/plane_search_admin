package com.yws.plane.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-21
 */
@TableName("tb_company")
@Data
@Entity
@Document(indexName = Company.INDEX, type = Company.ORDER_TYPE,  shards = 6, replicas = 2, refreshInterval = "-1")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
    //建立索引
    public static final String INDEX = "company-test";
    //类型
    public static final String ORDER_TYPE = "company-type";


    @Id
    @TableId(value = "cid", type = IdType.AUTO)
    @Excel(name = "cid", orderNum = "0")
    private Integer cid;
    /**
     * 公司头像
     */
    @Excel(name = "公司头像", orderNum = "2")
    private String icon;
    /**
     * 公司名称
     */
    @Field(type = FieldType.Keyword, searchAnalyzer = "ik", analyzer = "ik")
    @Excel(name = "公司名称", orderNum = "1")
    private String name;
    /**
     * 详细介绍
     */
    @Field(type = FieldType.Keyword, searchAnalyzer = "ik", analyzer = "ik")
    @Excel(name = "详细介绍", orderNum = "3")
    private String description;
}
