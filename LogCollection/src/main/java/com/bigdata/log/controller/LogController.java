package com.bigdata.log.controller;

import javax.servlet.http.HttpServletRequest;

import com.bigdata.common.util.HttpRequestUtils;
import com.bigdata.log.entity.Log;
import com.bigdata.log.service.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 日志控制层
 * @author: lingjian
 * @create: 2020/5/27 14:27
 */
@RestController
@RequestMapping("/data/log")
public class LogController {

  @Autowired private LogService service;

  /**
   * 保存日志信息
   *
   * @param log
   */
  @GetMapping("save")
  public void saveLog(Log log, HttpServletRequest request) {
    // 设置ip地址
    log.setIp(HttpRequestUtils.getIpAddress(request));
    // 设置创建时间
    log.setCreateTime(System.currentTimeMillis());
    service.saveLog(log);
  }

  //  @KafkaListener(topics = {"log"})
  //  public void listen(String data) {
  //    System.err.println("kafka====>" + data);
  //  }
}
