package com.example.dal.entity;

import lombok.Data;

import java.util.Date;

/**
 * @since 2023/5/7 1:47
 * @author by liangzj
 */
@Data
public class LoadRecordDO {
    private Integer id;

    private String pathname;

    private Date modifyTime;
}
