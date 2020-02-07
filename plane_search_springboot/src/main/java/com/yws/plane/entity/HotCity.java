package com.yws.plane.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author: yewenshu https://github.com/Alloceee
 * @Date: 2020/2/5 17:54
 * @Project: plane
 */
@Data
@Entity
@TableName("tb_hot_city")
public class HotCity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 热门城市
     */
    private String city;
    /**
     * 城市类型
     */
    private Integer type;
}
