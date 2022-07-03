package com.caleb.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInfo {
    private String id;

    private Integer type;

    private String message;

    private Date createTime;

}
