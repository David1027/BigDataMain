package com.bigdata.demo.service;

import com.bigdata.demo.entity.Demo;

/**
 * @description: 案例服务层接口
 * @author: lingjian
 * @create: 2020/6/3 14:48
 */
public interface DemoService {
    Demo getByTime(String time);
}
