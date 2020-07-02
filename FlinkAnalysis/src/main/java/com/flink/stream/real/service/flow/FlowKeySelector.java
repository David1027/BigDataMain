package com.flink.stream.real.service.flow;

import com.flink.stream.real.entity.flow.FlowLog;
import com.flink.stream.utils.DateUtils;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * @description: 流量选择分组的字段
 * @author: lingjian
 * @create: 2020/6/23 9:43
 */
public class FlowKeySelector
    implements KeySelector<Tuple2<FlowLog, Long>, Tuple3<String, String, String>> {
  @Override
  public Tuple3<String, String, String> getKey(Tuple2<FlowLog, Long> value) throws Exception {
    return Tuple3.of(
        DateUtils.longToString(value.f0.getCreateTime(), "yyyy-MM-dd"),
        value.f0.getDevice(),
        value.f0.getSource());
  }
}
