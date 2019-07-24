package com.reptile.demo.entity;

import lombok.Data;

/**
 * @Author: Mr.ZuoY
 * @Description:
 * @Date: 2019/2/20 14:32
 */

@Data
public class ResponseJson {
    private String PageNumber;
    private String TotalCount;
    private String PageSize;
    private String RequestId;
    private Item DomainRecords;

}
