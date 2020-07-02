package com.flink.stream.real.service.country;

import com.flink.stream.real.entity.country.CountryLog;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;

/**
 * @description: 实例水位线
 * @author: lingjian
 * @create: 2020/6/3 9:11
 */
public class CountryWatermark extends AscendingTimestampExtractor<Tuple2<CountryLog, Long>> {
  @Override
  public long extractAscendingTimestamp(Tuple2<CountryLog, Long> element) {
    return element.f0.getCreateTime();
  }
}
