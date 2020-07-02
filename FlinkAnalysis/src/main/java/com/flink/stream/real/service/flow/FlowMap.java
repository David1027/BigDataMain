package com.flink.stream.real.service.flow;

import com.alibaba.fastjson.JSONObject;
import com.flink.stream.real.entity.flow.FlowLog;
import com.flink.stream.utils.device.HttpRequestUtils;
import com.flink.stream.utils.source.SourceTypeUtils;

import org.apache.flink.api.common.functions.MapFunction;

/**
 * @description: 流量map方法
 * @author: lingjian
 * @create: 2020/6/23 9:21
 */
public class FlowMap implements MapFunction<String, FlowLog> {
  @Override
  public FlowLog map(String s) throws Exception {
    FlowLog result = JSONObject.parseObject(s, FlowLog.class);
    // 根据设备标识获取设备类型
    result.setDevice(HttpRequestUtils.getDevice(result.getAgent()));
    // 根据流量来源获取来源类型
    result.setSource(SourceTypeUtils.getSourceType(result.getRef()));
    return result;
  }
}
