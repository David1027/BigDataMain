package com.flink.stream.real.service.flow;

import com.flink.stream.real.entity.flow.FlowLog;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;

/**
 * @description: 流量水位线
 * @author: lingjian
 * @create: 2020/6/23 9:39
 */
public class FlowWatermark extends AscendingTimestampExtractor<Tuple2<FlowLog, Long>> {
  @Override
  public long extractAscendingTimestamp(Tuple2<FlowLog, Long> element) {
    return element.f0.getCreateTime();
  }
}
