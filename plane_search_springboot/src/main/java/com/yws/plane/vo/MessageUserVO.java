package com.yws.plane.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageUserVO implements Serializable {
    public Integer uid;

    public Long planeId;

    /**
     * 用户名
     */
    public String username;

    /**
     * 航班序号
     */
    public String number;

    /**
     * 状态
     */
    public Integer status;

    public Integer type;

    public Date startTime;
}
