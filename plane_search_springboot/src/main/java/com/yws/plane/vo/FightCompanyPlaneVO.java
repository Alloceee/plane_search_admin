package com.yws.plane.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author AlmostLover
 */
@Data
@Document(indexName = FightCompanyPlaneVO.INDEX, type = FightCompanyPlaneVO.ORDER_TYPE,  shards = 6, replicas = 2, refreshInterval = "-1")
public class FightCompanyPlaneVO implements Serializable {
    public static final String INDEX = "search-index";
    public static final String ORDER_TYPE = "search-type";

    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;
    /**
     * 目的机场
     */
    @Field(type = FieldType.Keyword, searchAnalyzer = "ik", analyzer = "ik")
    private String endAirport;
    /**
     * 目的地
     */
    private String endCity;
    /**
     * 抵达时间
     */
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 所属航班
     */
    @TableField("plane_id")
    private Integer planeId;
    /**
     * 始发机场
     */
    @Field(type = FieldType.Keyword, searchAnalyzer = "ik", analyzer = "ik")
    private String startAirport;
    /**
     * 始发地
     */
    private String startCity;
    /**
     * 起飞时间
     */
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @Field(type = FieldType.Float)
    private Float price;

    @Excel(name = "公司头像", orderNum = "2")
    private String icon;
    /**
     * 公司名称
     */
    @Excel(name = "公司名称", orderNum = "1")
    private String name;
    /**
     * 详细介绍
     */
    @Excel(name = "详细介绍", orderNum = "3")
    private String description;

    /**
     * 所属公司
     */
    @TableField("company_id")
    private Integer companyId;
    /**
     * 客机机型
     */
    private String model;
    /**
     * 客机序号
     */
    private String number;
    /**
     * 客机型号,0小型，1中型，2大型
     */
    private Integer type;
    public String getStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (this.startTime == null) {
            return "";
        }
        return sdf.format(this.startTime);
    }

    public String getEndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (this.endTime == null) {
            return "";
        }
        return sdf.format(this.endTime);
    }
}
