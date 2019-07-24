package com.reptile.demo;

import com.reptile.demo.entity.ResponseJson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
        String bString = "<div id=\"result\"><div class=\"well\"><p>您现在的 IP：<code>118.250.93.3</code></p><p>所在地理位置：<code>湖南省长沙市 电信</code></p><p>GeoIP: Changsha, Hunan, China</p></div></div>";
        String regEx = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(bString);

        while (m.find()) {
            String result = m.group();
            System.out.println(result);
        }
    }

    @Test
    public void test(){
        Queue<String> queue = new LinkedList<>();
        //添加元素
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        String ip = queue.poll();
        System.out.println(ip);
        ip = queue.poll();
        System.out.println(ip);
        System.out.println(111);
    }

    @Test
    public void test1(){
        Map map = new HashMap();
        map.put(123,null);
        System.out.println(map.get(123));
    }
}
