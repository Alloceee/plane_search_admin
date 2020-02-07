package com.yws.plane.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-23
 */
@Data
@Entity
@TableName("tb_plane")
public class Plane implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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

}
