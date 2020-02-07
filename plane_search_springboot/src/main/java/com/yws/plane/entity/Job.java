package com.yws.plane.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Job implements Serializable {
    private Long id;

    private String jobName;

    private String jobTime;

    private String jobGroup;
}
