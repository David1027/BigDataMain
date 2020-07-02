package com.bigdata.log.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bigdata.log.entity.Log;
import com.bigdata.log.service.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @description: 日志服务层接口实现类
 * @author: lingjian
 * @create: 2020/5/27 14:35
 */
@Service
public class LogServiceImpl implements LogService {

  @Autowired KafkaTemplate kafkaTemplate;

  /**
   * 保存日志信息
   *
   * @param log 日志对象
   */
  @Override
  public void saveLog(Log log) {
    // 设置用户id：如果用户为空，设为默认0
    if (log.getUserId() == null) {
      log.setUserId(0);
    }
    // 设置访问平台
    log.setPlat("xwt");
    // 打印输出
    System.err.println(JSONObject.toJSONString(log));
    // 输出到kafka的主题中
    kafkaTemplate.send("log", JSONObject.toJSONString(log));
  }
}
