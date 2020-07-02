package com.flink.stream.hdfs.service;

import com.flink.stream.real.entity.real.RealLog;

import org.apache.flink.api.common.functions.AggregateFunction;

/**
 * @description: 流量统计
 * @author: lingjian
 * @create: 2020/6/4 15:29
 */
public class UserCountAggregate implements AggregateFunction<RealLog, Long, Long> {
  @Override
  public Long createAccumulator() {
    return 0L;
  }

  @Override
  public Long add(RealLog value, Long accumulator) {
    return accumulator + 1;
  }

  @Override
  public Long getResult(Long accumulator) {
    return accumulator;
  }

  @Override
  public Long merge(Long a, Long b) {
    return a + b;
  }
}
