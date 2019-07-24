package com.reptile.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: Mr.ZuoY
 * @Description:
 * @Date: 2019/2/18 18:23
 */
@Data
public class RecordIds {
    public static RecordIds recordIds = new RecordIds();

    private List<Record> RecordId;
}
