package com.yws.plane.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlaneCompanyVO implements Serializable {
    private Integer id;

    private Integer cid;

    private String name;

    private String model;

    private String number;

    private Integer type;
}
