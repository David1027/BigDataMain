package com.flink.stream.real.service.real.common;

import com.flink.stream.real.entity.real.RealLog;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;

/**
 * @description: 实例水位线
 * @author: lingjian
 * @create: 2020/6/3 9:11
 */
public class Watermark extends AscendingTimestampExtractor<Tuple2<RealLog, Long>> {
  @Override
  public long extractAscendingTimestamp(Tuple2<RealLog, Long> element) {
    return element.f0.getCreateTime();
  }
}
