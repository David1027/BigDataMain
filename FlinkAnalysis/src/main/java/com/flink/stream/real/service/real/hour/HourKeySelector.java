package com.flink.stream.real.service.real.hour;

import com.flink.stream.real.entity.real.RealLog;
import com.flink.stream.utils.DateUtils;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * @description: 自定义分组
 * @author: lingjian
 * @create: 2020/6/3 9:02
 */
public class HourKeySelector implements KeySelector<Tuple2<RealLog, Long>, String> {
  @Override
  public String getKey(Tuple2<RealLog, Long> value) throws Exception {
    return DateUtils.longToString(value.f0.getCreateTime(), "yyyy-MM-dd-HH");
  }
}
