package com.flink.stream.real.service.country;

import com.alibaba.fastjson.JSONObject;
import com.flink.stream.real.entity.country.CountryLog;
import com.flink.stream.utils.country.IpUtils;

import org.apache.flink.api.common.functions.MapFunction;

/**
 * @description: 实时国家map方法
 * @author: lingjian
 * @create: 2020/6/22 10:31
 */
public class CountryMap implements MapFunction<String, CountryLog> {
  @Override
  public CountryLog map(String s) throws Exception {
    CountryLog result = JSONObject.parseObject(s, CountryLog.class);
    result.setIp(((int)(Math.random() * 100)) % 2 == 0 ? "125.110.207.119" : "46.229.168.151");
    // 设置国家
    String[] strings = IpUtils.find(result.getIp());
    result.setCountry(strings == null ? "未知" : strings[0]);
    return result;
  }
}
