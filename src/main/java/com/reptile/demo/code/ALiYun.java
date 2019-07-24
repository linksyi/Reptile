package com.reptile.demo.code;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reptile.demo.config.ALiYunConfig;
import com.reptile.demo.config.ServersConfig;
import com.reptile.demo.entity.Record;
import com.reptile.demo.entity.RecordIds;
import com.reptile.demo.entity.RecordRr;
import com.reptile.demo.entity.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Author: Mr.ZuoY
 * @Description:
 * @Date: 2019/2/20 9:45
 */

@Component
public class ALiYun {

    @Autowired
    private ALiYunConfig aliyun;
    @Autowired
    private ServersConfig servers;
    /**
     * JSON装换成实体
     *
     * @param json
     * @return
     */
    public static List<Record> jsonToEntity(String json) {
        ResponseJson responseJson = new Gson().fromJson(json, new TypeToken<ResponseJson>() {
        }.getType());
        return responseJson.getDomainRecords().getRecord();
    }

    /**
     * 查询域名解析信息
     *
     * @return
     */
    public void describeDomainRecords(String ip) {
        DefaultProfile profile = DefaultProfile.getProfile("default", aliyun.getId(), aliyun.getKey());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("alidns.aliyuncs.com");
        request.setVersion("2015-01-09");
        request.setAction("DescribeDomainRecords");
        request.putQueryParameter("DomainName", servers.getUrl());
        try {
            CommonResponse response = client.getCommonResponse(request);
            this.addOrUp(response,ip);

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public void addOrUp(CommonResponse response,String ip){
        if(RecordIds.recordIds.getRecordId() == null || RecordIds.recordIds.getRecordId().size() == 0){
        List<Record> recordList = jsonToEntity(response.getData());
        //判断是否有配置信息
        if (recordList != null && recordList.size() != 0) {
            //判断是否有符合条件的解析数据计数器
            Integer i = 0;
            Integer j = 0;
            //遍历所有解析
            for (Record record : recordList) {

                if (RecordRr.A.getValue().equals(record.getType()) && (RecordRr.xing.getValue().equals(record.getRR()) || RecordRr.AT.getValue().equals(record.getRR()))) {
                    if(!ip.equals(record.getValue())){
                        i++;
                        this.updateDomainRecord(record.getRecordId(),ip,record.getRR());
                        RecordIds.recordIds.getRecordId().add(record);
                    }
                    j++;
                }
            }
            if(i == 0 && j == 0){
                this.addDomainRecord(ip);
            }
        } else {
            this.addDomainRecord(ip);
        }
        }else {
            RecordIds.recordIds.getRecordId().forEach(record -> {
                this.updateDomainRecord(record.getRecordId(),ip,record.getRR());
            });
        }

    }
    /**
     * 添加解析记录
     */
    public void addDomainRecord(String ip) {
        DefaultProfile profile = DefaultProfile.getProfile("default", aliyun.getId(), aliyun.getKey());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("alidns.aliyuncs.com");
        request.setVersion("2015-01-09");
        request.setAction("AddDomainRecord");
        request.putQueryParameter("RR", "@");
        request.putQueryParameter("DomainName", servers.getUrl());
        request.putQueryParameter("Type", "A");
        request.putQueryParameter("Value", ip);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改域名解析
     * @param recordId
     * @param ip
     */
    public void updateDomainRecord(String recordId,String ip,String RR){
        DefaultProfile profile = DefaultProfile.getProfile("default", aliyun.getId(), aliyun.getKey());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("alidns.aliyuncs.com");
        request.setVersion("2015-01-09");
        request.setAction("UpdateDomainRecord");
        request.putQueryParameter("RecordId", recordId);
        request.putQueryParameter("RR", RR);
        request.putQueryParameter("Type", "A");
        request.putQueryParameter("Value", ip);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
