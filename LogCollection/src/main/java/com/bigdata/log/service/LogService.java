package com.bigdata.log.service;

import com.bigdata.log.entity.Log;

/**
 * @description: 日志服务层接口
 * @author: lingjian
 * @create: 2020/5/27 14:34
 */
public interface LogService {

  /**
   * 保存日志信息
   *
   * @param log 日志对象
   */
  void saveLog(Log log);
}
