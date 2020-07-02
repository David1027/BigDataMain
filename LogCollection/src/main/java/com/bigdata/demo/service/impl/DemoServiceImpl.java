package com.bigdata.demo.service.impl;

import com.bigdata.demo.dao.DemoDao;
import com.bigdata.demo.entity.Demo;
import com.bigdata.demo.service.DemoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 案例服务层接口实现类
 * @author: lingjian
 * @create: 2020/6/3 14:48
 */
@Service
public class DemoServiceImpl implements DemoService {

  @Autowired private DemoDao dao;

  @Override
  public Demo getByTime(String time) {
    return dao.findFirstByCreateTime(time).orElse(null);
  }
}
