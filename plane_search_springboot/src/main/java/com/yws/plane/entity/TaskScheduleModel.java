package com.yws.plane.entity;

import lombok.Data;

@Data
public class TaskScheduleModel {

    private Integer jobType;

    private Integer second;

    private Integer minute;

    private Integer hour;

    private Integer[] dayOfWeeks;

    private Integer[] dayOfMonths;
}
