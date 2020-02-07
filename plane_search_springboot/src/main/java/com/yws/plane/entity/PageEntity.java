package com.yws.plane.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author AlmostLover
 */
@Data
public class PageEntity {
    /**
     * 分页大小
     */
    private Integer pageSize;
    /**
     * 当前页数
     */
    private Integer currentPage;
    /**
     * 查询关键字
     */
    private String key;
    /**
     * 查询起始城市
     */
    private String startCity;
    private String endCity;

    /**
     * 起始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;
    /**
     * 排序字段
     */
    private String orderByField;

    private Boolean isAsc;

}
