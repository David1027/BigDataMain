package com.bigdata.demo.controller;

import com.bigdata.demo.entity.Demo;
import com.bigdata.demo.service.DemoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 案例控制层
 * @author: lingjian
 * @create: 2020/6/3 14:50
 */
@RestController
@RequestMapping("/data/demo")
public class DemoController {

  @Autowired private DemoService service;

  @GetMapping("get")
  public Demo getByTime(String time) {
    return service.getByTime(time);
  }
}
