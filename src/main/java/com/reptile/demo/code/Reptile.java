package com.reptile.demo.code;

import com.reptile.demo.config.ServersConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Mr.ZuoY
 * @Description:
 * @Date: 2019/2/18 15:55
 */



@Component
public class Reptile {

    @Autowired
    private ServersConfig serversConfig;
    @Autowired
    private ALiYun aLiYun;

    @Scheduled(initialDelay=1000,fixedDelay = 1000 * 60 * 5)
    public void reptile() {
        URL url;

        int responsecode;

        HttpURLConnection urlConnection;

        BufferedReader reader;

        String line;

        Queue<String> urlQueue = serversConfig.getUrlQueue();

        String httpUrl = urlQueue.poll();
        try{

            //生成一个URL对象，要获取源代码的网页地址
            url=new URL(httpUrl);

            //打开URL

            urlConnection = (HttpURLConnection)url.openConnection();

            //获取服务器响应代码

            responsecode=urlConnection.getResponseCode();

            if(responsecode==200){

                //得到输入流，即获得了网页的内容

                reader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"GBK"));

                while((line=reader.readLine())!=null){

                    //ip正则
                    String regEx = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(line);
                    //检测是否符合ip正则
                    if (matcher.find()) {
                        String ip = matcher.group();
                        aLiYun.describeDomainRecords(ip);
                        //检查ip返回状态
                        break;
                    }

                }
                urlQueue.offer(httpUrl);
            }else{

                System.out.println("获取不到网页:"+httpUrl+"的源码，服务器响应代码为："+responsecode);
                urlQueue.offer(httpUrl);
                reptile();
            }

        }

        catch(Exception e){
            System.out.println("获取不到网页:"+httpUrl+"的源码,出现异常："+e);
            urlQueue.offer(httpUrl);
            reptile();
        }
    }
}
