package com.yws.plane.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yewenshu123
 * @since 2020-01-04
 */
@TableName("tb_manager")
@Data
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 密码
     */
    private String password;
    private String username;
    /**
     * 角色
     */
    private String role;
    /**
     * 创建时间
     */
    private Date createTime;


}
